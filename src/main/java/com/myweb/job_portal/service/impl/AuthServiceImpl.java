package com.myweb.job_portal.service.impl;

import com.myweb.job_portal.dto.request.LoginRequestByAn;
import com.myweb.job_portal.dto.request.RegisterRequest;
import com.myweb.job_portal.dto.response.LoginResponse;
import com.myweb.job_portal.dto.response.RegisterResponse;
import com.myweb.job_portal.entity.Users;
import com.myweb.job_portal.enums.UserRole;
import com.myweb.job_portal.enums.UserStatus;
import com.myweb.job_portal.repository.UserRepository;
import com.myweb.job_portal.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    @Override
    public RegisterResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        if (userRepository.existsByPhone(request.getPhone())) {
            throw new RuntimeException("Phone already exists");
        }

        Users user = Users.builder()
                .email(request.getEmail())
                .phone(request.getPhone())
                .password(request.getPassword())
                .userRole(UserRole.candidate)     // mặc định
                .userStatus(UserStatus.active)
                .createdAt(LocalDateTime.now())
                .build();

        Users savedUser = userRepository.save(user);

        return new RegisterResponse(
                savedUser.getId(),
                savedUser.getEmail(),
                savedUser.getPhone(),
                savedUser.getUserRole(),
                savedUser.getUserStatus()
        );
    }

    @Override
    public LoginResponse login(LoginRequestByAn request) {

        Users user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if ((request.getPassword() == user.getPassword()
        )) {
            throw new RuntimeException("Invalid email or password");
        }

        if (user.getUserStatus() != UserStatus.active) {
            throw new RuntimeException("Account is not active");
        }

        // CHƯA JWT → trả thông tin cơ bản
        return new LoginResponse(
                user.getId(),
                user.getEmail(),
                user.getUserRole()
        );
    }
}
