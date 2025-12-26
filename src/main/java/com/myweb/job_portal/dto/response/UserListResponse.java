package com.myweb.job_portal.dto.response;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserListResponse {
    private Long id;
    private String email;
    private String role;
    private String status;
    String displayName;

}
