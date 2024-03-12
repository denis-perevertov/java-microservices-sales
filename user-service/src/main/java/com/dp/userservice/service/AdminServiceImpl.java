package com.dp.userservice.service;

import com.dp.userservice.persistence.entity.Admin;
import com.dp.userservice.persistence.repository.AdminRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;

    @Override
    public List<Admin> getAdmins() {
        log.info("fetching all admins");
        return adminRepository.findAll();
    }

    @Override
    public Page<Admin> getAdminsPage(Specification<Admin> spec, Pageable pageable) {
        Page<Admin> admins = adminRepository.findAll(spec, pageable);
        log.info("fetched page {}/{} of admins", admins.getNumber(), admins.getTotalPages());
        return admins;
    }

    @Override
    public Admin getAdmin(Long id) {
        log.info("fetching admin (ID:{})", id);
        return adminRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Admin not found!"));
    }

    @Override
    public Admin saveAdmin(Admin admin) {
        Admin savedAdmin = adminRepository.save(admin);
        log.info("saved admin (ID:{})", savedAdmin.getId());
        return savedAdmin;
    }

    @Override
    public void deleteAdmin(Long id) {
        log.info("deleting admin (ID:{})", id);
        adminRepository.deleteById(id);
    }
}
