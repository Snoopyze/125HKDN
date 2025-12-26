package com.myweb.job_portal.dto.response;

import com.myweb.job_portal.enums.UserRole;
import com.myweb.job_portal.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegisterResponse {
    private Long id;
    private String email;
    private String phone;
    private UserRole role;
    private UserStatus status;
}
