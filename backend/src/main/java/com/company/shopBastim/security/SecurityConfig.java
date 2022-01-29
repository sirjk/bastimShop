package com.company.shopBastim.security;

import com.company.shopBastim.filter.CustomAuthenticationFilter;
import com.company.shopBastim.filter.CustomAuthorizationFilter;
import com.company.shopBastim.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserRepository userRepository;

    @Value("${api-base-endpoint}")
    private String basePath;

    @Autowired
    public SecurityConfig(UserDetailsService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder, UserRepository userRepository) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource()
    {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST"));
        configuration.setAllowedHeaders(Arrays.asList("Content-Type"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean(), userRepository);
        customAuthenticationFilter.setFilterProcessesUrl(basePath + "/login");
        http.cors();
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers(basePath + "/login/**", basePath + "/refresh_token/**", basePath + "/register/**", basePath + "/logout/**").permitAll();
        http.authorizeRequests().antMatchers(POST, basePath + "/products/**").hasAnyAuthority("WRITE_PRODUCT");
        http.authorizeRequests().antMatchers(PUT, basePath + "/products/**").hasAnyAuthority("UPDATE_PRODUCT");
        http.authorizeRequests().antMatchers(GET, basePath + "/products/**").permitAll();
        http.authorizeRequests().antMatchers(DELETE, basePath + "/products/**").hasAnyAuthority("DELETE_PRODUCT");

        http.authorizeRequests().antMatchers(GET, basePath + "/users/{accountId:\\d+}").hasAnyAuthority("READ_USER_SELF", "READ_USER");

        http.authorizeRequests().antMatchers(POST, basePath + "/users/**").hasAnyAuthority("WRITE_USER");
        http.authorizeRequests().antMatchers(PUT, basePath + "/users/**").hasAnyAuthority("UPDATE_USER");
        http.authorizeRequests().antMatchers(GET, basePath + "/users/**").hasAnyAuthority("READ_USER");
        http.authorizeRequests().antMatchers(DELETE, basePath + "/users/**").hasAnyAuthority("DELETE_USER");

        http.authorizeRequests().antMatchers(GET, basePath + "/permissions/**").hasAnyAuthority("READ_PERMISSION");
        http.authorizeRequests().antMatchers(DELETE, basePath + "/permissions/**").hasAnyAuthority("DELETE_PERMISSION");
        http.authorizeRequests().antMatchers(POST, basePath + "/permissions/**").hasAnyAuthority("WRITE_PERMISSION");
        http.authorizeRequests().antMatchers(PUT, basePath + "/permissions/**").hasAnyAuthority("UPDATE_PERMISSION");

        http.authorizeRequests().antMatchers(GET, basePath + "/roles/**").hasAnyAuthority("READ_ROLE");
        http.authorizeRequests().antMatchers(DELETE, basePath + "/roles/**").hasAnyAuthority("DELETE_ROLE");
        http.authorizeRequests().antMatchers(POST, basePath + "/roles/**").hasAnyAuthority("WRITE_ROLE");
        http.authorizeRequests().antMatchers(PUT, basePath + "/roles/**").hasAnyAuthority("UPDATE_ROLE");

        http.authorizeRequests().antMatchers(GET, basePath + "/orders/{accountId:\\d+}").hasAnyAuthority("READ_ORDER_SELF", "READ_ORDER");

        http.authorizeRequests().antMatchers(GET, basePath + "/orders/**").hasAnyAuthority("READ_ORDER");
        http.authorizeRequests().antMatchers(DELETE, basePath + "/orders/**").hasAnyAuthority("DELETE_ORDER");
        http.authorizeRequests().antMatchers(POST, basePath + "/orders/**").hasAnyAuthority("WRITE_ORDER", "WRITE_ORDER_SELF");
        http.authorizeRequests().antMatchers(PUT, basePath + "/orders/**").hasAnyAuthority("UPDATE_ORDER");

        http.authorizeRequests().antMatchers(GET, basePath + "/categories/**").permitAll();
        http.authorizeRequests().antMatchers(DELETE, basePath + "/categories/**").hasAnyAuthority("DELETE_CATEGORY");
        http.authorizeRequests().antMatchers(POST, basePath + "/categories/**").hasAnyAuthority("WRITE_CATEGORY");
        http.authorizeRequests().antMatchers(PUT, basePath + "/categories/**").hasAnyAuthority("UPDATE_CATEGORY");

        http.authorizeRequests().antMatchers(GET, basePath + "/users/*/roles").hasAnyAuthority("READ_USER_ROLE");
        http.authorizeRequests().antMatchers(DELETE, basePath + "/users/*/roles").hasAnyAuthority("DELETE_USER_ROLE");
        http.authorizeRequests().antMatchers(POST, basePath + "/users/*/roles").hasAnyAuthority("WRITE_USER_ROLE");

        http.authorizeRequests().antMatchers(GET, basePath + "/roles/*/permissions").hasAnyAuthority("READ_ROLE_PERMISSION");
        http.authorizeRequests().antMatchers(DELETE, basePath + "/roles/*/permissions").hasAnyAuthority("READ_ROLE_PERMISSION");
        http.authorizeRequests().antMatchers(POST, basePath + "/roles/*/permissions").hasAnyAuthority("READ_ROLE_PERMISSION");




        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(customAuthenticationFilter);
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.exceptionHandling()
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }
}


