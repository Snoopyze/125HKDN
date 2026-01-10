package com.myweb.job_portal.utils;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CurrentUserUtil {
    private final HttpServletRequest request;

    public Long getCurrentUserId() {
        String userId = request.getHeader("currentUserId");
        return userId != null ? Long.parseLong(userId) : null;
    }

}
