package com.coconet.articleservice.controller;

import com.coconet.articleservice.common.response.Response;
import com.coconet.articleservice.dto.ApplicationDto;
import com.coconet.articleservice.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/article-service/api")
public class PrivateApplicationController {

    private final ApplicationService applicationService;

    @PostMapping("/apply")
    public Response<ApplicationDto> apply(@RequestBody ApplicationDto applicationDto, @RequestHeader(value = "memberUUID") UUID memberUUID) {
        return Response.OK(applicationService.apply(applicationDto, memberUUID));
    }

    @GetMapping("/my-applications")
    public Response<Page<ApplicationDto>> getMyApplications(@RequestHeader(value = "memberUUID") UUID memberUUID, Pageable pageable) {
        return Response.OK(applicationService.getMyApplications(memberUUID, pageable));
    }
}
