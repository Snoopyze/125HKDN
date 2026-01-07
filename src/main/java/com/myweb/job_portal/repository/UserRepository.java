package com.myweb.job_portal.repository;

import com.myweb.job_portal.entity.Users;
import com.myweb.job_portal.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface    UserRepository extends JpaRepository<Users,Long> {

    Optional<Users> findByEmailOrPhone(String email, String phone);
    Optional<Users> findByEmail(String email);

    @Override
    Optional<Users> findById(Long aLong);

    Boolean existsUsersById(Long userId);
    Boolean existsByEmail(String email);
    Boolean existsByPhone(String phone);

    long countByUserRole(UserRole userRole);

    @Query("select u from Users u " +
            "left join fetch u.candidateProfile " +
            "left join fetch u.companies")
    List<Users> findAllWithDetails();
}
