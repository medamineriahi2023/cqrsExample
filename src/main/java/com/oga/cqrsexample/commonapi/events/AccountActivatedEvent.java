package com.oga.cqrsexample.commonapi.events;

import com.oga.cqrsexample.commonapi.enums.AccountStatus;
import lombok.Getter;

public class AccountActivatedEvent extends BaseEvent<String>{
    
    @Getter private AccountStatus status;
    public AccountActivatedEvent(String id, AccountStatus status) {
        super(id);
        this.status = status;
    }
}
