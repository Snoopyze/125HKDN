package com.myweb.job_portal.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "industries")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Industries {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "industries", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Specializations> specializations = new ArrayList<>();


}
