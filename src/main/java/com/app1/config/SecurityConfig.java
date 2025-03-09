package com.app1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

@Configuration //this files automaticaly runs as we start the code
public class SecurityConfig {
    private JWTFilter jwtFilter;

    public SecurityConfig(JWTFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception { // here http is object where we will keep thst which data we want to config.


                      //h(cd)2
        http.csrf().disable().cors().disable();                     //this step is to save link from hacking attack ans cors() meaning we dont want to interact our api with any other any client like angular,react etc
                      //haap = to keep all requests open
        http.authorizeHttpRequests().anyRequest().permitAll();      // in this config we will tell whether we want to permit 1 method or all methods and this means it will permit all http requests which are coming and do not secure those
//        http.addFilterBefore(jwtFilter, AuthorizationFilter.class); // it says before running jwtfilter, run authorization filter
//        http.authorizeHttpRequests().requestMatchers("/api/v1/auth/signup",
//                                                                "/api/v1/auth/signin",
//                                                                 "/api/v1/auth/content-manager-signup",
//                                                                  "/api/v1/auth/blog-manager-signup",
//                                                                    "/api/v1/auth/login-otp")//IN REQUEST MATCHERS MENTIONED URI WILL GET ACCESS FOR ALL
//                .permitAll()
//                .requestMatchers("/api/v1/cars/add-car").hasRole("CONTENTMANAGER")      // [.hasRole]= allows only one role to access but [.hasAnyRole]= it will allow multiple role to access and linkes mentioned in [requestMatcher] will accessed by only those which are mentioned in it
//                .anyRequest().authenticated();

        return http.build();                                        // in this step we created one object http and we are providing info of that in spring via this step

    }

}
