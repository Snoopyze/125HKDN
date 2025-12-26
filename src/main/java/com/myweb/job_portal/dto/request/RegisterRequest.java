package com.myweb.job_portal.dto.request;

import lombok.Data;

@Data
public class RegisterRequest {
    private String email;
    private String phone;
    private String password;
}
