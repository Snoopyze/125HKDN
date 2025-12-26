package com.myweb.job_portal.service;

import com.myweb.job_portal.dto.request.UpdateProfileRequest;
import com.myweb.job_portal.dto.response.CandidateDetailResponse;
import com.myweb.job_portal.entity.CandidateProfiles;
import com.myweb.job_portal.entity.Users;
import com.myweb.job_portal.repository.CandidateProfileRepository;
import com.myweb.job_portal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CandidateService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CandidateProfileRepository candidateProfileRepository;

    public CandidateDetailResponse getCandidateDetail(Long userId)
    {
        Users user =  userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy user với ID: " + userId));

        CandidateProfiles profiles = candidateProfileRepository.findByUsers_Id(userId).orElse(null);

        CandidateDetailResponse.CandidateDetailResponseBuilder response = CandidateDetailResponse.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .phone(user.getPhone())
                .password(user.getPassword())
                .role(user.getUserRole().name())
                .status(user.getUserStatus().name())
                .createAt(user.getCreatedAt())
                .updateAt(user.getUpdatedAt())
                .id(profiles.getId())
                .fullName(profiles.getFullName())
                .avatarUrl(profiles.getAvatarUrl())
                .streetAddress(profiles.getStreetAddress())
                .wardAddress(profiles.getWardAddress())
                .provinceAddress(profiles.getProvinnceAddress());
        return response.build();
    }

    @Transactional
    public CandidateDetailResponse updateCandidateDetail(Long userId, UpdateProfileRequest request)
    {
        Users users = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy user"));

        CandidateProfiles profiles = candidateProfileRepository.findByUsers_Id(userId)
                .orElseThrow(() -> new RuntimeException("Hồ sơ ứng viên chưa được tạo"));

        if(!users.getPhone().equals(request.getPhone()) && userRepository.existsByPhone(request.getPhone())) {
            throw new RuntimeException("Số điện thoại này đã được sử dụng!");
        }

        if (!users.getEmail().equals(request.getEmail()) && userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email này đã được sử dụng!");
        }

        users.setEmail(request.getEmail());
        users.setPhone(request.getPhone());

        profiles.setFullName(request.getFullName());
        profiles.setStreetAddress(request.getStreetAddress());
        profiles.setWardAddress(request.getWardAddress());
        profiles.setProvinnceAddress(request.getProvinceAddress());

        candidateProfileRepository.save(profiles);

        return getCandidateDetail(userId);

    }
}
