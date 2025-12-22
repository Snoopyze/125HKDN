package com.myweb.job_portal.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "company_projects")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
public class CompanyProjects {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    @JsonIgnore
    private Companies company;

    @Column(name = "tittle")
    private String tittle;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "companyProjects", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<JobPosts> jobPosts = new ArrayList<>();
}
