package com.poc.support.jwt;

import com.poc.support.dto.MsgInfo;
import com.poc.support.util.LogUtil;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public class JwtParser {
    private static final Logger LOG = LoggerFactory.getLogger(JwtParser.class);

    private final String secret;
    private final int expiration;

    public JwtParser(String jwtSecret, int jwtExpirationMs) {
        Objects.requireNonNull(jwtExpirationMs);
        Objects.requireNonNull(jwtSecret);
        this.secret = jwtSecret;
        this.expiration = jwtExpirationMs;
    }

    public  String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public  <T> T extractClaim(String token, Function<Claims,T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private  Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    private boolean isTokenExpired(String token) {
        return (new Date()).after(extractExpiration(token));
    }

    public String generateToken(String email) {
        Map<String,Object> claims = new HashMap<>();
        return createToken(claims, email);
    }

    private  String createToken(Map<String,Object> claims, String subject) {
        // 512 bit allowd 64 bit secretkey, 256 bit allowed 32 bit secret key...
        return Jwts.builder().setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public  boolean validateToken(String token, String email) {
        final String userName = extractUserName(token);
        return (userName.equals(email)) && !isTokenExpired(token);
    }

    public  boolean validateJwtToken(String authToken) {
        try {
            extractAllClaims(authToken);
            return true;
        } catch (SignatureException e) {
            LogUtil.INFO.apply(LOG, MsgInfo.of("Invalid JWT signature: {}", e.getMessage()));
        } catch (MalformedJwtException e) {
            LogUtil.INFO.apply(LOG, MsgInfo.of("Invalid JWT token: {}", e.getMessage()));
        } catch (ExpiredJwtException e) {
            LogUtil.INFO.apply(LOG,MsgInfo.of("JWT token is expired: {}", e.getMessage()));
        } catch (UnsupportedJwtException e) {
            LogUtil.INFO.apply(LOG, MsgInfo.of("JWT token is unsupported: {}", e.getMessage()));
        } catch (IllegalArgumentException e) {
            LogUtil.INFO.apply(LOG, MsgInfo.of("JWT claims string is empty: {}", e.getMessage()));
        }
        return false;
    }
}
