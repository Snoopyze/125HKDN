package com.myweb.job_portal.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "candidate_profiles")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
public class CandidateProfiles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users users;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "street_address")
    private String streetAddress;

    @Column(name = "ward_address")
    private String wardAddress;

    @Column(name = "provinnce_address")
    private String provinnceAddress;

    @Column(name = "avatar_url", length = 2048)
    private String avatarUrl;
}
