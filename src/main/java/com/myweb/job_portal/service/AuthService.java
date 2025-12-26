package com.myweb.job_portal.service;

import com.myweb.job_portal.dto.request.LoginRequestByAn;
import com.myweb.job_portal.dto.request.RegisterRequest;
import com.myweb.job_portal.dto.response.LoginResponse;
import com.myweb.job_portal.dto.response.RegisterResponse;

public interface AuthService {

    RegisterResponse register(RegisterRequest request);

    LoginResponse login(LoginRequestByAn request);
}
