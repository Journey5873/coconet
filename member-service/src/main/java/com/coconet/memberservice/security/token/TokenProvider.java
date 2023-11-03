package com.coconet.memberservice.security.token;

import com.coconet.memberservice.common.errorcode.ErrorCode;
import com.coconet.memberservice.common.errorcode.TokenErrorCode;
import com.coconet.memberservice.common.exception.ApiException;
import com.coconet.memberservice.security.auth.MemberPrincipal;
import com.coconet.memberservice.security.token.dto.TokenDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
@Slf4j
public class TokenProvider {

    @Value("${token.secret.key}")
    private String secretKey;

    @Value("${token.access-token.plus-hour}")
    private Long accessTokenPlusHour;

    @Value("${token.refresh-token.plus-hour}")
    private Long refreshTokenPlusHour;



    public TokenDto issueAccessToken(MemberPrincipal principal) {

        Map<String, Object> data = new HashMap<>();
        data.put("memeberId", principal.getMember().getId());
        data.put("email", principal.getMember().getEmail());

        LocalDateTime expiredLocalDateTime = LocalDateTime.now().plusHours(accessTokenPlusHour);

        Date expiredAt = Date.from(
                expiredLocalDateTime.atZone(
                        ZoneId.systemDefault()
                ).toInstant()
        );

        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());

        String jwtToken = Jwts.builder()
                .signWith(key, SignatureAlgorithm.HS256)
                .setClaims(data)
                .setExpiration(expiredAt)
                .compact();

        return TokenDto.builder()
                .token(jwtToken)
                .expiredAt(expiredLocalDateTime)
                .build();

    }

    public TokenDto issueRefreshToken(MemberPrincipal principal) {

        Map<String, Object> data = new HashMap<>();
        data.put("memeberId", principal.getMember().getId());
        data.put("email", principal.getMember().getEmail());

        LocalDateTime expiredLocalDateTime = LocalDateTime.now().plusHours(refreshTokenPlusHour);

        Date expiredAt = Date.from(
                expiredLocalDateTime.atZone(
                        ZoneId.systemDefault()
                ).toInstant()
        );

        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());

        String jwtToken = Jwts.builder()
                .signWith(key, SignatureAlgorithm.HS256)
                .setClaims(data)
                .setExpiration(expiredAt)
                .compact();

        return TokenDto.builder()
                .token(jwtToken)
                .expiredAt(expiredLocalDateTime)
                .build();

    }

    public Map<String, Object> validationTokenWithThrow(String token) {

        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());

        JwtParser parser = Jwts.parserBuilder()
                .setSigningKey(key)
                .build();

        try {
            Jws<Claims> result = parser.parseClaimsJws(token);
            log.info("Jws result : {}", result.getBody());
            return new HashMap<String, Object>(result.getBody());

        } catch (Exception e) {

            if (e instanceof SignatureException) {
                throw new ApiException(TokenErrorCode.INVALID_TOKEN, "");
            }
            else if (e instanceof ExpiredJwtException) {
                throw new ApiException(TokenErrorCode.EXPIRED_TOKEN, "");
            }
            else {
                throw new ApiException(TokenErrorCode.TOKEN_EXCEPTION, "");
            }
        }
    }

    public String validationTokenWithUserEmail(String authorizationToken) {

        Map<String, Object> data = validationTokenWithThrow(authorizationToken);
        Object userEmail = data.get("email");
        Objects.requireNonNull(userEmail, () -> {
            throw new ApiException(ErrorCode.NULL_POINT, "");
        });
        return userEmail.toString();
    }
}
