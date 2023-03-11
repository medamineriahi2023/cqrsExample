package com.oga.cqrsexample.commonapi.commands;

import com.oga.cqrsexample.commonapi.enums.AccountStatus;
import lombok.Getter;

public class CreateAccountCommand extends BaseCommand<String>{

    @Getter private double initialBalance;
    @Getter private String currency;
    @Getter private AccountStatus status;

    public CreateAccountCommand(String id, double initialBalance, String currency, AccountStatus status) {
        super(id);
        this.initialBalance = initialBalance;
        this.currency = currency;
        this.status = status;
    }
}
