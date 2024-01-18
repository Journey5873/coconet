package com.coconet.memberservice.security.token;

import com.coconet.memberservice.dto.MemberTokenDto;
import com.coconet.memberservice.security.token.dto.TokenDto;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class TokenProvider {

    @Value("${token.secret.key}")
    private String secretKey;

    @Value("${token.access-token.plus-hour}")
    private Long accessTokenPlusHour;

    @Value("${token.refresh-token.plus-hour}")
    private Long refreshTokenPlusHour;

    public TokenDto issueAccessToken(MemberTokenDto memberTokenDto) {

        Map<String, Object> data = new HashMap<>();
        data.put("memberUUID", memberTokenDto.getMemberUUID());
        data.put("email", memberTokenDto.getEmail());

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

    public TokenDto issueRefreshToken(MemberTokenDto memberTokenDto) {

        Map<String, Object> data = new HashMap<>();
        data.put("memberUUID", memberTokenDto.getMemberUUID());
        data.put("email", memberTokenDto.getEmail());

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


}
