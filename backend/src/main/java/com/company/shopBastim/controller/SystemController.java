package com.company.shopBastim.controller;

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
import com.company.shopBastim.utility.RefreshTokenUtility;
import com.company.shopBastim.utility.RequestResponseClass;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Ref;
import java.util.*;

import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
//@RequestMapping(path = "api/v1")
public class SystemController {

    private final UserService userService;
    private final SystemService systemService;
    private  final RefreshTokenUtility refreshTokenUtility;


    @Autowired
    public SystemController(UserService userServiceArg, SystemService systemServiceArg, RefreshTokenUtility refreshTokenUtilityArg){
        userService = userServiceArg;
        systemService = systemServiceArg;
        refreshTokenUtility = refreshTokenUtilityArg;
    }


    @PostMapping (value="api/v1/logout")
    public ResponseEntity<?> logoutPage (HttpServletRequest request, HttpServletResponse response) {
        return new ResponseEntity<String>("Logged out", HttpStatus.OK);
    }

    @PostMapping("api/v1/refresh_token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {

       try{
           RequestResponseClass output = refreshTokenUtility.refreshToken(request, response, null);
       }
       catch (Exception exception){

           if(exception.getClass() == RefreshTokenMissingException.class){
               response.setStatus(NOT_ACCEPTABLE.value());
               new ObjectMapper().writeValue(response.getOutputStream(), "Error: Refresh Token is missing.");
           }
           else if(exception.getClass() == RefreshTokenTTLBelowOneException.class){
               response.setStatus(NOT_ACCEPTABLE.value());
               new ObjectMapper().writeValue(response.getOutputStream(), "Error: Refresh Token used too many times - please log in again.");
           }

       }


        /*String authorizationHeader = null;  //It is actually refresh token
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
        authorizationHeader = "Bearer " + authorizationHeader;

        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            try {
                String token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256(System.getenv("SECRET").getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(token);
                String userName = decodedJWT.getSubject();
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


                String refresh_token = JWT.create()
                        .withSubject(user.getEmail())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 30*60*1000))
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

                response.addCookie(cookie_access);
                response.addCookie(cookie_refresh);
                response.addCookie(user_id);

                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), "Token refreshed.");

            }catch (Exception exception){
                response.setHeader("error", exception.getMessage());
                response.setStatus(UNAUTHORIZED.value());
                //response.sendError(FORBIDDEN.value());
                Map<String,String> error =new HashMap<>();
                error.put("error_message", exception.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), "Refresh token expired");
            }
        }
        else{
            throw new RuntimeException("Refresh token is missing");
        }*/
    }

    @PostMapping("api/v1/register")
    public ResponseEntity<String> postUser(@RequestBody User user){
        String message = systemService.registerUser(user);
        if(message.startsWith("Fail")){
            return new ResponseEntity<String>(message, HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<String>(message, HttpStatus.OK);
    }

}
