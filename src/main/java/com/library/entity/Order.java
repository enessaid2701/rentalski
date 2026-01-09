package com.library.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "orders")
public class Order extends BaseEntity
{
    private Double totalPrice;

    private String gender;

    @Enumerated(EnumType.STRING)
    private PriceType priceType;

    @ManyToOne
    @JoinColumn(name="customer_id", nullable=false)
    private CustomerEntity customer;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
}
