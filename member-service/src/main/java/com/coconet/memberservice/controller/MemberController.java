package com.coconet.memberservice.controller;

import com.coconet.memberservice.common.response.Response;
import com.coconet.memberservice.dto.*;
import com.coconet.memberservice.security.auth.MemberPrincipal;
import com.coconet.memberservice.security.oauth.model.AuthProvider;
import com.coconet.memberservice.security.token.TokenProvider;
import com.coconet.memberservice.security.token.converter.TokenConverter;
import com.coconet.memberservice.security.token.dto.TokenDto;
import com.coconet.memberservice.security.token.dto.TokenResponse;
import com.coconet.memberservice.service.MemberServiceImpl;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.Collections;

import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member-service/open-api")
public class MemberController {

    private final MemberServiceImpl memberServiceImpl;
    private final RestTemplate restTemplate;

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String googleClientId;
    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String googleSecret;
    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String googleRedirectUri;
    @Value("${spring.security.oauth2.client.registration.github.client-id}")
    private String githubClientId;
    @Value("${spring.security.oauth2.client.registration.github.client-secret}")
    private String githubSecret;
    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String kakaoClientId;
    @Value("${spring.security.oauth2.client.registration.kakao.client-secret}")
    private String kakaoSecret;
    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String kakaoRedirectUri;

    @GetMapping("/github")
    public Response<TokenResponse> githubCallback(@RequestParam("code") String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<AccessTokenRequest> entity = new HttpEntity<>(new AccessTokenRequest(githubClientId, githubSecret, code), headers);
        String accessToken = restTemplate.postForObject(
                "https://github.com/login/oauth/access_token",
                entity,
                AccessTokenResponse.class).getAccess_token();

        headers.setBearerAuth(accessToken);
        HttpEntity<Void> request = new HttpEntity<>(headers);
        String email = restTemplate.exchange(
                "https://api.github.com/user",
                HttpMethod.GET,
                request,
                AccessInfoResponse.class
        ).getBody().getLogin();
        email += "@github.com";

        if(!memberServiceImpl.existMember(email)) {
            String memberId = memberServiceImpl.preRegister(AuthProvider.github, email);
            return Response.OK(TokenResponse.builder()
                    .memberId(memberId).build());
        }

        TokenResponse result = memberServiceImpl.login(email);
        return Response.OK(result);
    }

    @GetMapping("/google")
    public Response<TokenResponse> googleCallback(@RequestParam("code") String code) throws ParseException {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<AccessGoogleTokenRequest> entity = new HttpEntity<>(new AccessGoogleTokenRequest(googleClientId, googleSecret, code, "authorization_code"
                , googleRedirectUri), headers);

        String accessToken = restTemplate.postForObject(
                "https://oauth2.googleapis.com/token",
                entity,
                GoogleTokenResponse.class).getId_token();

        Base64.Decoder decoder = Base64.getUrlDecoder();
        String[] chunks = accessToken.split("\\.");

        String payload = new String(decoder.decode(chunks[1]));

        JSONParser jsonParser = new JSONParser();

        JSONObject jsonObject = (JSONObject) jsonParser.parse(payload);
        String email = (String) jsonObject.get("email");

        if(!memberServiceImpl.existMember(email)) {
            String memberId = memberServiceImpl.preRegister(AuthProvider.google, email);
            return Response.OK(TokenResponse.builder()
                    .memberId(memberId).build());
        }

        TokenResponse result = memberServiceImpl.login(email);
        return Response.OK(result);
    }

    @GetMapping("/kakao")
    public Response<TokenResponse> kakaoCallback(@RequestParam("code") String code) throws ParseException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", kakaoClientId);
        params.add("redirect_uri", kakaoRedirectUri);
        params.add("client_secret", kakaoSecret);
        params.add("code", code);

        HttpEntity<MultiValueMap<String, String>> kakaoRequest = new HttpEntity<>(params, headers);

        String response = restTemplate.postForObject(
                "https://kauth.kakao.com/oauth/token",
                kakaoRequest,
                String.class);

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(response);
        String accessToken = jsonObject.getAsString("access_token");

        headers.add("Authorization", "Bearer " + accessToken);
        kakaoRequest = new HttpEntity<>(headers);

        response = restTemplate.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.GET,
                kakaoRequest,
                String.class).getBody();

        jsonObject = (JSONObject) jsonParser.parse(response);
        String accountInfo = jsonObject.getAsString("kakao_account");
        jsonObject = (JSONObject) jsonParser.parse(accountInfo);
        String email = jsonObject.getAsString("email");
        email = email.substring(0, email.indexOf("@")) + "@kakao.com";

        if(!memberServiceImpl.existMember(email)) {
            String memberId = memberServiceImpl.preRegister(AuthProvider.kakao, email);
            return Response.OK(TokenResponse.builder()
                    .memberId(memberId).build());
        }

        TokenResponse result = memberServiceImpl.login(email);
        return Response.OK(result);
    }

    @GetMapping("/my-profile")
    public Response<MemberResponseDto> getUserInfo(@AuthenticationPrincipal MemberPrincipal memberPrincipal) {
        String id = memberPrincipal.getMember().getMemberId();
        MemberResponseDto memberResponseDto = memberServiceImpl.getUserInfo(id);
        return Response.OK(memberResponseDto);
    }

    @PostMapping("/register")
    public Response<TokenResponse> register(@RequestBody MemberRegisterRequestDto requestDto) {
        TokenResponse token = memberServiceImpl.register(requestDto);

        return Response.OK(token);
    }

    @PutMapping("/my-profile")
    public Response<MemberResponseDto> updateUserInfo(@RequestPart("requestDto") MemberRequestDto requestDto, @RequestPart("imageFile") MultipartFile imageFile, @AuthenticationPrincipal MemberPrincipal memberPrincipal) {
        String id = memberPrincipal.getMember().getMemberId();
        MemberResponseDto memberResponseDto = memberServiceImpl.updateUserInfo(id, requestDto, imageFile);
        return Response.OK(memberResponseDto);
    }

    @DeleteMapping("/delete")
    public Response<MemberResponseDto> deleteUser(@AuthenticationPrincipal MemberPrincipal memberPrincipal) {
        String id = memberPrincipal.getMember().getMemberId();
        MemberResponseDto memberResponseDto = memberServiceImpl.deleteUser(id);
        return Response.OK(memberResponseDto);
    }

    @GetMapping("/health")
    public String healthCheck() {
        return "Hello world";
    }
}