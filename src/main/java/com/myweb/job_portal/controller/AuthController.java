package com.myweb.job_portal.controller;

import com.myweb.job_portal.dto.request.LoginRequestByAn;
import com.myweb.job_portal.dto.request.RegisterRequest;
import com.myweb.job_portal.dto.response.LoginResponse;
import com.myweb.job_portal.dto.response.RegisterResponse;
import com.myweb.job_portal.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public RegisterResponse register(
            @RequestBody RegisterRequest request
    ) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public LoginResponse login(
            @RequestBody LoginRequestByAn request
    ) {
        return authService.login(request);
    }
}
