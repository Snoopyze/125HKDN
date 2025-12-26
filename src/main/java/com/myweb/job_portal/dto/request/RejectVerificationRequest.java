package com.myweb.job_portal.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RejectVerificationRequest {
    @NotBlank(message = "Lý do từ chối không được để trống")
    private String reason;
}
