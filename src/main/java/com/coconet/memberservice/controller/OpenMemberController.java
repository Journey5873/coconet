package com.coconet.memberservice.controller;

import com.coconet.memberservice.common.response.Response;
import com.coconet.memberservice.dto.AccessTokenRequest;
import com.coconet.memberservice.dto.MemberRegisterRequestDto;
import com.coconet.memberservice.dto.client.MemberClientDto;
import com.coconet.memberservice.security.oauthModel.AuthProvider;
import com.coconet.memberservice.security.token.dto.TokenResponse;
import com.coconet.memberservice.service.MemberServiceImpl;
import com.coconet.memberservice.service.MemberTokenService;
import com.coconet.memberservice.service.MemberValidationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.Collections;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member-service/open-api")
@Tag(name = "Public Controller", description = "Access without authorisation")
public class OpenMemberController {

    private final MemberServiceImpl memberServiceImpl;
    private final MemberValidationService memberValidationService;
    private final MemberTokenService memberTokenService;
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
    @Value("${frontend.URI}")
    private String frontURI;


    @Operation(
            summary = "Github call back",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "No member found",
                            content = @Content(
                                    schema = @Schema(name = "Response")
                            )
                    )
            }
    )
    @GetMapping("/github")
    public ResponseEntity<TokenResponse> githubCallback(@RequestParam("code") String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        AccessTokenRequest accessTokenRequest = AccessTokenRequest.builder()
                .client_id(githubClientId)
                .client_secret(githubSecret)
                .code(code)
                .build();
        HttpEntity<AccessTokenRequest> entity = new HttpEntity<>(accessTokenRequest, headers);

        String response = restTemplate.postForObject(
                "https://github.com/login/oauth/access_token",
                entity,
                String.class);

        String accessToken = memberTokenService.getJsonValue(response, "access_token");

        headers.setBearerAuth(accessToken);
        HttpEntity<Void> request = new HttpEntity<>(headers);

        response = restTemplate.exchange(
                "https://api.github.com/user",
                HttpMethod.GET,
                request,
                String.class
        ).getBody();

        String email = memberTokenService.getJsonValue(response, "login");
        email += "@github.com";

        if(!memberValidationService.existMember(email)) {
            UUID memberId = memberServiceImpl.preRegister(AuthProvider.github, email);
            return ResponseEntity.status(HttpStatus.FOUND)
                    .location(URI.create(frontURI+ "?memberId=" + memberId.toString()))
                    .build();
        }

        TokenResponse result = memberServiceImpl.login(email);

        if(result.getMemberUUID() != null)
            return ResponseEntity.status(HttpStatus.FOUND)
                    .location(URI.create(frontURI+ "?memberId=" + result.getMemberUUID().toString()))
                    .build();

        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(frontURI+ "?accessToken=" + result.getAccessToken().toString()))
                .build();
    }

    @Operation(
            summary = "Google call back",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK"
                    )
            }
    )
    @GetMapping("/google")
    public ResponseEntity<TokenResponse> googleCallback(@RequestParam("code") String code) {
        HttpHeaders headers = new HttpHeaders();
        AccessTokenRequest accessTokenRequest = AccessTokenRequest.builder()
                .client_id(googleClientId)
                .client_secret(googleSecret)
                .code(code)
                .grant_type("authorization_code")
                .redirect_uri(googleRedirectUri)
                .build();
        HttpEntity<AccessTokenRequest> entity = new HttpEntity<>(accessTokenRequest, headers);

        String response = restTemplate.postForObject(
                "https://oauth2.googleapis.com/token",
                entity,
                String.class);

        String accessToken = memberTokenService.getJsonValue(response, "id_token");
        String payload = memberTokenService.decodeJwt(accessToken);
        String email = memberTokenService.getJsonValue(payload, "email");

        if(!memberValidationService.existMember(email)) {
            UUID memberId = memberServiceImpl.preRegister(AuthProvider.google, email);
            return ResponseEntity.status(HttpStatus.FOUND)
                    .location(URI.create(frontURI+"?memberId=" + memberId.toString()))
                    .build();
        }

        TokenResponse result = memberServiceImpl.login(email);

        if(result.getMemberUUID() != null)
            return ResponseEntity.status(HttpStatus.FOUND)
                    .location(URI.create(frontURI+"?memberId=" + result.getMemberUUID().toString()))
                    .build();

        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(frontURI+"?accessToken=" + result.getAccessToken().toString()))
                .build();
    }

    @Operation(
            summary = "Kakao call back",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK"
                    )
            }
    )
    @GetMapping("/kakao")
    public ResponseEntity<TokenResponse> kakaoCallback(@RequestParam("code") String code){
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

        String accessToken = memberTokenService.getJsonValue(response, "access_token");

        headers.add("Authorization", "Bearer " + accessToken);
        kakaoRequest = new HttpEntity<>(headers);

        response = restTemplate.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.GET,
                kakaoRequest,
                String.class).getBody();

        String accountInfo = memberTokenService.getJsonValue(response, "kakao_account");
        String email = memberTokenService.getJsonValue(accountInfo, "email");
        email = email.substring(0, email.indexOf("@")) + "@kakao.com";

        if(!memberValidationService.existMember(email)) {
            UUID memberId = memberServiceImpl.preRegister(AuthProvider.kakao, email);
            return ResponseEntity.status(HttpStatus.FOUND)
                    .location(URI.create(frontURI+"?memberId=" + memberId.toString()))
                    .build();
        }

        TokenResponse result = memberServiceImpl.login(email);

        if(result.getMemberUUID() != null)
            return ResponseEntity.status(HttpStatus.FOUND)
                    .location(URI.create(frontURI+"?memberId=" + result.getMemberUUID().toString()))
                    .build();

        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(frontURI+"?accessToken=" + result.getAccessToken().toString()))
                .build();
    }

    @Operation(
            summary = "Register member with additional info",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "No member found",
                            content = @Content(
                                    schema = @Schema(name = "Response")
                            )
                    )
            }
    )
    @PostMapping("/register")
    public Response<TokenResponse> register(@Valid @RequestPart("requestDto") MemberRegisterRequestDto requestDto, @RequestPart("imageFile") MultipartFile imageFile) {
        TokenResponse token = memberServiceImpl.register(requestDto, imageFile);

        return Response.OK(token);
    }

    @Operation(
            summary = "Oauth2 entry point"
    )
    @GetMapping(value = {"/oauth2/authorize/google", "/oauth2/authorize/github",
            "/oauth2/authorize/kakao"})
    public void authorise() {
    }

    @Operation(
            summary = "Health test",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK"
                    )
            }
    )
    @GetMapping("/health")
    public String healthCheck() {
        return "Hello world";
    }

    @GetMapping("/clientMemberAllInfo/{memberUUID}")
    public Response<MemberClientDto> clientMemberAllInfo(@PathVariable UUID memberUUID) {
        MemberClientDto memberClientDto = memberServiceImpl.clientMemberAllInfo(memberUUID);
        return Response.OK(memberClientDto);
    }

    @GetMapping("/clientMemberProfile/{memberUUID}")
    public Response<MemberClientDto> clientMemberProfile(@PathVariable UUID memberUUID) {
        MemberClientDto memberClientDto = memberServiceImpl.clientMemberProfile(memberUUID);
        return Response.OK(memberClientDto);
    }

    @GetMapping("/chatClient/{memberUUID}")
    public Response<MemberClientDto> clientMemberForChat(@PathVariable UUID memberUUID) {
        MemberClientDto memberClientDto = memberServiceImpl.clientMemberForChat(memberUUID);
        return Response.OK(memberClientDto);
    }

    @GetMapping("/memberNameCheck/{name}")
    public Response<Boolean> checkMemberNickName(@PathVariable String name) {
        Boolean isPresent = memberServiceImpl.checkMemberNickName(name);
        return Response.OK(isPresent);
    }

}
