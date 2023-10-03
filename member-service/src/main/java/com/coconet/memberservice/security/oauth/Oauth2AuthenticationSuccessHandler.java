package com.coconet.memberservice.security.oauth;

import com.coconet.memberservice.security.oauth.repository.CookieAuthorizationRequestRepository;
import com.coconet.memberservice.security.oauth.utils.CookieUtils;
import com.coconet.memberservice.security.token.TokenProvider;
import com.coconet.memberservice.security.token.converter.TokenConverter;
import com.coconet.memberservice.security.token.dto.TokenDto;
import com.coconet.memberservice.security.token.dto.TokenResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.Optional;

import static com.coconet.memberservice.security.oauth.repository.CookieAuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME;

@Component
@RequiredArgsConstructor
@Slf4j
public class Oauth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String redirectUri;
    private final TokenProvider tokenProvider;
    private final TokenConverter tokenConverter;
    private final CookieAuthorizationRequestRepository cookieAuthorizationRequestRepository;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        String targetUrl = determineTargetUrl(request, response, authentication);

        if (response.isCommitted()) {
            log.debug("Response has already been committed.");
            return;
        }
        clearAuthenticationAttributes(request, response);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Optional<String> redirectUri = CookieUtils.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
                .map(Cookie::getValue);

        if (redirectUri.isPresent() && !isAuthorizedRedirectUri(redirectUri.get())) {
            throw new RuntimeException("redirect URIs are not matched.");
        }
        String targetUrl = redirectUri.orElse(getDefaultTargetUrl());

        //JWT 생성
        TokenDto accessToken = tokenProvider.issueAccessToken(authentication);
        TokenDto refreshToken = tokenProvider.issueRefreshToken(authentication);
        TokenResponse tokenResponse = tokenConverter.toResponse(accessToken, refreshToken);


        return UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("token", tokenResponse.getAccessToken())
                .build().toUriString();
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        cookieAuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }

    private boolean isAuthorizedRedirectUri(String uri) {
        URI clientRedirectUri = URI.create(uri);
        URI authorizedUri = URI.create(redirectUri);

        if (authorizedUri.getHost().equalsIgnoreCase(clientRedirectUri.getHost())
                && authorizedUri.getPort() == clientRedirectUri.getPort()) {
            return true;
        }
        return false;
    }

}
