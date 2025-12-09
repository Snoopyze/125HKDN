package com.myweb.job_portal.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Entity
@Table(name = "reviews")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reviews {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "job_application_id", nullable = false, unique = true)
    private JobApplications jobApplications;

    @ManyToOne
    @JoinColumn(name = "reviewer_id", nullable = false)
    private Users reviewer;

    @ManyToOne
    @JoinColumn(name = "reviewee_id", nullable = false)
    private Users reviewee;

    @Column(name = "rating")
    private Integer rating;

    @Column(name = "is_published")
    private Boolean isPublished = false;

    @Column(name = "created_at")
    private LocalDateTime createdAt =  LocalDateTime.now();

}
