package com.app1.config;


import com.app1.entity.User;
import com.app1.repository.UserRepository;
import com.app1.service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

@Component
public class JWTFilter extends OncePerRequestFilter {

    private JWTService jwtService;
    private UserRepository userRepository;

    public JWTFilter(JWTService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService ;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,  //this will receive request(http url will come[header info i.e authorization])             //this code is to send token in backend
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
                  String token = request.getHeader("Authorization");
                  if(token!= null && token.startsWith("Bearer ")){             //this code is to remove bearer and space from the token
                       String jwtToken = token.substring(8,token.length());
                       String username = jwtService.getUsername(jwtToken);
                      Optional<User> opUser  = userRepository.findByUsername(username);
                      if(opUser.isPresent()){
                          User user = opUser.get();                            //now we will send this user object data to spring security to grant access for it
                                                                               // now we need authentication for username and password so we will make object like that only
                          UsernamePasswordAuthenticationToken
                                  authenticationToken = new UsernamePasswordAuthenticationToken(
                                          user,
                                          null,
                                           Collections.singleton(new SimpleGrantedAuthority(user.getRole()))
                          );
                                   //australia sets new world record
                                  authenticationToken.setDetails
                                          (new WebAuthenticationDetails(request));
                          SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                      }
                  }
                  filterChain.doFilter(request,response);

    }
}
