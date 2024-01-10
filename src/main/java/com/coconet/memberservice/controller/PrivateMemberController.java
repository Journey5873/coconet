package com.coconet.memberservice.controller;

import com.coconet.memberservice.common.response.Response;
import com.coconet.memberservice.dto.MemberRequestDto;
import com.coconet.memberservice.dto.MemberResponseDto;
import com.coconet.memberservice.service.MemberServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member-service/api")
@Tag(name = "Private Controller", description = "Access with authorisation")
public class PrivateMemberController {

    private final MemberServiceImpl memberServiceImpl;

    @Operation(
            summary = "Get member info",
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
                    ),
            }
    )
    @GetMapping("/my-profile")
    public Response<MemberResponseDto> getUserInfo(@RequestHeader(value="memberUUID") UUID memberUUID) {
        MemberResponseDto memberResponseDto = memberServiceImpl.getUserInfo(memberUUID);
        return Response.OK(memberResponseDto);
    }

    @Operation(
            summary = "Update member info",
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
                    ),
            }
    )
    @PutMapping("/my-profile")
    public Response<MemberResponseDto> updateUserInfo(@RequestPart("requestDto") MemberRequestDto requestDto, @RequestPart("imageFile") MultipartFile imageFile, @RequestHeader(value="memberUUID") UUID memberUUID) {
        MemberResponseDto memberResponseDto = memberServiceImpl.updateUserInfo(memberUUID, requestDto, imageFile);
        return Response.OK(memberResponseDto);
    }

    @Operation(
            summary = "Inactivate member",
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
                    ),
            }
    )
    @DeleteMapping("/delete")
    public Response<MemberResponseDto> deleteUser(@RequestHeader(value="memberUUID") UUID memberUUID) {
        MemberResponseDto memberResponseDto = memberServiceImpl.deleteUser(memberUUID);
        return Response.OK(memberResponseDto);
    }
}