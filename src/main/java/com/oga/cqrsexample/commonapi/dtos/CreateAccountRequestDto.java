package com.oga.cqrsexample.commonapi.dtos;

import com.oga.cqrsexample.commonapi.enums.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class CreateAccountRequestDto {

    private double initialBalance;
    private String currency;}
