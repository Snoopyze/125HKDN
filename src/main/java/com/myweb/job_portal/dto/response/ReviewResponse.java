package com.myweb.job_portal.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ReviewResponse {

    private Long id;
    private Long jobApplicationId;
    private Long reviewerId;
    private Long revieweeId;
    private Integer rating;
    private Boolean isPublished;
    private LocalDateTime createdAt;
}
