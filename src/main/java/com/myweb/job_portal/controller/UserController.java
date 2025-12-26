package com.myweb.job_portal.controller;

import com.myweb.job_portal.dto.UserDTO;
import com.myweb.job_portal.dto.request.ChangeImageRequest;
import com.myweb.job_portal.dto.request.ChangePasswordRequest;
import com.myweb.job_portal.dto.request.LoginRequest;
import com.myweb.job_portal.entity.Users;
import com.myweb.job_portal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/user-login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {

        Users user = userService.login(loginRequest);

        if (user != null) {
            UserDTO userResponse = new UserDTO(
                    user.getId(),
                    user.getEmail(),
                    user.getPhone(),
                    user.getPassword(),
                    user.getUserRole().name(),
                    user.getUserStatus().name(),
                    user.getCreatedAt(),
                    user.getUpdatedAt()
            );

            return ResponseEntity.ok(userResponse);
        } else {
            // Đăng nhập thất bại
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Sai tài khoản hoặc mật ");
        }
    }

    @PutMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest request) {
        try {
            userService.changePassword(request);
            return ResponseEntity.ok("Đổi mật khẩu thành công!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(
            value = "/avatar",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadAvatar(
            @RequestParam Long userId,
            @RequestParam MultipartFile file
    ) {
        try {
            String newAvatarUrl = userService.updateAvatar(userId, file);
            return ResponseEntity.ok("Cập nhật thành công! Link ảnh mới: " + newAvatarUrl);

        }   catch (IOException e) {
                return ResponseEntity.internalServerError().body("Lỗi khi upload ảnh: " + e.getMessage());
        }   catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
