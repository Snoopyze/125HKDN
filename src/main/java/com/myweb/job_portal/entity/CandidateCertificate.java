package com.myweb.job_portal.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private Users users;

    @ManyToOne
    @JoinColumn(name = "job_application", nullable = false)
    @JsonIgnore
    private JobApplications jobApplications;

    @Column(name = "certificate_url", length = 2048)
    private String certificateUrl;

    @Column(name = "create_at")
    private LocalDateTime createdAt;
}
