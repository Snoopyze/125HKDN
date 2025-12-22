package com.myweb.job_portal.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PositionDto {
    private Long id;
    private String name;
}
