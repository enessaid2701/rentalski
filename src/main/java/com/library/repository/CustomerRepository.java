package com.library.repository;

import com.library.entity.CustomerEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends CrudRepository<CustomerEntity, Long>
{
    public List<CustomerEntity> findAllByPhoneNumber(String phoneNumber);
}
