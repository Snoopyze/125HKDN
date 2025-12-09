package com.myweb.job_portal.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Positions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "specialization_id", nullable = false)
    private Specializations specialization;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "positions", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<JobPosts> jobPosts = new ArrayList<>();
}
