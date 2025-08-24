package com.ntech.dispatchloadbalancer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ntech.dispatchloadbalancer.model.Order;
import com.ntech.dispatchloadbalancer.model.Priority;
import com.ntech.dispatchloadbalancer.model.dto.OrderRequestList;
import com.ntech.dispatchloadbalancer.service.OrderService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OrderService orderService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Valid orders should return status 200")
    void addOrders_whenValidInput_thenReturns200() throws Exception {
        OrderRequestList orderRequest = new OrderRequestList();
        orderRequest.setOrders(new ArrayList<Order>(List.of(
                new Order("ORD001", 12.9716, 77.5946, "MG Road, Bangalore, Karnataka, India", 10.0, Priority.HIGH)
        ))); // a sample valid order
        doNothing().when(orderService).addOrders(any(List.class));

        mockMvc.perform(post("/api/dispatch/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Delivery orders accepted."))
                .andExpect(jsonPath("$.status").value("success"));
    }

    @Test
    @DisplayName("Empty orders should return status 400")
    void addOrders_whenEmptyList_thenReturns400() throws Exception {
        OrderRequestList orderRequest = new OrderRequestList();
        orderRequest.setOrders(Collections.emptyList());

        mockMvc.perform(post("/api/dispatch/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderRequest)))
                .andExpect(status().isBadRequest());
    }
}