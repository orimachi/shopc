package backend.config.JWTConfig;

import backend.enums.EToken;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${spring.jwt.jwtSecretKey}")
    protected String jwtSecretKey;

    @Value("${spring.jwt.jwtRefreshSecretKey}")
    protected String jwtRefreshSecretKey;

    @Value("${spring.jwt.expirationMs}")
    protected int jwtExpirationMs;

    public String generateJwtToken(Authentication authentication) {
        UserDetailsImp userPrincipal = (UserDetailsImp) authentication.getPrincipal();
        Map<String, Object> customClaims = buildCustomClaims(
                "UUID",userPrincipal.getId(),
                "type", EToken.REFRESH,
                "roles", userPrincipal.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList()
        );
        return Jwts.builder()
                .subject((userPrincipal.getUsername()))
                .claims(customClaims)
                .issuedAt(new Date())
                .expiration(new Date(Instant.now().plus(jwtExpirationMs, ChronoUnit.SECONDS).toEpochMilli()))
                .signWith(secretKey())
                .compact();
    }

    public String generateRefreshTokenFromUsername(Authentication authentication) {
        UserDetailsImp userPrincipal = (UserDetailsImp) authentication.getPrincipal();
        Map<String, Object> customClaims = buildCustomClaims(
                "UUID",userPrincipal.getId(),
                "type", EToken.REFRESH,
                "roles", userPrincipal.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList()
        );
        return Jwts.builder()
                .subject(userPrincipal.getUsername())
                .claims(customClaims)
                .issuedAt(new Date())
                .expiration(new Date(Instant.now().plus(jwtExpirationMs, ChronoUnit.SECONDS).toEpochMilli()))
                .signWith(refreshSecretKey())
                .compact();
    }

    private SecretKey secretKey() {
        return Keys.hmacShaKeyFor(jwtSecretKey.getBytes(StandardCharsets.UTF_8));
    }

    private SecretKey refreshSecretKey() {
        return Keys.hmacShaKeyFor(jwtRefreshSecretKey.getBytes(StandardCharsets.UTF_8));
    }

    protected Map<String,Object> buildCustomClaims(Object...keyValuePairs){
        Map<String,Object> customClaims = new HashMap<>();
        for (int i = 0; i < keyValuePairs.length; i+=2){
            customClaims.put((String) keyValuePairs[i], keyValuePairs[i+1]);
        }
        return  customClaims;
    }

    protected SecretKey determineKey(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .verifyWith(secretKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (Exception ex) {
            claims = Jwts.parser()
                    .verifyWith(refreshSecretKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        }
        String tokenType = claims.get("type", String.class);
        return EToken.ACCESS.toString().equals(tokenType) ? secretKey() : refreshSecretKey();
    }

    protected Claims getAllClaimsFormToken(String token) {
        return Jwts.parser().verifyWith(determineKey(token)).build().parseSignedClaims(token).getPayload();
    }

    public String getUserNameFromJwtToken(String token) {
        return getAllClaimsFormToken(token).getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().verifyWith(determineKey(authToken)).build().parse(authToken);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }

}
