package com.library.entity;

import lombok.Data;

import javax.persistence.*;

@Table(name = "prices")
@Entity
@Data
public class    PriceEntity extends BaseEntity
{
    @ManyToOne
    @JoinColumn(name="product_id", nullable=false)
    private ProductEntity product;

    private double price;

    @Enumerated(EnumType.STRING)
    private PriceType priceType;
}
