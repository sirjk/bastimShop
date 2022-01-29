package com.company.shopBastim.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import com.company.shopBastim.exceptions.BlackListedException;

public class CustomAuthorizationFilter extends OncePerRequestFilter {

    BlackList blackList = BlackList.getInstance();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(request.getServletPath().equals("/api/v1/login") || request.getServletPath().equals("/api/v1/refresh_token")){
            filterChain.doFilter(request,response);
        }
        else if(request.getServletPath().equals("/api/v1/logout")){

            String authorizationHeader = null;
            String authorizationHeaderRefresh = null;
           // String authorizationHeader = request.getHeader(AUTHORIZATION);
          //  String authorizationHeaderRefresh = request.getHeader(AUTHORIZATION);
            Cookie[] cookies = request.getCookies();

            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("access_token")) {
                        //do something
                        //value can be retrieved using #cookie.getValue()
                        authorizationHeader = cookie.getValue();
                    }
                    if (cookie.getName().equals("refresh_token")) {
                        authorizationHeaderRefresh = cookie.getValue();

                    }
                }
            }

            //ONLY FOR VERSION WITH COOKIES
            if(authorizationHeader != null)
                authorizationHeader = "Bearer " + authorizationHeader;

            if(authorizationHeaderRefresh  != null)
                authorizationHeaderRefresh = "Bearer " + authorizationHeaderRefresh;



                String responseMessage = "";

                Boolean tokenLoggedOutFlag =false;
                Boolean refreshTokenLoggedOutFlag =false;

                Algorithm algorithm = Algorithm.HMAC256(System.getenv("SECRET").getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();

                if(authorizationHeaderRefresh != null && authorizationHeaderRefresh.startsWith("Bearer ")) {
                    try{
                        String tokenRefresh = authorizationHeaderRefresh.substring("Bearer ".length());
                        DecodedJWT decodedJWTRefresh = verifier.verify(tokenRefresh);
                        if(blackList.getBlackListList().contains(authorizationHeader)){
                            responseMessage += "Fail: Refresh Token in already not valid.\n";
                        }
                        else{
                            blackList.getBlackListList().add(authorizationHeaderRefresh);
                            responseMessage += "Success: Refresh Token logged out.\n";
                            refreshTokenLoggedOutFlag = true;
                        }


                    }catch (TokenExpiredException e){
                        responseMessage += "Fail: Refresh Token in already not valid.\n";
                    }
                }
                else{
                    responseMessage += "Fail: No valid Refresh Token found.\n";
                }


                if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                    try {
                        String token = authorizationHeader.substring("Bearer ".length());
                        DecodedJWT decodedJWT = verifier.verify(token);
                        if (blackList.getBlackListList().contains(authorizationHeader)) {
                            responseMessage += "Fail: Token in already not valid.\n";
                        } else {
                            blackList.getBlackListList().add(authorizationHeader);
                            responseMessage += "Success: Token logged out.\n";
                            tokenLoggedOutFlag = true;
                        }
                    } catch (TokenExpiredException e) {
                        responseMessage += "Fail: Token in already not valid.\n";
                    }

                }
                else{
                    responseMessage += "Fail: No valid Token found.\n";
                }

                  response.setStatus(OK.value());
                if(tokenLoggedOutFlag && refreshTokenLoggedOutFlag){
                    responseMessage += "Success: Tokens are no longer valid!\n";
                }
                else if(!tokenLoggedOutFlag && refreshTokenLoggedOutFlag){
                    responseMessage += "Partial success: Refresh Token is no longer valid!\n";
                }
                else if(tokenLoggedOutFlag && !refreshTokenLoggedOutFlag){
                    responseMessage += "Partial success: Token is no longer valid!\n";
                }
                else{
                    response.setStatus(NOT_ACCEPTABLE.value());
                    responseMessage += "Fail: No token was made invalid!\n";
                }
            response.setContentType(APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(), responseMessage);

            /*Cookie isLoggedIn = new Cookie("is_logged_in", "false");
            isLoggedIn.setHttpOnly(false);

            response.addCookie(isLoggedIn);

            */

                //filterChain.doFilter(request, response);
                /*
                catch (Exception exception){
                    System.out.println("Error logging out "+ exception.getMessage());
                    response.setHeader("error", exception.getMessage());
                    response.setStatus(UNAUTHORIZED.value());
                    //response.sendError(FORBIDDEN.value());
                    //Map<String,String> error =new HashMap<>();
                    //error.put("error_message", exception.getMessage());
                    response.setContentType(APPLICATION_JSON_VALUE);
                    new ObjectMapper().writeValue(response.getOutputStream(), "Log out failed.");
                }*/



        }
        else{

           // String authorizationHeader = request.getHeader(AUTHORIZATION);
            String authorizationHeader = null;

            Cookie[] cookies = request.getCookies();

            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("access_token")) {
                        //do something
                        //value can be retrieved using #cookie.getValue()
                        authorizationHeader = cookie.getValue();
                    }
                }
            }

            //ONLY FOR VERSION WITH COOKIES
            if(authorizationHeader != null)
                authorizationHeader = "Bearer " + authorizationHeader;


            //ponizej czyszczenie(sprawdzanie) czarnej listy przy kazdym zaptaniu
            if(!blackList.getBlackListList().isEmpty()){
                Algorithm algorithm = Algorithm.HMAC256(System.getenv("SECRET").getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                List<String> newBlackList = new ArrayList<>();
                for(String bToken : blackList.getBlackListList()){
                    String token = bToken.substring("Bearer ".length());
                    try {
                        DecodedJWT decodedJWT = verifier.verify(token);
                        Date expiresAt = decodedJWT.getExpiresAt();
                        if(!expiresAt.before(new Date()) ){
                            newBlackList.add(bToken);
                        }
                    }catch (TokenExpiredException e){

                    }

                }
                blackList.setBlackListList(newBlackList);
            }

            if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ") && !blackList.getBlackListList().contains(authorizationHeader)){
                try {
                    String token = authorizationHeader.substring("Bearer ".length());
                    Algorithm algorithm = Algorithm.HMAC256(System.getenv("SECRET").getBytes());
                    JWTVerifier verifier = JWT.require(algorithm).build();
                    DecodedJWT decodedJWT = verifier.verify(token);
                    String userName = decodedJWT.getSubject();
                    String[] permissions = decodedJWT.getClaim("permissions").asArray(String.class);
                    Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                    stream(permissions).forEach(permission -> {
                        authorities.add(new SimpleGrantedAuthority(permission));
                    });
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userName, null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    filterChain.doFilter(request, response);
                }catch (Exception exception){
                    filterChain.doFilter(request, response);
                    /*System.out.println("Error logging in "+ exception.getMessage());
                    response.setHeader("error", exception.getMessage());
                    response.setStatus(UNAUTHORIZED.value());
                    //response.sendError(FORBIDDEN.value());
                    Map<String,String> error =new HashMap<>();
                    error.put("error_message", exception.getMessage());
                    response.setContentType(APPLICATION_JSON_VALUE);
                    new ObjectMapper().writeValue(response.getOutputStream(), error);*/
                }
            }
            else{
                filterChain.doFilter(request, response);
            }
        }
    }
}
