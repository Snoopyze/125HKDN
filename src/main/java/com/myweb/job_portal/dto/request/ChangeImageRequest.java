package com.myweb.job_portal.dto.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ChangeImageRequest {

    private Long userId;
    private MultipartFile imageFile;
}
