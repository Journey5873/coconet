package com.coconet.memberservice.security.config;

import com.coconet.memberservice.security.entrypoint.AuthenticationEntryPoint;
import com.coconet.memberservice.security.filter.JwtFilter;
import com.coconet.memberservice.security.jwthandler.JwtAccessDeniedHandler;
import com.coconet.memberservice.security.oauth.Oauth2AuthenticationFailureHandler;
import com.coconet.memberservice.security.oauth.Oauth2AuthenticationSuccessHandler;
import com.coconet.memberservice.security.oauth.repository.CookieAuthorizationRequestRepository;
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
    private final Oauth2AuthenticationFailureHandler oauth2AuthenticationFailureHandler;
    private final CookieAuthorizationRequestRepository cookieAuthorizationRequestRepository;
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
            .exceptionHandling(configurer -> {
                configurer.authenticationEntryPoint(new AuthenticationEntryPoint(objectMapper));
            })

            //oauth2 관련 설정입니다.
            .oauth2Login(configurer -> {
                configurer.authorizationEndpoint(cus -> {
                    cus.baseUri("/oauth2/authorize");
                });
                configurer.authorizationEndpoint(cus -> cus.authorizationRequestRepository(cookieAuthorizationRequestRepository));
                configurer.redirectionEndpoint(cus -> cus.baseUri("/oauth2/callback/*"));
                configurer.userInfoEndpoint(cus -> cus.userService(oauth2UserService));
                configurer.successHandler(oauth2AuthenticationSuccessHandler);
//                configurer.failureHandler(oauth2AuthenticationFailureHandler);
            })
            .exceptionHandling(configurer -> {
                configurer.accessDeniedHandler(new JwtAccessDeniedHandler(objectMapper));
                configurer.authenticationEntryPoint(new AuthenticationEntryPoint(objectMapper));
            })
            .addFilterBefore(new JwtFilter(tokenProvider, userDetailsService), UsernamePasswordAuthenticationFilter.class)
            .build();
    }

}
