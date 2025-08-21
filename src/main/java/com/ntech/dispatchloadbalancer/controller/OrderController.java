package com.ntech.dispatchloadbalancer.controller;

import com.ntech.dispatchloadbalancer.model.Order;
import com.ntech.dispatchloadbalancer.model.Status;
import com.ntech.dispatchloadbalancer.model.dto.PostResponse;
import com.ntech.dispatchloadbalancer.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/dispatch/orders")
public class OrderController {

    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }

    @PostMapping
    public PostResponse addOrders(@RequestBody List<Order> orders){
        orderService.addOrders(orders);
        return new PostResponse("Delivery orders accepted.", Status.success);
    }
}
