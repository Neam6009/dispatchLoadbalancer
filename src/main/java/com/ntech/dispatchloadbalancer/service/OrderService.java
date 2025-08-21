package com.ntech.dispatchloadbalancer.service;

import com.ntech.dispatchloadbalancer.model.Order;
import com.ntech.dispatchloadbalancer.repo.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    @Autowired
    public OrderService(OrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }

    public void addOrders(List<Order> orders){
        orderRepository.saveAll(orders);
    }

    public List<Order> getAllOrders(){
        return orderRepository.findAll();
    }
}
