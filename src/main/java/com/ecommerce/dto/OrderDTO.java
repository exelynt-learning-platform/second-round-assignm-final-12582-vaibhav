package com.ecommerce.dto;

import java.util.List;

import lombok.Data;

@Data
public class OrderDTO {

    private Long id;
    private double totalPrice;
    private String status;
    private String address;
    private List<OrderItemDTO> items;
}