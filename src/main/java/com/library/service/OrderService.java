package com.library.service;

import com.library.dto.CreateOrderRequest;
import com.library.entity.*;
import com.library.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderService
{
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderEntryRepository orderEntryRepository;

    @Autowired
    private WebSocketService webSocketService;

    @Autowired
    private PriceRepository priceRepository;

    @Autowired
    private CustomerRepository customerRepository;


    public void createOrder(CreateOrderRequest req)
    {
        Order order = new Order();
        order.setCustomer(createCustomerIfNeeded(req.getCustomer()));
        order.setOrderStatus(OrderStatus.OK);
        Set<OrderEntry> entries= new HashSet<>();
        PriceType type = PriceType.valueOf(req.getOrderType().toUpperCase());

        order.setGender(req.getGender().equals("male") ? "ERKEK" : "KADIN");
        List<String> products = getProducts(req);

        boolean useDiscount = req.isExtraDiscount();
        double discountRate = req.getDiscountRate();

        for (String product : products)
        {
            ProductEntity pEn = productRepository.findByCode(product);
            PriceEntity price = priceRepository.findByProductAndPriceType(pEn, type);

            OrderEntry entry = new OrderEntry();
            entry.setOrder(order);
            entry.setProduct(pEn);


            if(product.equals("sled"))
            {
                Double calculatedPrice = price.getPrice() * req.getSledTime();

                if(useDiscount)
                {
                    Double calculatedDiscount = (calculatedPrice * discountRate) / 100;
                    calculatedPrice = calculatedPrice - calculatedDiscount;
                }
                entry.setPrice(calculatedPrice);
                entry.setSledTime(req.getSledTime());
            }
            else if (product.equals("iceskate")){
                Double calculatedPrice = price.getPrice() * req.getIceskateHour();

                if(useDiscount)
                {
                    Double calculatedDiscount = (calculatedPrice * discountRate) / 100;
                    calculatedPrice = calculatedPrice - calculatedDiscount;
                }
                entry.setPrice(calculatedPrice);
                entry.setSledTime(req.getIceskateHour());
            }
            else{
                Double calculatedPrice = price.getPrice();

                if(useDiscount)
                {
                    Double calculatedDiscount = (calculatedPrice * discountRate) / 100;
                    calculatedPrice = calculatedPrice - calculatedDiscount;
                }
                entry.setPrice(calculatedPrice);
            }
            entries.add(entry);
        }

        double total = 0d;
        for (OrderEntry entry : entries) {
            if (entry.getPrice() != null) {
                total += entry.getPrice();
            }
        }
        order.setTotalPrice(total);
        order.setPriceType(type);  // PriceType'ı order'a set et
        order = orderRepository.save(order);
        orderEntryRepository.saveAll(entries);

        webSocketService.sendToPrint(order, req.getPrinterCode());
    }

    public List<String> getProducts(CreateOrderRequest request)
    {
        List<String> products = new ArrayList<>();
        if(request.getBoard().booleanValue()) products.add("board");
        if(request.getSky().booleanValue()) products.add("sky");
        if(request.getCoats().booleanValue()) products.add("coats");
        if(request.getGlasses().booleanValue()) products.add("glasses");
        if(request.getHelmet().booleanValue()) products.add("helmet");
        if(request.getSled().booleanValue()) products.add("sled");
        if(request.getIceskate().booleanValue()) products.add("iceskate");
        if(request.getCabinet().booleanValue()) products.add("cabinet");
        if(request.getNightsky().booleanValue()) products.add("nightsky");
        if(request.getGlove().booleanValue()) products.add("glove");
        if(request.getSocks().booleanValue()) products.add("socks");
        return products;
    }

    private CustomerEntity createCustomerIfNeeded(CustomerEntity customer)
    {
        if(customer.getId() == null)
        {
            customer = customerRepository.save(customer);
        }

        return customer;
    }
}
