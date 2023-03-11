package com.oga.cqrsexample.commands.controllers;

import com.oga.cqrsexample.commonapi.commands.CreateAccountCommand;
import com.oga.cqrsexample.commonapi.commands.CreditAccountCommand;
import com.oga.cqrsexample.commonapi.commands.DebitAccountCommand;
import com.oga.cqrsexample.commonapi.dtos.CreateAccountRequestDto;
import com.oga.cqrsexample.commonapi.dtos.CreditAccountRequestDto;
import com.oga.cqrsexample.commonapi.dtos.DebitAccountRequestDto;
import com.oga.cqrsexample.commonapi.enums.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@RestController
@RequestMapping("command/account")
@AllArgsConstructor
@Slf4j
public class AccountCommandController {
    private CommandGateway commandGateway;
    private EventStore eventStore;
    @PostMapping
    public CompletableFuture<String> createAccount(@RequestBody CreateAccountRequestDto createAccountRequestDto){
        log.info("request create account dto {}",createAccountRequestDto);
        CompletableFuture<String> response = commandGateway.send(new CreateAccountCommand(
                UUID.randomUUID().toString(),
                createAccountRequestDto.getInitialBalance(),
                createAccountRequestDto.getCurrency(),
                AccountStatus.CREATED));
        return response;
    }
    @GetMapping("/{accountId}")
    public Stream eventStore(@PathVariable String accountId){
        return eventStore.readEvents(accountId).asStream();
    }

    @PutMapping("/credit")
    public CompletableFuture<String> updateBalance(@RequestBody CreditAccountRequestDto creditAccountRequestDto){
        log.info("request create account dto {}", creditAccountRequestDto);
        CompletableFuture<String> response = commandGateway.send(new CreditAccountCommand(
                creditAccountRequestDto.getId(),
                creditAccountRequestDto.getAmount(),
                creditAccountRequestDto.getCurrency()
        ));
        return response;
    }

    @PutMapping("/debit")
    public CompletableFuture<String> debit(@RequestBody DebitAccountRequestDto debitAccountRequestDto){
        log.info("request debit from account dto {}", debitAccountRequestDto);
        CompletableFuture<String> response = commandGateway.send(new DebitAccountCommand(
                debitAccountRequestDto.getId(),
                debitAccountRequestDto.getAmount(),
                debitAccountRequestDto.getCurrency()
        ));
        return response;
    }
}
