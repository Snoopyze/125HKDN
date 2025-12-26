package com.myweb.job_portal.service.impl;

import com.myweb.job_portal.dto.request.ReviewRequest;
import com.myweb.job_portal.dto.response.ReviewResponse;
import com.myweb.job_portal.entity.JobApplications;
import com.myweb.job_portal.entity.Reviews;
import com.myweb.job_portal.entity.Users;
import com.myweb.job_portal.repository.JobApplicationRepository;
import com.myweb.job_portal.repository.ReviewRepository;
import com.myweb.job_portal.repository.UserRepository;
import com.myweb.job_portal.service.ReviewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewsServiceImpl implements ReviewsService {

    private final ReviewRepository reviewsRepository;
    private final UserRepository usersRepository;
    private final JobApplicationRepository jobApplicationsRepository;

    @Override
    public ReviewResponse create(ReviewRequest request) {

        JobApplications jobApplication =
                jobApplicationsRepository.findById(request.getJobApplicationId())
                        .orElseThrow(() -> new RuntimeException("Job application not found"));

        Users reviewer =
                usersRepository.findById(request.getReviewerId())
                        .orElseThrow(() -> new RuntimeException("Reviewer not found"));

        Users reviewee =
                usersRepository.findById(request.getRevieweeId())
                        .orElseThrow(() -> new RuntimeException("Reviewee not found"));

        Reviews reviews = Reviews.builder()
                .jobApplications(jobApplication)
                .reviewer(reviewer)
                .reviewee(reviewee)
                .rating(request.getRating())
                .isPublished(false)
                .build();

        return toResponse(reviewsRepository.save(reviews));
    }

    @Override
    public ReviewResponse update(Long id, Integer rating) {

        Reviews reviews = reviewsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        reviews.setRating(rating);

        return toResponse(reviewsRepository.save(reviews));
    }

    @Override
    public ReviewResponse publish(Long id) {

        Reviews reviews = reviewsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        reviews.setIsPublished(true);

        return toResponse(reviewsRepository.save(reviews));
    }

    @Override
    public ReviewResponse getById(Long id) {
        return toResponse(
                reviewsRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Review not found"))
        );
    }

    @Override
    public ReviewResponse getByJobApplication(Long jobApplicationId) {
        return toResponse(
                reviewsRepository.findByJobApplications_Id(jobApplicationId)
                        .orElseThrow(() -> new RuntimeException("Review not found"))
        );
    }

    @Override
    public List<ReviewResponse> getByReviewer(Long reviewerId) {
        return reviewsRepository.findByReviewer_Id(reviewerId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public List<ReviewResponse> getByReviewee(Long revieweeId) {
        return reviewsRepository.findByReviewee_Id(revieweeId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public void delete(Long id) {
        if (!reviewsRepository.existsById(id)) {
            throw new RuntimeException("Application not found");
        }
        reviewsRepository.deleteById(id);
    }

    private ReviewResponse toResponse(Reviews reviews) {
        return ReviewResponse.builder()
                .id(reviews.getId())
                .jobApplicationId(reviews.getJobApplications().getId())
                .reviewerId(reviews.getReviewer().getId())
                .revieweeId(reviews.getReviewee().getId())
                .rating(reviews.getRating())
                .isPublished(reviews.getIsPublished())
                .createdAt(reviews.getCreatedAt())
                .build();
    }
}
