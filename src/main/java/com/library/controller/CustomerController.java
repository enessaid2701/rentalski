package com.library.controller;

import com.library.dto.CustomerDTO;
import com.library.dto.EntryDTO;
import com.library.dto.OrderDTO;
import com.library.entity.CustomerEntity;
import com.library.entity.Order;
import com.library.entity.OrderEntry;
import com.library.repository.CustomerRepository;
import com.library.repository.OrderEntryRepository;
import com.library.repository.OrderRepository;
import com.library.service.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/customer")
public class CustomerController
{
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderEntryRepository orderEntryRepository;

    @Autowired
    private WebSocketService webSocketService;

    @GetMapping("/search/{phone-number}")
    @ResponseBody
    public List<CustomerEntity> searchCustomer(@PathVariable(name = "phone-number") String phoneNumber)
    {
        return customerRepository.findAllByPhoneNumber(phoneNumber);
    }

    @GetMapping("/detail/{customer-id}")
    public String searchCustomer(@PathVariable(name = "customer-id") long id, Model model)
    {
        CustomerEntity customer  = customerRepository.findById(id).get();

        CustomerDTO dto = CustomerDTO
            .builder()
            .fullName(customer.getFullname())
            .phoneNumber(customer.getPhoneNumber())
            .customerId(customer.getId())
            .build();
        model.addAttribute("customer", dto);
        return "customer-detail";
    }

    @GetMapping("/orders/{customer-id}")
    @ResponseBody
    public List<OrderDTO> seachOrder(@PathVariable(name = "customer-id") long id)
    {
        CustomerEntity customer  = customerRepository.findById(id).get();
        List<Order> orders = orderRepository.findAllByCustomer(customer);
        List<OrderDTO> dtos = new ArrayList<>();

        for (Order order : orders) {
            OrderDTO orderDTO = new OrderDTO();
            List<EntryDTO> entries = new ArrayList<>();

            for (OrderEntry entry : orderEntryRepository.findAllByOrder(order)) {
                EntryDTO eD = new EntryDTO();
                eD.setPrice(String.valueOf(entry.getPrice()));
                eD.setName(entry.getProduct().getDisplayName());

                entries.add(eD);
            }
            orderDTO.setId(order.getId());
            orderDTO.setGender(order.getGender());
            orderDTO.setEntries(entries);
            orderDTO.setDate(dateToString(order.getCreatedAt()));
            orderDTO.setTotalPrice(String.valueOf(order.getTotalPrice()));
            dtos.add(orderDTO);
        }

        Collections.sort(dtos);
        Collections.reverse(dtos);

        return dtos;
    }

    @GetMapping("/order/print/{order-id}")
    @ResponseBody
    public void print(@PathVariable(name = "order-id") long id, HttpServletRequest req)
    {
        Integer pId = (Integer) req.getSession().getAttribute("printer-code");
        Order order = orderRepository.findById(id).get();
        webSocketService.sendToPrint(order,pId );
    }

    String dateToString(Date d)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-M-yyyy HH:mm:ss");
        return dateFormat.format(d);
    }
}
