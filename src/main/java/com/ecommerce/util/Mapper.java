package com.ecommerce.util;

import java.util.List;
import java.util.stream.Collectors;

import com.ecommerce.dto.*;
import com.ecommerce.entity.*;

public class Mapper {

    public static ProductDTO toProductDTO(Product p) {
        ProductDTO dto = new ProductDTO();
        dto.setId(p.getId());
        dto.setName(p.getName());
        dto.setDescription(p.getDescription());
        dto.setPrice(p.getPrice());
        dto.setStock(p.getStock());
        dto.setImageUrl(p.getImageUrl());
        return dto;
    }

    public static OrderDTO toOrderDTO(Order order) {

        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setTotalPrice(order.getTotalPrice());
        dto.setStatus(order.getStatus().name());
        dto.setAddress(order.getAddress());

        List<OrderItemDTO> items = order.getItems().stream().map(i -> {
            OrderItemDTO item = new OrderItemDTO();
            item.setProductName(i.getProduct().getName());
            item.setQuantity(i.getQuantity());
            item.setPrice(i.getPrice());
            return item;
        }).collect(Collectors.toList());

        dto.setItems(items);

        return dto;
    }
}