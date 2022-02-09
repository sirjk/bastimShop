package com.company.shopBastim.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.company.shopBastim.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


//@PropertySource("classpath:application.properties")
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    BlackList blackList = BlackList.getInstance();


    @Value("${refresh-token-TTL:10}")
    private Integer refreshTokenTTL;

    @Value("${refresh-token-longevity:720}")
    private Integer refreshTokenLongevity;

    @Value("${refresh-token-dynamic-settings-mode:false}")
    private Boolean refreshTokenDynamicSettingsMode;

    @Value("${refresh-token-max-longevity:1440}")
    private Integer refreshTokenMaxLongevity;

    @Value("${refresh-token-min-longevity:60}")
    private Integer refreshTokenMinLongevity;

    @Value("${refresh-token-decrease-multiplier:0.5}")
    private Double refreshTokenDecreaseMultiplier;



    private UserRepository userRepository;

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
    }

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager, UserRepository userRepository, Integer refreshTokenTTLArgument, Integer refreshTokenLongevityArgument, Boolean refreshTokenDynamicSettingsModeArgument, Integer refreshTokenMaxLongevityArgument, Integer refreshTokenMinLongevityArgument, Double refreshTokenDecreaseMultiplierArgument) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.refreshTokenTTL = refreshTokenTTLArgument;
        this.refreshTokenLongevity = refreshTokenLongevityArgument;
        this.refreshTokenDynamicSettingsMode = refreshTokenDynamicSettingsModeArgument;
        this.refreshTokenMaxLongevity = refreshTokenMaxLongevityArgument;
        this.refreshTokenMinLongevity = refreshTokenMinLongevityArgument;
        this.refreshTokenDecreaseMultiplier = refreshTokenDecreaseMultiplierArgument;
    }

   //@Autowired
   //public CustomAuthenticationFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
   //    this.userRepository = userRepository;
   //    this.authenticationManager =authenticationManager;
   //}

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String email = request.getParameter("username");
        String password = request.getParameter("password");
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
        return authenticationManager.authenticate(authenticationToken);
    }



    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        User user = (User)authentication.getPrincipal();
        Algorithm algorithm = Algorithm.HMAC256(System.getenv("SECRET").getBytes());
        String access_token = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 5*60*1000))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("permissions", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);


        if(!refreshTokenDynamicSettingsMode){
            refreshTokenMinLongevity = refreshTokenLongevity;
            refreshTokenMaxLongevity = refreshTokenLongevity;
            refreshTokenDecreaseMultiplier = 1.0;
        }

        if(refreshTokenMinLongevity>refreshTokenLongevity){
            refreshTokenLongevity = refreshTokenMinLongevity;
        }
        if(refreshTokenMaxLongevity<refreshTokenLongevity){
            refreshTokenLongevity = refreshTokenMaxLongevity;
        }

        String refresh_token = JWT.create()
                .withSubject(user.getUsername())
                .withClaim("TTL", refreshTokenTTL)
                .withExpiresAt(new Date(System.currentTimeMillis() + refreshTokenLongevity*60*1000))
                .withIssuer(request.getRequestURL().toString())
                .sign(algorithm);

        //LOGOUT W JWT
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(access_token);
        DecodedJWT decodedJWTRefresh = verifier.verify(refresh_token);
        List<String> newBlackList = new ArrayList<>();
        for(String bToken : blackList.getBlackListList()){
            String token = bToken.substring("Bearer ".length());
            try {
                DecodedJWT decodedJWTinner = verifier.verify(token);
                Date expiresAt = decodedJWTinner.getExpiresAt();
                if(!expiresAt.before(new Date()) && !decodedJWT.equals(decodedJWTinner) && !decodedJWTRefresh.equals(decodedJWTinner) ){
                    newBlackList.add(bToken);
                }
            }catch (TokenExpiredException e){
            }


        }
        blackList.setBlackListList(newBlackList);
        //LOGOUT W JWT

        //response.setHeader("access_token", access_token);
        //response.setHeader("refresh_token", refresh_token);
       /* Map<String,String> tokens =new HashMap<>();
        tokens.put("access_token", access_token);
        tokens.put("refresh_token", refresh_token);
        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), tokens);*/



        Cookie cookie_access = new Cookie("access_token", access_token);
       // cookie_access.setSecure(true);
        cookie_access.setHttpOnly(true);
        cookie_access.setPath("/");

        Cookie cookie_refresh = new Cookie("refresh_token", refresh_token);
       // cookie_refresh.setSecure(true);
        cookie_refresh.setHttpOnly(true);
        cookie_refresh.setPath("/");


        Optional<com.company.shopBastim.model.User> querer = userRepository.findUserByEmail(user.getUsername());

        Cookie user_id = new Cookie("user_id", querer.get().getId().toString());
        user_id.setHttpOnly(false);

        user_id.setPath("/");

        response.addCookie(cookie_access);
        response.addCookie(cookie_refresh);
        response.addCookie(user_id);

    }
}
