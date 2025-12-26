package com.myweb.job_portal.dto.response;

import com.myweb.job_portal.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponse {
    private Long userId;
    private String email;
    private UserRole role;
}
