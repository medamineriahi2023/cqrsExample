package com.oga.cqrsexample.query.service;

import com.oga.cqrsexample.commonapi.enums.AccountStatus;
import com.oga.cqrsexample.commonapi.enums.OperationType;
import com.oga.cqrsexample.commonapi.events.AccountCreatedEvent;
import com.oga.cqrsexample.commonapi.events.AccountCreditedEvent;
import com.oga.cqrsexample.commonapi.events.AccountDebitedEvent;
import com.oga.cqrsexample.commonapi.queries.GetAllAccountQuery;
import com.oga.cqrsexample.query.entities.Account;
import com.oga.cqrsexample.query.entities.Operation;
import com.oga.cqrsexample.query.repository.AccountRepository;
import com.oga.cqrsexample.query.repository.OperationRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class AccountServiceHandler {
    private AccountRepository accountRepository;
    private OperationRepository operationRepository;
    @EventHandler
    public void on(AccountCreatedEvent accountCreatedEvent){
        log.info("creating an account in the database");
        accountRepository.save(new Account(accountCreatedEvent.getId(), accountCreatedEvent.getInitialBalance(),accountCreatedEvent.getCurrency(), accountCreatedEvent.getStatus(),null));
    }

    @EventHandler
    public void on(AccountDebitedEvent accountDebitedEvent){
        log.info("debit event has been done");
        Account account= accountRepository.findById(accountDebitedEvent.getId()).get();
        Operation operation = new Operation();
        operation.setAmount(BigDecimal.valueOf(accountDebitedEvent.getAmount()));
        operation.setOperationDate(new Date());
        operation.setAccount(account);
        operation.setType(OperationType.DEBIT);
        operationRepository.save(operation);
        account.setBalance(account.getBalance()- accountDebitedEvent.getAmount());
        accountRepository.save(account);
    }


    @EventHandler
    public void on(AccountCreditedEvent accountCreditedEvent){
        log.info("credit event has been done");
        Account account= accountRepository.findById(accountCreditedEvent.getId()).get();
        Operation operation = new Operation();
        operation.setAmount(BigDecimal.valueOf(accountCreditedEvent.getAmount()));
        operation.setOperationDate(new Date()); // a ne pas faire
        operation.setAccount(account);
        operation.setType(OperationType.CREDIT);
        operationRepository.save(operation);
        account.setBalance(account.getBalance()+ accountCreditedEvent.getAmount());
        accountRepository.save(account);
    }

    @QueryHandler
    public List<Account> on(GetAllAccountQuery getAllAccountQuery){
       return accountRepository.findAll();
    }

}
