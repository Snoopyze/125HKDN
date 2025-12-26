package com.myweb.job_portal.service;

import com.myweb.job_portal.dto.request.ReviewRequest;
import com.myweb.job_portal.dto.response.ReviewResponse;

import java.util.List;

public interface ReviewsService {

    ReviewResponse create(ReviewRequest request);

    ReviewResponse update(Long id, Integer rating);

    ReviewResponse publish(Long id);

    ReviewResponse getById(Long id);

    ReviewResponse getByJobApplication(Long jobApplicationId);

    List<ReviewResponse> getByReviewer(Long reviewerId);

    List<ReviewResponse> getByReviewee(Long revieweeId);

    void delete(Long id);
}
