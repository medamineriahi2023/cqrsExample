package com.oga.cqrsexample.query.repository;

import com.oga.cqrsexample.query.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, String> {
}
