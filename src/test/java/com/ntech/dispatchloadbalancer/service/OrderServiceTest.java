package com.ntech.dispatchloadbalancer.service;

import com.ntech.dispatchloadbalancer.model.Order;
import com.ntech.dispatchloadbalancer.model.Priority;
import com.ntech.dispatchloadbalancer.repo.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    private List<Order> sampleOrders;

    @BeforeEach
    void setup() {
        sampleOrders = List.of(
                new Order("ORD001", 12.9716, 77.5946, "MG Road", 10.0, Priority.HIGH),
                new Order("ORD002", 13.0827, 80.2707, "Anna Salai", 20.0, Priority.MEDIUM)
        );
    }

    @Test
    @DisplayName("Get orders should return proper order list")
    void testGetAllOrders_should_return_proper_orders() {
        when(orderRepository.findAll()).thenReturn(sampleOrders);
        List<Order> result = orderService.getAllOrders();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("ORD001", result.get(0).getOrderId());
    }

    @Test
    @DisplayName("Get orders should return empty list when there are no orders")
    void testGetAllOrders_should_return_empty_when_no_orders() {
        when(orderRepository.findAll()).thenReturn(Collections.emptyList());
        List<Order> result = orderService.getAllOrders();

        assertNotNull(result);
        assertEquals(0, result.size());
    }
}