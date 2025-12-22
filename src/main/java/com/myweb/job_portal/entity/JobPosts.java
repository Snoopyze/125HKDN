package com.myweb.job_portal.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "job_posts")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
public class JobPosts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    @JsonIgnore
    private CompanyProjects companyProjects;

    @ManyToOne
    @JoinColumn(name = "position_id", nullable = false)
    @JsonIgnore
    private Positions positions;

    @Column(name = "tittle")
    private String tittle;

    @Column(name = "application_deadline")
    private LocalDate applicationDeadline;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "job_description", columnDefinition = "TEXT")
    private String jobDescription;


    @OneToMany(mappedBy = "jobPosts", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<JobApplications> jobApplications = new ArrayList<>();


}
