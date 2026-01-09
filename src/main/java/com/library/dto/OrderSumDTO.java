package com.library.dto;

import lombok.Data;

@Data
public class OrderSumDTO
{
    private Double sum;

    public OrderSumDTO(Double sum) {
        this.sum = sum;
    }

    public OrderSumDTO() {
    }
}
