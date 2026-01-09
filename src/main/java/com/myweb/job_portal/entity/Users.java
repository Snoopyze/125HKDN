package com.myweb.job_portal.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.myweb.job_portal.enums.UserRole;
import com.myweb.job_portal.enums.UserStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, length = 50)
    private String email;

    @Column(unique = true, length = 10)
    private String phone;

    @JsonIgnore
    @Column(name = "password_hash", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private UserRole userRole;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private UserStatus userStatus;

    @Column(name = "create_at")
    private LocalDateTime createdAt;

    @Column(name = "update_at")
    private LocalDateTime updatedAt;

    @Column(name = "gender", nullable = false)
    private Integer gender;

    @OneToOne(mappedBy = "users", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private CandidateProfiles candidateProfile;
//
//
//    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<CandidateCertificate> candidateCertificates = new ArrayList<>();
//
//
    @OneToOne(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Companies companies;
//
//
//    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<CompanyReports> companyReports = new ArrayList<>();
//
//
//    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<JobApplications> jobApplications = new ArrayList<>();
//
//
//    @OneToMany(mappedBy = "recipient", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<Notifications> notificationsReceived = new ArrayList<>();
//
//
//    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<Notifications> notificationsSent = new ArrayList<>();
//
//
//    @OneToMany(mappedBy = "reviewer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<Reviews> reviewsGiven = new ArrayList<>();
//
//
//    @OneToMany(mappedBy = "reviewee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<Reviews> reviewsReceived = new ArrayList<>();
//
//
//    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<Message> messages = new ArrayList<>();
}
