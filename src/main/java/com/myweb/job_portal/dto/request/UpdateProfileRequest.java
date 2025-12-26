package com.myweb.job_portal.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateProfileRequest {
    @NotBlank(message = "Họ tên không được để trống")
    private String fullName;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không đúng định dạng")
    private String email;

    @NotBlank(message = "Số điện thoại không được để trống")
    private String phone;

    @NotBlank(message = "Địa chỉ không được để trống")
    private String streetAddress;

    @NotBlank(message = "Phường/Xã không được để trống")
    private String wardAddress;

    @NotBlank(message = "Tỉnh/Thành phố không được để trống")
    private String provinceAddress;
}
