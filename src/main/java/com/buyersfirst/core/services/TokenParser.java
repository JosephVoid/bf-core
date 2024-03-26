package com.buyersfirst.core.services;

import org.jose4j.jwt.JwtClaims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.security.auth.message.AuthException;

@Component
public class TokenParser {
    @Autowired
    private JWTBuilder jwtBuilder;

    public String getUserId(String token) throws AuthException {
        JwtClaims claims = jwtBuilder.generateParseToken(token);
        String userId = claims.getClaimValue("userId").toString();
        return userId;
    }
}
