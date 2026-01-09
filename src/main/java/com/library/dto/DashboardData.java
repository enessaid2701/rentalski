package com.library.dto;

import lombok.Data;

@Data
public class DashboardData {
    private String displayName;
    private Double totalPrice;

    public DashboardData() {
    }

    public DashboardData(String displayName, Double totalPrice) {
        this.displayName = displayName;
        this.totalPrice = totalPrice;
    }
}
