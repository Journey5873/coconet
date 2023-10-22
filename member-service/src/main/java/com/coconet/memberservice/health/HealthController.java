package com.coconet.memberservice.health;

import com.coconet.memberservice.common.response.Response;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/open-api/health")
public class HealthController {

    @GetMapping("/")
    public Response<HealthResponse> health(){

        HealthResponse response = HealthResponse.builder()
                .id(1L)
                .name("coconet")
                .email("coconet@coconet.com")
                .build();

        return Response.OK(response);
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    static class HealthResponse {

        private Long id;
        private String name;
        private String email;
    }


}
