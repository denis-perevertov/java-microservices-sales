package com.dp.userservice.persistence.repository;

import com.dp.userservice.persistence.entity.UserTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTransactionRepository extends JpaRepository<UserTransaction, Long> {
}
