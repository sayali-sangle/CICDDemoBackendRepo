package com.bank.allservices.services;

import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.bank.allservices.models.JwtResponse;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

@Service
public class JWTService {

    private String secretKey = "";

    public JWTService() {
        try {
            KeyGenerator keygen = KeyGenerator.getInstance("HmacSHA256");
            SecretKey sk = keygen.generateKey();
            secretKey = Base64.getEncoder().encodeToString(sk.getEncoded());

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public JwtResponse generateToken(String username,long userId) {

        Map<String,Object> claims  = new HashMap<String,Object>();
        JwtResponse jwtresp = new JwtResponse();
        claims.put("user_id", userId);

       /* return Jwts.builder()
                .claims()
                .add(claims) // name of Map "claims"
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 60*60*30))
                .and()
                .signWith(getKey())
                .compact();*/
        
        String str = Jwts.builder()
        		.setClaims(claims)
        		.setSubject(username)
        		.setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 60*60*1000*30))
                .signWith(getKey())
                .compact();
        
        jwtresp.setJwtToken(str);
        jwtresp.setUsername(username);
        return jwtresp;
    }

    private SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUserName(String token) {
        // extract the username from jwt token
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
//        return Jwts.parserBuilder()
//                .verifyWith(getKey())
//                .build()
//                .parseSignedClaims(token)
//                .getPayload();
    	
    	JwtParser parser = Jwts.parserBuilder()
                .setSigningKey(getKey())  // Set the signing key to verify the token's signature
                .build();
    	return parser.parseClaimsJws(token).getBody();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    
    public Claims decodeToken(String token) {
        try {
        	JwtParser parser = Jwts.parserBuilder() // Use parserBuilder()
                    .setSigningKey(getKey())         // Set the secret key
                    .build();   
        	return parser.parseClaimsJws(token).getBody();
        } catch (SignatureException e) {
            // Invalid token signature
            return null;
        }
    }
}
