package com.myweb.job_portal.dto.response;

import com.myweb.job_portal.dto.PositionDto;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CategoryResponse {

    private Long id;
    private String name;
    private List<PositionDto> positions;
}
