package com.zehut.market.web.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;

@Component
public class JWTUtil {
    private static final String KEY = "z3hu7";
    public String generateToken(UserDetails userDetails){

        /*return Jwts.builder().setSubject(userDetails.getUsername()).setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, KEY).compact();*/
        return Jwts.builder().setClaims(new HashMap<>()).setSubject(userDetails.getUsername())
                .setIssuedAt(new Date()).setExpiration(new Date(System.currentTimeMillis() + (10006060*10)))
                .signWith(SignatureAlgorithm.HS256,KEY).compact();
    }

    public boolean validateToken(String token, UserDetails userDetails){

        return userDetails.getUsername().equals(extractUsername(token)) && !isTokenExpired(token);
    }

    public String extractUsername(String token){

        return getClaims(token).getSubject();
    }

    public boolean isTokenExpired(String token){

        return getClaims(token).getExpiration().before(new Date());
    }

    private Claims getClaims(String token){

        return Jwts.parser().setSigningKey(KEY).parseClaimsJws(token).getBody();
    }
}
