package com.myweb.job_portal.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.myweb.job_portal.dto.request.CandidateRegisterRequest;
import com.myweb.job_portal.dto.request.ChangePasswordRequest;
import com.myweb.job_portal.dto.request.CompanyRegisterRequest;
import com.myweb.job_portal.dto.request.LoginRequest;
import com.myweb.job_portal.dto.response.UserListResponse;
import com.myweb.job_portal.entity.CandidateProfiles;
import com.myweb.job_portal.entity.Companies;
import com.myweb.job_portal.entity.CompanyVerifications;
import com.myweb.job_portal.entity.Users;
import com.myweb.job_portal.enums.UserRole;
import com.myweb.job_portal.enums.UserStatus;
import com.myweb.job_portal.enums.VerificationsStatus;
import com.myweb.job_portal.repository.CandidateProfileRepository;
import com.myweb.job_portal.repository.CompaniesRepository;
import com.myweb.job_portal.repository.CompanyVerificationRepository;
import com.myweb.job_portal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CandidateProfileRepository candidateProfileRepository;

    @Autowired
    private Cloudinary cloudinary;

    private final PasswordEncoder passwordEncoder;
    private final CompaniesRepository companiesRepository;
    private final CompanyVerificationRepository companyVerificationRepository;

    public Users login(LoginRequest request, UserRole roles, UserStatus userStatus) {
        Optional<Users> userOptional = userRepository.findByEmailOrPhone(request.getAccount(), request.getAccount());
        if (userOptional.isEmpty()) {
            throw new RuntimeException("Tài khoản không tồn tại!");
        }

        Users user = userOptional.get();

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Mật khẩu không chính xác!");
        }

        if (!user.getUserRole().equals(roles)) {
            throw new RuntimeException("Tài khoản này không có quyền truy cập vào giao diện " + roles.name());
        }

        if(!user.getUserStatus().equals(userStatus)){
            throw new RuntimeException("Tài khoản của bạn đã bị khóa hoặc chưa kích hoạt!");
        }

        return user;
    }

    @Transactional
    public void changePassword(ChangePasswordRequest request) {
        Users users = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("Người dùng không tồn tại!"));

        if(!users.getEmail().equals(request.getEmail())) {
            throw new RuntimeException("Email nhập vào không khớp với tài khoản này!");
        }

        if(!passwordEncoder.matches(request.getCurrentPassword(), users.getPassword())) {
            throw new RuntimeException("Mật khẩu hiện tại không đúng!");
        }

        if (request.getNewPassword().equals(request.getCurrentPassword())) {
            throw new RuntimeException("Mật khẩu mới không được trùng với mật khẩu cũ!");
        }

        users.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(users);
    }

    @Transactional
    public String updateAvatar(Long userId, MultipartFile imageFile) throws IOException {


        if(!userRepository.existsUsersById(userId)) {
            throw new RuntimeException("Người dùng không tồn tại!");
        }

        String publicId = UUID.randomUUID().toString();
        String imageUrl;

        try {
            Map uploadResult = cloudinary.uploader().upload(
                    imageFile.getBytes(),
                    ObjectUtils.asMap(  "resource_type", "image",
                            "folder", "candidate/avatar",
                            "public_id", publicId)
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

    public Users registerCandidate(CandidateRegisterRequest request) {
        Users user = new Users();
        String inputAccount = request.getAccount();

        if(inputAccount.contains("@")) {
            if (userRepository.existsByEmail(inputAccount)) {
                throw new RuntimeException("Email này đã được sử dụng!");
            }
            user.setEmail(inputAccount);
        } else {
            if (userRepository.existsByPhone(inputAccount)) {
                throw new RuntimeException("Số điện thoại này đã được sử dụng!");
            }
            user.setPhone(inputAccount);
        }

        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setUserRole(UserRole.candidate);
        user.setUserStatus(UserStatus.active);
        user.setCreatedAt(LocalDateTime.now());
        user.setGender(request.getGender());

        Users savedUser = userRepository.save(user);

        CandidateProfiles candidateProfiles = new CandidateProfiles();
        candidateProfiles.setUsers(savedUser);
        candidateProfiles.setFullName(request.getFullName());

        candidateProfileRepository.save(candidateProfiles);

        return savedUser;
    }

    @Transactional(rollbackFor = Exception.class)
    public Users registerCompany(CompanyRegisterRequest request, MultipartFile image) {
        if(!request.getEmail().contains("@")) {
            throw new RuntimeException("Nhà tuyển dụng phải đăng ký bằng Email hợp lệ!");
        }
        if(userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email này đã được sử dụng!");
        }

        Users user = new Users();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setUserRole(UserRole.employer);
        user.setUserStatus(UserStatus.active);
        user.setCreatedAt(LocalDateTime.now());
        user.setGender(request.getGender());

        Users savedUser = userRepository.save(user);

        Companies companies = new Companies();
        companies.setOwner(savedUser);
        companies.setOwnerName(request.getOwnerName());
        companies.setLegalName(request.getLegalName());
        companies.setProvinnceAddress(request.getProvince());
        companies.setWardAddress(request.getWard());
        companies.setStreetAddress(request.getStreet());
        companies.setIndustry(request.getIndustry());
        companies.setCompanySize(request.getCompanySize());

        Companies companiesSave = companiesRepository.save(companies);

        CompanyVerifications companyVerifications = new CompanyVerifications();
        companyVerifications.setCompany(companiesSave);
        companyVerifications.setTaxCode(request.getTaxCode());
        companyVerifications.setVerificationsStatus(VerificationsStatus.pending);

        MultipartFile imageFile = image;
        if (imageFile == null || imageFile.isEmpty()) {
            throw new RuntimeException("File ảnh không được để trống");
        }
        String contentType = imageFile.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new RuntimeException("Chỉ cho phép upload file ảnh");
        }
        String publicId = UUID.randomUUID().toString();
        String imageUrl;
        try {
            Map uploadResult = cloudinary.uploader().upload(
                    imageFile.getBytes(),
                    ObjectUtils.asMap(  "resource_type", "image",
                            "folder", "company_verifications/license",
                            "public_id", publicId)
            );
            imageUrl = uploadResult.get("secure_url").toString();
        }catch (IOException e){
            throw new RuntimeException("Upload fail");
        }

        companyVerifications.setBusinessLicenseUrl(imageUrl);
        companyVerificationRepository.save(companyVerifications);
        return savedUser;
    }

}
