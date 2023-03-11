package com.oga.cqrsexample.commonapi.dtos;

import lombok.Data;

@Data
public class DebitAccountRequestDto {
    private String id;
    private double amount;
    private String currency;
}
