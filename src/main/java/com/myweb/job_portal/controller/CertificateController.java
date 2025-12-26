package com.myweb.job_portal.controller;

import com.myweb.job_portal.dto.response.CertificateResponse;
import com.myweb.job_portal.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/certificates")
public class CertificateController {

    @Autowired
    CertificateService certificateService;

    @GetMapping("/list")
    public ResponseEntity<List<CertificateResponse>> getAllCertificates(@RequestParam Long userId){
        try {
            List<CertificateResponse> list = certificateService.getMyCertificates(userId);
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

    }
}
