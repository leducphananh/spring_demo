package com.example.demo.dto.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LoginResponse {
    private String accessToken;

    private String tokenType;

    private Long expiresIn;
}
