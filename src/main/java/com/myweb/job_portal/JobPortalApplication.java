package com.myweb.job_portal;

import com.myweb.job_portal.controller.UserController;
import com.myweb.job_portal.dto.request.CompanyRegisterRequest;
import com.myweb.job_portal.entity.Users;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication()
public class JobPortalApplication {

	public static void main(String[] args) {
		SpringApplication.run(JobPortalApplication.class, args);
	}

}
