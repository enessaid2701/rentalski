package com.library.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderDTO implements Comparable<OrderDTO>
{
    Long id;
    String date;
    String gender;
    String status;
    List<EntryDTO> entries;
    String totalPrice;

    @Override
    public int compareTo(OrderDTO o) {
        return getId().compareTo(o.getId());
    }
}
