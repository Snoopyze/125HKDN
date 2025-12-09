package com.myweb.job_portal.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "companies")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
public class Companies {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "owner_user_id", nullable = false)
    private Users owner;

    @Column(name = "owner_name")
    private String ownerName;

    @Column(name = "legal_name")
    private String legalName;

    @Column(name = "short_name")
    private String shortName;

    @Column(name = "street_address")
    private String streetAddress;

    @Column(name = "ward_address")
    private String wardAddress;

    @Column(name = "provinnce_address")
    private String provinnceAddress;

    @Column(name = "website_url", length = 2048)
    private String websiteUrl;

    @Column(name = "industry")
    private String industry;

    @Column(name = "company_size")
    private String companySize;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "logo_url", length = 2048)
    private String logoUrl;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CompanyProjects> projects = new ArrayList<>();


    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CompanyVerifications> verification = new ArrayList<>();


    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CompanyReports> reports = new ArrayList<>();

}
