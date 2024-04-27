package com.dp.userservice.service;

import com.dp.userservice.model.user.UserBalanceUpdateRequest;
import com.dp.userservice.persistence.entity.User;
import com.dp.userservice.persistence.entity.UserTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.List;

public interface UserService {

    List<User> getUsers();
    Page<User> getUsersPage(Specification<User> spec, Pageable pageable);
    User getUser(Long id);
    User saveUser(User user);
    void deleteUser(Long id);

    List<UserTransaction> getUserTransactions(Long id);

    void updateUserBalance(UserBalanceUpdateRequest request);

}
