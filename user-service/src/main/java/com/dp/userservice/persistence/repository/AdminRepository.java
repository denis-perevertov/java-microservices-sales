package com.dp.userservice.persistence.repository;

import com.dp.userservice.persistence.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AdminRepository extends JpaRepository<Admin, Long>, JpaSpecificationExecutor<Admin> {
    boolean existsByEmail(String email);
}
