package com.library.service;

import com.library.dto.ProductDTO;
import com.library.entity.PriceEntity;
import com.library.entity.ProductEntity;
import com.library.repository.PriceRepository;
import com.library.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductService
{
    @Autowired
    private ProductRepository productRepository;
//
    @Autowired
    private PriceRepository priceRepository;

    public List<ProductDTO> getAllProduct()
    {
        List<ProductDTO> maps = new ArrayList<>();

        Iterator<ProductEntity> it = productRepository.findAll().iterator();
        it.forEachRemaining(productEntity -> {
            List<PriceEntity> prices = priceRepository.findAllByProduct(productEntity);
            HashMap<String, PriceEntity> pricesMap = new HashMap<>();

            for (PriceEntity price : prices)
            {
                pricesMap.put(price.getPriceType().name(), price);
            }

            maps.add(
                    ProductDTO.builder()
                        .code(productEntity.getCode())
                        .displayName(productEntity.getDisplayName())
                        .prices(pricesMap)
                        .orderNumber(productEntity.getOrderNumber())
                        .build()
                );
            }
        );

        Collections.sort(maps);
        return maps;
    }

    public void createProduct(ProductDTO dto)
    {
        ProductEntity product = new ProductEntity();
        product.setCode(dto.getCode());
        product.setDisplayName(dto.getDisplayName());
        product.setOrderNumber(dto.getOrderNumber());

        productRepository.save(product);
    }

    public void deleteProduct(Long id)
    {
        Optional<ProductEntity> productEntityOptional = productRepository.findById(id);
        productEntityOptional.ifPresent(productEntity -> productRepository.delete(productEntity));
    }

    public void updateProduct(ProductDTO dto)
    {
        ProductEntity product = new ProductEntity();
        product.setCode(dto.getCode());
        product.setDisplayName(dto.getDisplayName());
        product.setOrderNumber(dto.getOrderNumber());

        productRepository.save(product);
    }
}
