package com.dp.userservice.service;

import com.dp.userservice.persistence.entity.Admin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface AdminService {

    List<Admin> getAdmins();
    Page<Admin> getAdminsPage(Specification<Admin> spec, Pageable pageable);
    Admin getAdmin(Long id);
    Admin saveAdmin(Admin admin);
    void deleteAdmin(Long id);

}
