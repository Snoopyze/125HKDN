package com.myweb.job_portal.dto.request;

import com.myweb.job_portal.enums.ApplicationStatus;
import lombok.Data;

@Data
public class UpdateApplicationStatusRequest {
    private ApplicationStatus status;
}
