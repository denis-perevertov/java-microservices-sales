package com.dp.userservice.service;

import com.dp.userservice.persistence.entity.User;
import com.dp.userservice.persistence.entity.UserTransaction;
import com.dp.userservice.persistence.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<User> getUsers() {
        log.info("fetching all users");
        return userRepository.findAll();
    }

    @Override
    public Page<User> getUsersPage(Specification<User> spec, Pageable pageable) {
        Page<User> users = userRepository.findAll(spec, pageable);
        log.info("fetched page {}/{} of users", users.getNumber(), users.getTotalPages());
        return users;
    }

    @Override
    public User getUser(Long id) {
        log.info("fetching user (ID:{})", id);
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found!"));
    }

    @Override
    public User saveUser(User user) {
        User savedUser = userRepository.save(user);
        log.info("saved user (ID:{})", savedUser.getId());
        return savedUser;
    }

    @Override
    public void deleteUser(Long id) {
        log.info("deleting user (ID:{})", id);
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public List<UserTransaction> getUserTransactions(Long id) {
        log.info("getting user transactions (user ID:{})", id);
        return getUser(id).getFinanceDetails().getTransactions();
    }
}
