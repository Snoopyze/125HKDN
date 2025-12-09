package com.myweb.job_portal.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "candidate_certificate")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CandidateCertificate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users users;

    @ManyToOne
    @JoinColumn(name = "job_application", nullable = false)
    private JobApplications jobApplications;

    @Column(name = "certificate_url", length = 2048)
    private String certificateUrl;

    @Column(name = "create_at")
    private LocalDateTime createdAt;
}
