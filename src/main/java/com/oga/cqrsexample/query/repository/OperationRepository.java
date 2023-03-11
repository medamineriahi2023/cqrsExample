package com.oga.cqrsexample.query.repository;

import com.oga.cqrsexample.query.entities.Account;
import com.oga.cqrsexample.query.entities.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationRepository extends JpaRepository<Operation, String> {
}
