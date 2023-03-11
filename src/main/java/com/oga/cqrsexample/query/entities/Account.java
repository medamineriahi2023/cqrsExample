package com.oga.cqrsexample.query.entities;
import com.oga.cqrsexample.commonapi.enums.AccountStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Collection;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    @Id
    private String id;
    private double balance;

    private String currency;
    @Enumerated(EnumType.STRING)
    private AccountStatus status;

    @OneToMany(mappedBy = "account")
    private Collection<Operation> operation;
}
