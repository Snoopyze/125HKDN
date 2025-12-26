package com.myweb.job_portal.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.myweb.job_portal.dto.request.ChangePasswordRequest;
import com.myweb.job_portal.dto.request.LoginRequest;
import com.myweb.job_portal.dto.response.UserListResponse;
import com.myweb.job_portal.entity.CandidateProfiles;
import com.myweb.job_portal.entity.Users;
import com.myweb.job_portal.enums.UserRole;
import com.myweb.job_portal.enums.UserStatus;
import com.myweb.job_portal.repository.CandidateProfileRepository;
import com.myweb.job_portal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CandidateProfileRepository candidateProfileRepository;

    @Autowired
    private Cloudinary cloudinary;

    public Users login(LoginRequest request) {
        Optional<Users> userOptional = userRepository.findByEmailOrPhone(request.getAccount(), request.getAccount());

        if (userOptional.isEmpty()) {
            return null;
        }

        Users user = userOptional.get();

        if (user.getPassword().equals(request.getPassword())) {
            return user;
        }

        return null;
    }

    @Transactional
    public void changePassword(ChangePasswordRequest request) {
        Users users = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("Người dùng không tồn tại!"));

        if(!users.getEmail().equals(request.getEmail())) {
            throw new RuntimeException("Email nhập vào không khớp với tài khoản này!");
        }

        if(!users.getPassword().equals(request.getCurrentPassword())) {
            throw new RuntimeException("Mật khẩu hiện tại không đúng!");
        }

        if(!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new RuntimeException("Mật khẩu xác nhận không khớp!");
        }

        if (request.getNewPassword().equals(request.getCurrentPassword())) {
            throw new RuntimeException("Mật khẩu mới không được trùng với mật khẩu cũ!");
        }

        users.setPassword(request.getNewPassword());
        userRepository.save(users);
    }

    @Transactional
    public String updateAvatar(Long userId, MultipartFile imageFile) throws IOException {


        if(!userRepository.existsUsersById(userId)) {
            throw new RuntimeException("Người dùng không tồn tại!");
        }

        String originalFilename = imageFile.getOriginalFilename();
        String imageUrl;

        try {
            Map uploadResult = cloudinary.uploader().upload(
                    imageFile.getBytes(),
                    ObjectUtils.asMap(  "resource_type", "raw",
                            "folder", "job_applications/images",
                            "public_id", originalFilename)
            );
            imageUrl = uploadResult.get("secure_url").toString();
        }catch (IOException e){
            throw new RuntimeException("Upload fail");
        }



        CandidateProfiles candidateProfiles = candidateProfileRepository.findByUsers_Id(userId)
                .orElseThrow(() -> new RuntimeException("Hồ sơ ứng viên chưa được tạo!"));

        candidateProfiles.setAvatarUrl(imageUrl);
        candidateProfileRepository.save(candidateProfiles);

        return imageUrl;
    }


    public Map<String, Long> getUserStatistics() {
        long total = userRepository.count(); // Tổng tất cả
        long candidate = userRepository.countByUserRole(UserRole.candidate);
        long employer = userRepository.countByUserRole(UserRole.employer);
        long admin = userRepository.countByUserRole(UserRole.admin);

        Map<String, Long> stats = new HashMap<>();
        stats.put("total", total);
        stats.put("candidate", candidate);
        stats.put("employer", employer);
        stats.put("admin", admin);

        return stats;
    }

    public List<UserListResponse> getAllUsers() {
        List<Users> users = userRepository.findAllWithDetails();

        return users.stream().map(user -> {
            String displayName = "N/A";
            if (user.getUserRole() == UserRole.candidate) {
                if (user.getCandidateProfile() != null) {
                    displayName = user.getCandidateProfile().getFullName();
                }
            } else if(user.getUserRole() == UserRole.employer) {
                if (user.getCompanies() != null) {
                    displayName = user.getCompanies().getOwnerName();
                }
            }else if (user.getUserRole() == UserRole.admin) {
                displayName = "Administrator";
            }

            return UserListResponse.builder()
                    .id(user.getId())
                    .email(user.getEmail())
                    .role(user.getUserRole().name())
                    .status(user.getUserStatus().name())
                    .displayName(displayName)
                    .build();
        }).collect(Collectors.toList());
    }

    @Transactional
    public String toggleUserStatus(Long userId) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Người dùng không tồn tại!"));

        UserStatus currentStatus = user.getUserStatus();
        UserStatus newStatus ;

        if(currentStatus == UserStatus.active)  {
            newStatus = UserStatus.locked;
        }else if(currentStatus == UserStatus.locked) {
            newStatus = UserStatus.active;
        }else {
            throw new RuntimeException("Không thể đổi trạng thái cho tài khoản đang là " + currentStatus);
        }

        user.setUserStatus(newStatus);
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
        return "Đã đổi trạng thái từ " + currentStatus + " sang " + newStatus;
    }

}
