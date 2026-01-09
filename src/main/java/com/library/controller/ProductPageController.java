package com.library.controller;

import com.library.dto.ProductDTO;
import com.library.entity.ProductEntity;
import com.library.repository.ProductRepository;
import com.library.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ProductPageController
{
    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;


    @GetMapping("/get-product")
    public String getProduct(Model model, HttpServletRequest request)
    {
        model.addAttribute("products", productService.getAllProduct());
        return "index3";
    }

    @PostMapping("/create-product")
    public String createProduct(@RequestBody ProductDTO dto)
    {
        productService.createProduct(dto);
        return "index4";
    }

    @PutMapping("/update-product")
    public String updateProduct(@RequestBody ProductDTO dto)
    {
        productService.updateProduct(dto);
        return "index3";
    }
    @DeleteMapping("/delete-product")
    public String deleteProduct(@RequestBody Long ProductId)
    {
        productService.deleteProduct(ProductId);
        return "index3";
    }

}
