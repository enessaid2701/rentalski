package com.library.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "products")
public class ProductEntity extends BaseEntity
{
    public String code;

    @Column(name = "displayName")
    public String displayName;

    @Column(name = "order_number")
    private Integer orderNumber;

    @OneToOne
    @JoinColumn(name="product_id", nullable=true)
    private ProductEntity parentProduct;

    @Enumerated(EnumType.STRING)
    private RentalType rentalType;
}
