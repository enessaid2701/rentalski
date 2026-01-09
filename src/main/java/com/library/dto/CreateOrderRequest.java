package com.library.dto;

import com.library.entity.CustomerEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderRequest
{
    private String gender;
    private String orderType;
    private Boolean sky;
    private Boolean board;
    private Boolean coats;
    private Boolean glasses;
    private Boolean helmet;
    private Boolean sled;
    private Boolean iceskate;
    private Boolean cabinet;
    private Boolean nightsky;
    private Boolean glove;
    private Boolean socks;
    private Double iceskateHour;
    private Double sledTime;
    private CustomerEntity customer;
    private Double discountRate;
    private boolean extraDiscount;
    private Integer printerCode;
}
