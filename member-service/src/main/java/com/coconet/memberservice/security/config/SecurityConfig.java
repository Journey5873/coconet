package com.coconet.memberservice.security.config;

import com.coconet.memberservice.security.jwthandler.JwtAccessDeniedHandler;
import com.coconet.memberservice.security.oauth.Oauth2AuthenticationSuccessHandler;
import com.coconet.memberservice.security.oauth.service.Oauth2UserService;
import com.coconet.memberservice.security.token.TokenProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final ObjectMapper objectMapper;
    private final Oauth2UserService oauth2UserService;
    private final Oauth2AuthenticationSuccessHandler oauth2AuthenticationSuccessHandler;
    private final TokenProvider tokenProvider;
    private final UserDetailsService userDetailsService;

    private List<String> SWAGGER = List.of(
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/v3/api-docs/**"
    );

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        return http
                .httpBasic(AbstractHttpConfigurer::disable) //rest api를 사용하므로 비활성화
                .csrf(AbstractHttpConfigurer::disable)  // token을 사용하므로 비활성화
                .cors(AbstractHttpConfigurer::disable)        //cors 추후 활성화 필요
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .sessionManagement(configurer -> {
                    configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                .authorizeHttpRequests(request -> {
                    request.requestMatchers("/api/**").authenticated();
                    request.anyRequest().permitAll();
                })

                .oauth2Login(oauth2Configurer -> oauth2Configurer
                        .userInfoEndpoint(cus -> cus.userService(oauth2UserService))
                        .authorizationEndpoint(cus -> cus.baseUri("/member-service/open-api/oauth2/authorize"))
                        .successHandler(oauth2AuthenticationSuccessHandler)
                )

            .exceptionHandling(configurer -> {
                configurer.accessDeniedHandler(new JwtAccessDeniedHandler(objectMapper));
            })
            .build();
    }
}

