package com.library.dto;

import com.library.entity.PriceEntity;
import com.library.entity.ProductEntity;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class ProductDTO implements Comparable<ProductDTO>
{
    private Long id;
    private String code;
    private String displayName;
    private Map<String, PriceEntity> prices;
    private Integer orderNumber;

    @Override
    public int compareTo(ProductDTO o) {
        return getOrderNumber().compareTo(o.getOrderNumber());
    }
}
