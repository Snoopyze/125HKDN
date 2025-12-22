package com.myweb.job_portal.controller;

import com.myweb.job_portal.dto.request.ProcessReportRequest;
import com.myweb.job_portal.dto.request.RejectVerificationRequest;
import com.myweb.job_portal.dto.response.*;
import com.myweb.job_portal.service.JobApplicationService;
import com.myweb.job_portal.service.ReportService;
import com.myweb.job_portal.service.UserService;
import com.myweb.job_portal.service.VerificationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    JobApplicationService jobApplicationService;

    @Autowired
    private ReportService reportService;

    @Autowired
    private VerificationService verificationService;


    @GetMapping("/user/stats")
    public ResponseEntity<Map<String, Long>> getUserStatistics() {
        return ResponseEntity.ok(userService.getUserStatistics());
    }

    @GetMapping("/report/stats")
    public ResponseEntity<Map<String, Long>> getReportStats() {
        return ResponseEntity.ok(reportService.getReportStatistics());
    }

    @GetMapping("/jobApplications/all")
    public ResponseEntity<List<JobApplicationDashBoardResponse>> getAllJobApplications() {
        return ResponseEntity.ok(jobApplicationService.getAllApplications());
    }

    @GetMapping("/all-user")
    public ResponseEntity<List<UserListResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<String> toggleStatus(@PathVariable Long id) {
        try {
            String message = userService.toggleUserStatus(id);
            return ResponseEntity.ok(message);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/verification/list/status/pending")
    public ResponseEntity<List<VerificationResponse>> getPendingList() {
        return ResponseEntity.ok(verificationService.getPendingVerifications());
    }

    @PutMapping("/verification/{id}/approve")
    public ResponseEntity<String> approveVerification(@PathVariable Long id) {
        try {
            verificationService.approveVerification(id);
            return ResponseEntity.ok("Đã duyệt yêu cầu thành công!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/verification/{id}/reject")
    public ResponseEntity<String> rejectVerification(
            @PathVariable Long id,
            @RequestBody @Valid RejectVerificationRequest request
    ) {
        try {
            verificationService.rejectVerification(id, request.getReason());
            return ResponseEntity.ok("Đã từ chối yêu cầu thành công!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/report/list-reports")
    public ResponseEntity<List<ReportResponse>> getAllReports() {
        return ResponseEntity.ok(reportService.getAllReports());
    }

    @GetMapping("/report/{id}")
    public ResponseEntity<ReportDetailResponse> getReportDetail(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(reportService.getReportDetail(id));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/report/{id}/ignore")
    public ResponseEntity<String> ignoreReport(@PathVariable Long id, @RequestBody ProcessReportRequest request) {
        reportService.ignoreReport(id, request);
        return ResponseEntity.ok("Đã bỏ qua báo cáo!");
    }

    @PutMapping("/report/{id}/warn")
    public ResponseEntity<String> warnCompany(@PathVariable Long id, @RequestBody ProcessReportRequest request) {
        reportService.warnCompany(id, request);
        return ResponseEntity.ok("Đã gửi cảnh báo thành công!");
    }

    @PutMapping("/report/{id}/lock")
    public ResponseEntity<String> lockAccount(@PathVariable Long id, @RequestBody ProcessReportRequest request) {
        try {
            reportService.lockCompanyAccount(id, request);
            return ResponseEntity.ok("Đã khóa tài khoản doanh nghiệp và xử lý báo cáo!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
