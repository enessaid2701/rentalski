package com.library.repository;

import com.library.entity.PriceEntity;
import com.library.entity.PriceType;
import com.library.entity.ProductEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PriceRepository extends CrudRepository<PriceEntity, Long>
{
    public List<PriceEntity> findAllByProduct(ProductEntity product);
    public PriceEntity findByProductAndPriceType(ProductEntity product, PriceType type);
}
