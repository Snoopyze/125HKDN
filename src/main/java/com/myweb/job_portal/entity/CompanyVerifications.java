package com.myweb.job_portal.entity;

import com.myweb.job_portal.enums.ApplicationStatus;
import com.myweb.job_portal.enums.VerificationsStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "company_verifications")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
public class CompanyVerifications {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Companies company;

    @Column(name = "tax_code")
    private String taxCode;

    @Column(name = "business_license_url", length = 2048)
    private String businessLicenseUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private VerificationsStatus verificationsStatus;

    @Column(name = "reviewed_at")
    private LocalDateTime reviewedAt;

    @Column(name = "reviewer_note", columnDefinition = "TEXT")
    private String reviewerNote;


}
