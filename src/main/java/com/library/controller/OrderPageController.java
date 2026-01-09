package com.library.controller;

import com.library.dto.*;
import com.library.entity.Order;
import com.library.entity.OrderEntry;
import com.library.entity.OrderStatus;
import com.library.repository.CustomerRepository;
import com.library.repository.OrderEntryRepository;
import com.library.repository.OrderRepository;
import com.library.service.AuthenticationService;
import com.library.service.OrderService;
import com.library.service.ProductService;
import com.library.service.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Controller
public class OrderPageController
{
    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository  orderRepository;

    @Autowired
    private OrderEntryRepository orderEntryRepository;

    @Autowired
    private AuthenticationService authenticationService;

    @GetMapping("/create-order")
    public String gotoCreateOrder(Model model, HttpServletRequest request)
    {
        model.addAttribute("products", productService.getAllProduct());
        model.addAttribute("printerId", request.getSession().getAttribute("printer-code"));
        return "create-order";
    }

    @GetMapping("/")
    public String gotoHomePage(Model model, HttpServletRequest request)
    {
        model.addAttribute("showCancel", authenticationService.isAdmin());
        return "redirect:/create-order";
    }

    @PostMapping("/create-order")
    @ResponseBody
    public String create(@RequestBody CreateOrderRequest req, HttpServletRequest request)
    {
        request.getSession().setAttribute("printer-code", req.getPrinterCode());
        orderService.createOrder(req);

        return "Ok";
    }

    @GetMapping("/order-search/{order-code}")
    @ResponseBody
    public OrderDTO search(@PathVariable(name = "order-code") long orderId)
    {
        Order order = orderRepository.findById(orderId).get();
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
        orderDTO.setStatus(order.getOrderStatus() != null ? order.getOrderStatus().name() : "OK");
        return orderDTO;
    }

    @GetMapping("/cancel-order/{order-code}")
    @ResponseBody
    public void cancelOrder(@PathVariable(name = "order-code") long orderId)
    {
        if(authenticationService.isAdmin())
        {
            Order order = orderRepository.findById(orderId).get();
            order.setOrderStatus(OrderStatus.CANCELLED);

            orderRepository.save(order);
        }
    }

    String dateToString(Date d)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-M-yyyy HH:mm:ss");
        return dateFormat.format(d);
    }
    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception
    {
        System.out.println(message.getName());
        return new Greeting("Hello, " + message.getName() + "!");
    }

    @GetMapping("/customers")
    public String orders()
    {
        return "customers";
    }
}
