package com.dp.userservice.validator;

import com.dp.userservice.persistence.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminValidator {
    private final AdminRepository adminRepository;

    public boolean adminExists(Long id) { return adminRepository.existsById(id); }
    public boolean adminExists(String email) {
        return adminRepository.existsByEmail(email);
    }
}
