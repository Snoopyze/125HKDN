package com.myweb.job_portal.controller;

import com.myweb.job_portal.dto.request.ReviewRequest;
import com.myweb.job_portal.dto.response.ReviewResponse;
import com.myweb.job_portal.service.ReviewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewsController {

    private final ReviewsService reviewsService;

    @PostMapping
    public ResponseEntity<ReviewResponse> create(
            @RequestBody ReviewRequest request
    ) {
        return ResponseEntity.ok(reviewsService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReviewResponse> update(
            @PathVariable Long id,
            @RequestParam Integer rating
    ) {
        return ResponseEntity.ok(
                reviewsService.update(id, rating)
        );
    }

    @PutMapping("/{id}/publish")
    public ResponseEntity<ReviewResponse> publish(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                reviewsService.publish(id)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewResponse> getById(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                reviewsService.getById(id)
        );
    }

    @GetMapping("/job-application/{jobApplicationId}")
    public ResponseEntity<ReviewResponse> getByJobApplication(
            @PathVariable Long jobApplicationId
    ) {
        return ResponseEntity.ok(
                reviewsService.getByJobApplication(jobApplicationId)
        );
    }

    @GetMapping("/reviewer/{reviewerId}")
    public ResponseEntity<List<ReviewResponse>> getByReviewer(
            @PathVariable Long reviewerId
    ) {
        return ResponseEntity.ok(
                reviewsService.getByReviewer(reviewerId)
        );
    }

    @GetMapping("/reviewee/{revieweeId}")
    public ResponseEntity<List<ReviewResponse>> getByReviewee(
            @PathVariable Long revieweeId
    ) {
        return ResponseEntity.ok(
                reviewsService.getByReviewee(revieweeId)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id
    ) {
        reviewsService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
