package com.coconet.chatservice.filter;

import com.coconet.chatservice.common.errorcode.TokenErrorCode;
import com.coconet.chatservice.common.exception.ApiException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.SignatureException;
import java.util.HashMap;
import java.util.Map;

@Component
public class TokenValidator {
    @Value("${token.secret.key}")
    private String secretKey;

    public String validationTokenWithThrow(String token) {

        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());

        JwtParser parser = Jwts.parserBuilder()
                .setSigningKey(key)
                .build();

        try {
            Jws<Claims> result = parser.parseClaimsJws(token);
            return result.getBody().get("memberUUID").toString();

        } catch (Exception e) {

            if (e instanceof SignatureException) {
                throw new ApiException(TokenErrorCode.INVALID_TOKEN, e.getMessage());
            }
            else if (e instanceof ExpiredJwtException) {
                throw new ApiException(TokenErrorCode.EXPIRED_TOKEN, e.getMessage());
            }
            else {
                throw new ApiException(TokenErrorCode.TOKEN_EXCEPTION, e.getMessage());
            }
        }
    }
}
