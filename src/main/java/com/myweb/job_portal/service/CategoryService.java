package com.myweb.job_portal.service;

import com.myweb.job_portal.dto.PositionDto;
import com.myweb.job_portal.dto.response.CategoryResponse;
import com.myweb.job_portal.entity.Specializations;
import com.myweb.job_portal.repository.SpecializationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    @Autowired
    private SpecializationRepository specializationRepository;

    public List<CategoryResponse> getAllCategories() {
        List<Specializations> list = specializationRepository.findAll();

        return list.stream().map(spec -> {
            List<PositionDto> positionDtos = spec.getPositions().stream()
                    .map(pos -> new PositionDto(pos.getId(), pos.getName()))
                    .collect(Collectors.toList());

            return CategoryResponse.builder()
                    .id(spec.getId())
                    .name(spec.getName())
                    .positions(positionDtos)
                    .build();
        }).collect(Collectors.toList());
    }
}
