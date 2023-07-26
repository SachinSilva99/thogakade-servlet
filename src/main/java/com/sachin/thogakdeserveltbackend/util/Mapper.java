package com.sachin.thogakdeserveltbackend.util;


import com.sachin.thogakdeserveltbackend.dto.*;
import com.sachin.thogakdeserveltbackend.entity.*;
import com.sachin.thogakdeserveltbackend.dto.CustomerDTO;
import com.sachin.thogakdeserveltbackend.dto.ItemDTO;
import com.sachin.thogakdeserveltbackend.dto.OrderDTO;
import com.sachin.thogakdeserveltbackend.entity.Customer;
import com.sachin.thogakdeserveltbackend.entity.Item;
import com.sachin.thogakdeserveltbackend.entity.Orders;

import java.util.ArrayList;

public class Mapper {
    public Customer toCustomer(CustomerDTO customerDTO) {
        return new Customer(
                customerDTO.getId(),
                customerDTO.getName(),
                customerDTO.getAddress(),
                new ArrayList<>());
    }

    public CustomerDTO toCustomerDTO(Customer customer) {
        return new CustomerDTO(
                customer.getId(),
                customer.getName(),
                customer.getAddress()
        );
    }

    public Item toItem(ItemDTO itemDTO) {
        return new Item(
                itemDTO.getCode(),
                itemDTO.getDescription(),
                itemDTO.getUnitPrice(),
                itemDTO.getQtyOnHand(),
                new ArrayList<>());
    }

    public ItemDTO toItemDTO(Item item) {
        return new ItemDTO(
                item.getCode(),
                item.getDescription(),
                item.getUnitPrice(),
                item.getQtyOnHand()
        );
    }

    public OrderDTO toOrderDto(Orders orders) {
        return new OrderDTO(
                orders.getOrderId(),
                orders.getDate(),
                orders.getCustomer().getId()
        );
    }

    public Orders toOrders(OrderDTO orderDTO) {
        return new Orders(orderDTO.getOrderId(), orderDTO.getDate(), null, null);
    }

    public OrderDetailDTO toOrderDetailDto(OrderDetail od) {
        return new OrderDetailDTO(od.getOrders().getOrderId(), od.getItem().getCode(), od.getItem().getDescription(), od.getQty(), od.getUnitPrice());
    }
}
