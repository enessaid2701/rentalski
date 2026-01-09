package com.library.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "entries")
public class OrderEntry extends BaseEntity
{
    @ManyToOne
    @JoinColumn(name="order_id", nullable=false)
    private Order order;

    @ManyToOne
    @JoinColumn(name="product_id", nullable=false)
    private ProductEntity product;

    private Double sledTime;
    private Double price;
}
