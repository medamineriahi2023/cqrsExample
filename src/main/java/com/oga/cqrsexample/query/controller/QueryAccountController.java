package com.oga.cqrsexample.query.controller;

import com.oga.cqrsexample.commonapi.queries.GetAllAccountQuery;
import com.oga.cqrsexample.query.entities.Account;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/query/accounts")
@AllArgsConstructor
@Slf4j
public class QueryAccountController {
private QueryGateway queryGateway;


@GetMapping
    public List<Account> accountList(){
    return queryGateway.query(new GetAllAccountQuery(), ResponseTypes.multipleInstancesOf(Account.class)).join();
}

}
