package com.myweb.job_portal.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.myweb.job_portal.enums.ApplicationStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "job_applications")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobApplications {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "job_id", nullable = false)
    @JsonIgnore
    private JobPosts jobPosts;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private Users users;

    @Column(name = "cv_url", length = 2048)
    private String cvUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ApplicationStatus status =  ApplicationStatus.pending;

    @Column(name = "applied_at")
    private LocalDateTime appliedAt;

    @OneToMany(mappedBy = "jobApplications", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CandidateCertificate> candidateCertificates = new ArrayList<>();

    @OneToMany(mappedBy = "jobApplications", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Conversations>  conversations = new ArrayList<>();

    @OneToOne(mappedBy = "jobApplications", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Reviews reviews;


}
