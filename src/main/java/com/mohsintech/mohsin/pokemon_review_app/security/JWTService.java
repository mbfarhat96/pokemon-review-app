package com.mohsintech.mohsin.pokemon_review_app.security;

import java.util.Date;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.function.Function;
//import java.security.KeyPair;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JWTService {
    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + SecurityConstants.JWT_EXPIRATION);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt( new Date())
                .setExpiration(expireDate)
                .signWith(key,SignatureAlgorithm.HS512)
                .compact();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isExpired(token));
    }

    //claims must be extracted to actually validate them against server retrieved user info.
    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build().parseClaimsJws(token).getBody();
    }

    private <T> T extractClaim(String token, Function<Claims,T> claimResolver){
        final Claims claim = extractAllClaims(token);
        return claimResolver.apply(claim);

    }
    //extract the user from TOKEN.
    public String extractUsername(String token) {
        return
                extractClaim(token,Claims::getSubject);
    }
    //extract expiration date from the TOKEN.
    private Date extractExpirationDate(String token){
        return
                extractClaim(token,Claims::getExpiration);
    }
    //check whether TOKEN DATE is expired or not, meaning is it past the current date or before. Returns false if token
    //is valid
    private boolean isExpired(String token){
        Date expirationDate = extractExpirationDate(token);
        return expirationDate.before(new Date());
    }

}