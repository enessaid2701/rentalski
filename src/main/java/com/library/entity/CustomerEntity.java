package com.library.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "customers")
@Entity
@Data
public class CustomerEntity extends BaseEntity
{
    private String fullname;
    @Column(name = "phone_number")
    private String phoneNumber;
    private String email;

    private Integer age;
    private Integer weight;
    private Integer height;

    @Column(name = "shoe_size")
    private Double shoeSize;
}
