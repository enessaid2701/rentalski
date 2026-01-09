package com.library.repository;

import com.library.entity.ProductEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<ProductEntity, Long>
{
    public ProductEntity findByCode(String code);
}
