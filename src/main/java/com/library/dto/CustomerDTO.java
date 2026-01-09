package com.library.dto;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class CustomerDTO
{
    private Long customerId;
    private String fullName;
    private String phoneNumber;
    private double age;
    private double shoeSize;
    private double height;
    private double weight;
}
