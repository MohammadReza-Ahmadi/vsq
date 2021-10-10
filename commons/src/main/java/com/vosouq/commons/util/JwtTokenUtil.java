package com.vosouq.commons.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class JwtTokenUtil {

    private static final String JWT_TOKEN_SECRET = "dKc54155a7fiQp924942d52a384Xc215c5df7de26f831EeF8051ab4c74A363z";
    private static final String JWT_TOKEN_VALIDITY_IN_SECONDS = "1000";

    private JwtTokenUtil() {
    }

    public static String getUserIdFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public static <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private static Claims getAllClaimsFromToken(String token) {
        return Jwts
                .parser()
                .setSigningKey(getSigningKey())
                .parseClaimsJws(token)
                .getBody();
    }

    public static Boolean isTokenExpired(String token) {
        try {
            getUserIdFromToken(token);
            return false;
        } catch (Throwable throwable) {
            return true;
        }
    }

    public static String generateToken(String userId, Map<String, Object> claims){
        return doGenerateToken(userId, claims);
    }

    public static String generateToken(String userId, long deviceId, String phoneNumber, String clientId, String clientName) {
        Map<String, Object> claims = new HashMap<>();

        claims.put("deviceId", deviceId);
        claims.put("phoneNumber", phoneNumber);
        claims.put("clientId", clientId);
        claims.put("clientName", clientName);

        return doGenerateToken(userId, claims);
    }

    private static String doGenerateToken(String userId, Map<String, Object> claims) {
        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(userId)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(JWT_TOKEN_VALIDITY_IN_SECONDS) * 1000))
                .signWith(SignatureAlgorithm.HS512, getSigningKey())
                .compact();
    }

    private static String getSigningKey() {
        return Base64.getEncoder().encodeToString(JWT_TOKEN_SECRET.getBytes());
    }

}
