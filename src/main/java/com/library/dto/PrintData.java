package com.library.dto;

import com.library.entity.CustomerEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrintData
{
    private String orderNumber;
    private String date;
    private CustomerDTO customerDTO;
    private List<EntryDTO> entries;
    private String totalPrice;
}
