package com.coconet.memberservice.security.oauth;

import com.coconet.memberservice.security.auth.MemberPrincipal;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
@RequiredArgsConstructor
@Slf4j
public class Oauth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {

        if (response.isCommitted()) {
            log.debug("Response has already been committed.");
            return;
        }
        clearAuthenticationAttributes(request);

        MemberPrincipal principal = (MemberPrincipal) ((OAuth2AuthenticationToken) authentication).getPrincipal();
        if(principal.getUsername().equals("Undefined")) {
            // 추가정보 받는 곳
            getRedirectStrategy().sendRedirect(request, response, "/undefined/"+ principal.getName());

        } else {
            // login
            Cookie cookie = new Cookie("memberId", principal.getName());
            cookie.setPath("/");
            cookie.setMaxAge(60*60*24*30); // 30일 동안 쿠키 유지.
            cookie.setSecure(true);
            response.addCookie(cookie);

            getRedirectStrategy().sendRedirect(request, response, "/member-service/open-api/login");

        }
    }

}
