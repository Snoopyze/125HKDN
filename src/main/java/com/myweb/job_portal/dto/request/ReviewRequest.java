package com.myweb.job_portal.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewRequest {

    private Long jobApplicationId;
    private Long reviewerId;
    private Long revieweeId;
    private Integer rating;
}
