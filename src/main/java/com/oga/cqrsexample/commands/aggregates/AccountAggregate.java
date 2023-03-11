package com.oga.cqrsexample.commands.aggregates;
import com.oga.cqrsexample.commonapi.commands.CreateAccountCommand;
import com.oga.cqrsexample.commonapi.commands.CreditAccountCommand;
import com.oga.cqrsexample.commonapi.commands.DebitAccountCommand;
import com.oga.cqrsexample.commonapi.enums.AccountStatus;

import com.oga.cqrsexample.commonapi.events.AccountActivatedEvent;
import com.oga.cqrsexample.commonapi.events.AccountCreatedEvent;
import com.oga.cqrsexample.commonapi.events.AccountCreditedEvent;
import com.oga.cqrsexample.commonapi.events.AccountDebitedEvent;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.UUID;

@Aggregate
@Slf4j
public class AccountAggregate {
    @AggregateIdentifier
    private String accountId;
    private double balance;

    private String currency;

    private AccountStatus status;

    public AccountAggregate(){
    }
    @CommandHandler
    public AccountAggregate(CreateAccountCommand createAccountCommand) throws Exception {
        if (createAccountCommand.getInitialBalance() < 0){
            throw new Exception("impossible de crée un compte ");
        }
        AggregateLifecycle.apply(new AccountCreatedEvent(
                createAccountCommand.getId(),
                createAccountCommand.getInitialBalance(),
                createAccountCommand.getCurrency(),
                createAccountCommand.getStatus()
        ));
    }
    @EventSourcingHandler
    public void on(AccountCreatedEvent event){
        this.accountId = event.getId();
        this.currency = event.getCurrency();
        this.status = AccountStatus.CREATED;
        this.balance = event.getInitialBalance();
        AggregateLifecycle.apply(new AccountActivatedEvent(
                UUID.randomUUID().toString(),
                AccountStatus.ACTIVATED
        ));
    }
    @EventSourcingHandler
    public void on (AccountActivatedEvent accountActivatedEvent){
        this.status= accountActivatedEvent.getStatus();
    }

    @CommandHandler
    public void handle(CreditAccountCommand creditAccountCommand){
        log.info("crefit command {} {} {}",creditAccountCommand.getId(),creditAccountCommand.getAmount() ,creditAccountCommand.getCurrency());
        AggregateLifecycle.apply(new AccountCreditedEvent(
                creditAccountCommand.getId(),
                creditAccountCommand.getAmount(),
                creditAccountCommand.getCurrency()
        ));
    }

    @CommandHandler
    public void handle(DebitAccountCommand debitAccountCommand){
        if (debitAccountCommand.getAmount() > this.balance){
            throw new RuntimeException("the account has no balance to do this");
        }
        log.info("crefit command {} {} {}",debitAccountCommand.getId(),debitAccountCommand.getAmount() ,debitAccountCommand.getCurrency());
        AggregateLifecycle.apply(new AccountDebitedEvent(
                debitAccountCommand.getId(),
                debitAccountCommand.getAmount(),
                debitAccountCommand.getCurrency()
        ));
    }

    @EventSourcingHandler
    public void on (AccountCreditedEvent accountCreditedEvent){
        log.info("account event command {} {} {}",accountCreditedEvent.getId(),accountCreditedEvent.getAmount() ,accountCreditedEvent.getCurrency());
        this.balance+= accountCreditedEvent.getAmount();
    }

    @EventSourcingHandler
    public void on (AccountDebitedEvent accountDebitedEvent){
        this.balance-= accountDebitedEvent.getAmount();
    }

}
