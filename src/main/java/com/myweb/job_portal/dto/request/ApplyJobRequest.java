package com.myweb.job_portal.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ApplyJobRequest {
    @NotNull(message = "ID công việc không được để trống")
    private Long jobId;

    @NotNull(message = "ID ứng viên không được để trống")
    private Long userId;

    @NotNull(message = "Link CV không được để trống")
    private MultipartFile cvFile;
}
