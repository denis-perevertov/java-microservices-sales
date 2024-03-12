package com.dp.userservice.validator;

import com.dp.userservice.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserValidator {

    private final UserRepository userRepository;

    public boolean userExists(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean userExists(Long id) { return userRepository.existsById(id); }

}
