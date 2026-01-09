package com.library.service;

import com.library.dto.CustomerDTO;
import com.library.dto.EntryDTO;
import com.library.dto.PrintData;
import com.library.entity.CustomerEntity;
import com.library.entity.Order;
import com.library.entity.OrderEntry;
import com.library.repository.OrderEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class WebSocketService
{
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private OrderEntryRepository orderEntryRepository;

    public void sendToPrint(Order order, Integer printerId)
    {
        CustomerEntity customer = order.getCustomer();
        List<OrderEntry> entriesFromDB = orderEntryRepository.findAllByOrder(order);
        List<EntryDTO> entries = new ArrayList<>();

        for (OrderEntry orderEntry : entriesFromDB) {
            EntryDTO dto = new EntryDTO();
            dto.setPrice(orderEntry.getPrice().toString() +"TL");
            dto.setProductCode(orderEntry.getProduct().getCode());
            if(orderEntry.getProduct().getCode().equals("sled") || orderEntry.getProduct().getCode().equals("iceskate"))
            {
                dto.setSledTime(orderEntry.getSledTime());
            }

            entries.add(dto);
        }

        PrintData printData = new PrintData();
        printData.setDate(dateToString(order.getCreatedAt()));
        printData.setOrderNumber(order.getId().toString());
        printData.setEntries(entries);
        printData.setTotalPrice(order.getTotalPrice().toString()+"TL");
        CustomerDTO customerDTO = CustomerDTO.builder()
            .phoneNumber(customer.getPhoneNumber())
            .fullName(customer.getFullname())
            .build();

        printData.setCustomerDTO(customerDTO);
        simpMessagingTemplate.convertAndSend("/topic/printer-"+printerId.toString(), printData);
    }

    String dateToString(Date d)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-M-yyyy HH:mm:ss");
        return dateFormat.format(d);
    }
}
