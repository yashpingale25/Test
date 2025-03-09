package com.app1.service;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Date;

@Service
public class JWTService {

    // TO GENERATE JWT TOKEN
    @Value("${jwt.key}")
    private String algorithmKey;                   //all the values for thrice are located in application properties

    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${jwt.expiry}")
    private int expiry;

    private Algorithm algorithm;

    @PostConstruct   //post construct will automatically call this method
    public void postConstruct() throws UnsupportedEncodingException {
        algorithm = Algorithm.HMAC256(algorithmKey);

    }

    // NOW GENERATING TOKEN ---------computer engineer always unemployed
    public String generateToken(String username) {
        return JWT.create()
                // compute engineer is always unemp.
                .withClaim("username", username)
                .withExpiresAt(new Date(System.currentTimeMillis() + expiry))
                .withIssuer(issuer)
                .sign(algorithm);

    }



    public String getUsername(String token) {                                   // now this method will used to extract username from the token
                                                                               // it will validate our token 1st: it will take the algorithem(signature+hmac256)
                                                                             //  2nd: to verify the token algo. needs to decode it
                                                                           //3rd: verify method will check that whether  the signature is valid
        DecodedJWT decodedToken = JWT.require(algorithm)                                  // now we have to extract the name
                .withIssuer(issuer)
                .build()
                .verify(token);
       return decodedToken.getClaim("username").asString();
    }
}
