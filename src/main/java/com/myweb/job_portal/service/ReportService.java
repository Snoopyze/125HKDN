package com.myweb.job_portal.service;

import com.myweb.job_portal.dto.request.ProcessReportRequest;
import com.myweb.job_portal.dto.response.ReportDetailResponse;
import com.myweb.job_portal.dto.response.ReportResponse;
import com.myweb.job_portal.entity.Companies;
import com.myweb.job_portal.entity.CompanyReports;
import com.myweb.job_portal.entity.Users;
import com.myweb.job_portal.enums.ReportStatus;
import com.myweb.job_portal.enums.UserStatus;
import com.myweb.job_portal.repository.CompanyReportRepository;
import com.myweb.job_portal.repository.CompanyVerificationRepository;
import com.myweb.job_portal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportService {

    @Autowired
    private CompanyReportRepository companyReportRepository;
    @Autowired
    private CompanyVerificationRepository companyVerificationRepository;
    @Autowired
    private UserRepository userRepository;

    public Map<String, Long> getReportStatistics() {
        long total = companyReportRepository.count(); // Tổng tất cả
        long pending = companyReportRepository.countByStatus(ReportStatus.pending);
        long resolves = companyReportRepository.countByStatus(ReportStatus.resolves);
        long reject = companyReportRepository.countByStatus(ReportStatus.reject);

        Map<String, Long> stats = new HashMap<>();
        stats.put("total", total);
        stats.put("pending", pending);
        stats.put("resolves", resolves);
        stats.put("reject", reject);

        return stats;
    }

    private String resolveNote(String inputNote, String defaultNote) {
        if (inputNote == null || inputNote.trim().isEmpty()) {
            return defaultNote;
        }
        return inputNote;
    }

    public List<ReportResponse> getAllReports() {
        List<CompanyReports> companyReports = companyReportRepository.findAllReportsWithDetails();

        return companyReports.stream().map(report -> {
            String fullName = "Ẩn danh";

            if (report.getUsers().getCandidateProfile() != null) {
                fullName = report.getUsers().getCandidateProfile().getFullName();
            }

            return ReportResponse.builder()
                    .reportId(report.getId())
                    .description(report.getDescription())
                    .createdAt(report.getCreatedAt())
                    .status(report.getStatus().name())
                    .reporterName(fullName)
                    .accusedCompany(report.getCompany().getShortName())
                    .build();
        }).collect(Collectors.toList());
    }

    public ReportDetailResponse getReportDetail(Long reportId) {
        CompanyReports report = companyReportRepository.findDetailById(reportId)
                .orElseThrow(() -> new RuntimeException("Báo cáo không tồn tại!"));

        String finalAdminNote = report.getAdminNote();
        if (report.getStatus() == ReportStatus.pending) {
            finalAdminNote = null;
        }

        String reporterName = "Ẩn danh";
        if (report.getUsers().getCandidateProfile() != null) {
            reporterName = report.getUsers().getCandidateProfile().getFullName();
        }

        return ReportDetailResponse.builder()
                .reportId(report.getId())
                .description(report.getDescription())
                .evidenceUrl(report.getEvidenceUrl())
                .status(report.getStatus().name())
                .adminNote(finalAdminNote)
                .reporterName(reporterName)
                .build();
    }

    @Transactional
    public void ignoreReport(Long reportId, ProcessReportRequest request) {
        CompanyReports report = companyReportRepository.findById(reportId)
                .orElseThrow(() -> new RuntimeException("Báo cáo không tồn tại"));

        report.setStatus(ReportStatus.reject);
        report.setAdminNote(resolveNote(request.getAdminNote(), "Báo cáo bị từ chối"));

        companyReportRepository.save(report);
    }

    @Transactional
    public void warnCompany(Long reportId, ProcessReportRequest request) {
        CompanyReports report = companyReportRepository.findById(reportId)
                .orElseThrow(() -> new RuntimeException("Báo cáo không tồn tại"));

        report.setStatus(ReportStatus.resolves);
        report.setAdminNote(resolveNote(request.getAdminNote(), "Bạn đã bị cảnh cáo"));

        companyReportRepository.save(report);
    }

    @Transactional
    public void lockCompanyAccount(Long reportId, ProcessReportRequest request) {
        CompanyReports report = companyReportRepository.findById(reportId)
                .orElseThrow(() -> new RuntimeException("Báo cáo không tồn tại"));

        Companies company = report.getCompany();
        if (company == null) throw new RuntimeException("Lỗi dữ liệu: Không tìm thấy công ty");

        Users ownerUser = company.getOwner();
        if (ownerUser == null) throw new RuntimeException("Lỗi dữ liệu: Công ty chưa có chủ sở hữu");

        ownerUser.setUserStatus(UserStatus.locked);
        ownerUser.setUpdatedAt(LocalDateTime.now());
        userRepository.save(ownerUser);

        report.setStatus(ReportStatus.resolves);
        report.setAdminNote(resolveNote(request.getAdminNote(), "Tài khoản đã bị khóa"));

        companyReportRepository.save(report);
    }
}
