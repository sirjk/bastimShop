package com.company.shopBastim.utility;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.company.shopBastim.exceptions.RefreshTokenMissingException;
import com.company.shopBastim.exceptions.RefreshTokenTTLBelowOneException;
import com.company.shopBastim.model.User;
import com.company.shopBastim.repository.UserRepository;
import com.company.shopBastim.service.SystemService;
import com.company.shopBastim.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
public class RefreshTokenUtility {
    private final UserService userService;


    @Autowired
    public RefreshTokenUtility(UserService userServiceArg){
        userService = userServiceArg;
    }



    @Value( "${refresh-token-TTL:10}" )
    private Integer refreshTokenTTL;

    @Value( "${refresh-token-longevity:720}" )
    private Integer refreshTokenLongevity;

    @Value( "${refresh-token-dynamic-settings-mode:false}" )
    private Boolean refreshTokenDynamicSettingsMode;

    @Value( "${refresh-token-max-longevity:1440}" )
    private Integer refreshTokenMaxLongevity;

    @Value( "${refresh-token-min-longevity:60}" )
    private Integer refreshTokenMinLongevity;

    @Value( "${refresh-token-decrease-multiplier:0.5}" )
    private Double refreshTokenDecreaseMultiplier;

    public RefreshTokenUtility(UserService userServiceArgument, Integer refreshTokenTTLArgument, Integer refreshTokenLongevityArgument, Boolean refreshTokenDynamicSettingsModeArgument, Integer refreshTokenMaxLongevityArgument, Integer refreshTokenMinLongevityArgument, Double refreshTokenDecreaseMultiplierArgument) {
        this.userService = userServiceArgument;
        this.refreshTokenTTL = refreshTokenTTLArgument;
        this.refreshTokenLongevity = refreshTokenLongevityArgument;
        this.refreshTokenDynamicSettingsMode = refreshTokenDynamicSettingsModeArgument;
        this.refreshTokenMaxLongevity = refreshTokenMaxLongevityArgument;
        this.refreshTokenMinLongevity = refreshTokenMinLongevityArgument;
        this.refreshTokenDecreaseMultiplier = refreshTokenDecreaseMultiplierArgument;
    }



    public RequestResponseClass refreshToken(HttpServletRequest request, HttpServletResponse response, String subject) throws IOException, RefreshTokenMissingException, RefreshTokenTTLBelowOneException {
        String authorizationHeader = null;  //It is actually refresh token
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("refresh_token")) {
                    //do something
                    //value can be retrieved using #cookie.getValue()
                    authorizationHeader = cookie.getValue();
                }

            }
        }

        //ONLY FOR VERSION WITH COOKIES
        if(authorizationHeader != null)
            authorizationHeader = "Bearer " + authorizationHeader;

        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            try {
                String token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256(System.getenv("SECRET").getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(token);
                String userName = decodedJWT.getSubject();
                if(subject != null)
                    userName = subject;
                User user = userService.getUserByEmail(userName);
                List<String> permission = new ArrayList<>();
                user.getRoles().stream().forEach(role -> {
                    role.getPermissions().stream().forEach(permis -> {
                        permission.add(permis.getName());
                    });
                });
                String access_token = JWT.create()
                        .withSubject(user.getEmail())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 10*60*1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("permissions", permission)
                        .sign(algorithm);


                Integer recieviedTokenTTL = decodedJWT.getClaim("TTL").asInt();
                if(recieviedTokenTTL < 1 && subject == null){
                    throw new RefreshTokenTTLBelowOneException();
                }

                if(!refreshTokenDynamicSettingsMode){
                    refreshTokenMinLongevity = refreshTokenLongevity;
                    refreshTokenMaxLongevity = refreshTokenLongevity;
                    refreshTokenDecreaseMultiplier = 1.0;
                }

                Double newRefreshTokenLongevity = refreshTokenLongevity + 0.0; //Fetched from token!!!!

                for(int i =0; i < refreshTokenTTL - recieviedTokenTTL; i ++){
                    newRefreshTokenLongevity = newRefreshTokenLongevity * refreshTokenDecreaseMultiplier;
                }


                if(refreshTokenMinLongevity>refreshTokenLongevity){
                    refreshTokenLongevity = refreshTokenMinLongevity;
                }
                if(refreshTokenMaxLongevity<refreshTokenLongevity){
                    refreshTokenLongevity = refreshTokenMaxLongevity;
                }

                newRefreshTokenLongevity = Math.ceil(newRefreshTokenLongevity*60*1000);
                Long newRefreshTokenLongevityAsLong = newRefreshTokenLongevity.longValue();// Long.parseLong( newRefreshTokenLongevity.toString());

                String refresh_token = JWT.create()
                        .withSubject(user.getEmail())
                        .withClaim("TTL", (recieviedTokenTTL - 1) ) //fetched from request token!!!!!
                        .withExpiresAt(new Date(System.currentTimeMillis() + newRefreshTokenLongevityAsLong ))
                        .withIssuer(request.getRequestURL().toString())
                        .sign(algorithm);


                Cookie cookie_access = new Cookie("access_token", access_token);
                // cookie_access.setSecure(true);
                cookie_access.setHttpOnly(true);
                cookie_access.setPath("/");

                Cookie cookie_refresh = new Cookie("refresh_token", refresh_token);
                // cookie_refresh.setSecure(true);
                cookie_refresh.setHttpOnly(true);
                cookie_refresh.setPath("/");

                Cookie user_id = new Cookie("user_id", user.getId().toString());
                user_id.setHttpOnly(false);
                user_id.setPath("/");

                Cookie isLogged = new Cookie("is_logged", "true");
                isLogged.setHttpOnly(false);
                isLogged.setPath("/");

                response.addCookie(cookie_access);
                response.addCookie(cookie_refresh);
                response.addCookie(user_id);
                response.addCookie(isLogged);

                response.setContentType(APPLICATION_JSON_VALUE);
                if(subject == null)
                    new ObjectMapper().writeValue(response.getOutputStream(), "Token refreshed.");
                //response.getWriter().write("Token refreshed.");

                return new RequestResponseClass(response, request);

            }catch (Exception exception){
                System.out.println(exception);
                response.setHeader("error", exception.getMessage());
                response.setStatus(UNAUTHORIZED.value());
                //response.sendError(FORBIDDEN.value());
                Map<String,String> error =new HashMap<>();
                error.put("error_message", exception.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                if(subject == null)
                     new ObjectMapper().writeValue(response.getOutputStream(), "Refresh token expired");
                //response.getWriter().write("Refresh token expired");

                return new RequestResponseClass(response, request);
            }
        }
        else{
            throw new RefreshTokenMissingException();
            //return new RequestResponseClass(null, null);
        }

    }
}
