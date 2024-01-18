package com.coconet.gateway.filter;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import java.util.HashMap;
import java.util.Map;

@Component
public class AuthorisationHeaderFilter extends AbstractGatewayFilterFactory<AuthorisationHeaderFilter.Config> {

    @Value("${token.secret.key}")
    private String secretKey;

    public AuthorisationHeaderFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            if(!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                return onError(exchange, "No authorisation header", HttpStatus.BAD_REQUEST);
            }

            String authorisationHeader = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
            String jwt = authorisationHeader.replace("Bearer", "");

            try {
                String memberId = validationTokenWithUserId(jwt);
                if (memberId.equals("")) {
                    return onError(exchange, "Jwt is invalid", HttpStatus.BAD_REQUEST);
                }

                request.mutate().header("memberUUID", memberId);

                return chain.filter(exchange);

            }  catch (Exception e) {
                if (e instanceof SignatureException) {
                    return onError(exchange, "Invalid token", HttpStatus.BAD_REQUEST);
                }
                else if (e instanceof ExpiredJwtException) {
                    return onError(exchange, "Expired token", HttpStatus.BAD_REQUEST);
                }
                else {
                    return onError(exchange, "Token exception", HttpStatus.BAD_REQUEST);
                }
            }
        });
    }

    private Map<String, Object> validationTokenWithThrow(String jwt) throws SignatureException, ExpiredJwtException, Exception {
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());

        JwtParser parser = Jwts.parserBuilder()
                .setSigningKey(key)
                .build();

        Jws<Claims> result = parser.parseClaimsJws(jwt);
        return new HashMap<String, Object>(result.getBody());
    }

    public String validationTokenWithUserId(String authorizationToken) throws SignatureException, ExpiredJwtException, Exception{
        Map<String, Object> data = validationTokenWithThrow(authorizationToken);
        Object userEmail = data.get("memberUUID");
        return userEmail.toString();
    }

    private Mono<Void> onError(ServerWebExchange exchange, String errorMessage, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);

        byte[] bytes = errorMessage.getBytes();
        DataBuffer buffer = response.bufferFactory().wrap(bytes);
        return exchange.getResponse().writeWith(Flux.just(buffer));
    }

    public static class Config {
    }

}
