//package com.example.base.config;
//
//import io.jsonwebtoken.*;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.function.Function;
//
//@Component
//public class JwtUtil {
//
//
//    @Value("${polytech.dictionaryapi.jwtExpiration}")
//    private int jwtExpiration;
//
//    public String generateJwtToken(Authentication authentication) {
//
//        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
//
//        return Jwts.builder()
//                .setSubject(userPrincipal.getUsername())
//                .setIssuedAt(new Date())
//                .setExpiration(new Date((new Date()).getTime() + jwtExpiration))
//                .signWith(SignatureAlgorithm.HS512, jwtSecret)
//                .compact();
//    }
//
//    public String getUserNameFromJwtToken(String token) {
//        return Jwts.parser()
//                .setSigningKey(jwtSecret)
//                .parseClaimsJws(token)
//                .getBody()
//                .getSubject();
//    }
//
//    public Boolean validateJwtToken(String authToken) {
//        try {
//            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
//            return true;
//        }  catch (SignatureException e) {
//            logger.error("SignatureException caught: Invalid JWT signature. Message: {} ", e.getMessage());
//        } catch (MalformedJwtException e) {
//            logger.error("MalformedJwtException caught: Invalid JWT token. Message: {}", e.getMessage());
//        } catch (ExpiredJwtException e) {
//            logger.error("ExpiredJwtException caught: Expired JWT token. Message: {}", e.getMessage());
//        } catch (UnsupportedJwtException e) {
//            logger.error("UnsupportedJwtException caught: Unsupported JWT token. Message: {}", e.getMessage());
//        } catch (IllegalArgumentException e) {
//            logger.error("IllegalArgumentException caught: JWT claims string is empty. Message: {}", e.getMessage());
//        }
//
//        return false;
//    }
//}
//
