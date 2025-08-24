package com.ntech.dispatchloadbalancer.controller;

import com.ntech.dispatchloadbalancer.model.*;
import com.ntech.dispatchloadbalancer.model.dto.DispatchPlanResponse;
import com.ntech.dispatchloadbalancer.model.dto.PlanResponse;
import com.ntech.dispatchloadbalancer.service.DispatchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PlanController.class)
class PlanControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DispatchService dispatchService;

    @Test
    @DisplayName("Dispatch plan should return expected plan")
    void testGetDispatchPlan_should_return_plan() throws Exception {
        when(dispatchService.getDispatchPlan()).thenReturn(expectedPlan);

        mockMvc.perform(get("/api/dispatch/plan"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.dispatchPlan").isArray())
                .andExpect(jsonPath("$.unassignedOrders").isArray());
    }

    @Test
    @DisplayName("Dispatch plan should return empty array, if no data is present")
    void testGetDispatchPlan_should_return_empty_plan() throws Exception {
        PlanResponse mockPlanResponse = new PlanResponse(Collections.emptyList(), Collections.emptyList());
        when(dispatchService.getDispatchPlan()).thenReturn(mockPlanResponse);

        mockMvc.perform(get("/api/dispatch/plan"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.dispatchPlan").isArray())
                .andExpect(jsonPath("$.unassignedOrders").isArray());
    }

    private PlanResponse expectedPlan;

    @BeforeEach
    void setUp() {
        List<DispatchPlan> dispatchPlan = List.of(
                new DispatchPlan(new DispatchVehicle("VEH001", 10.0, 5.060290248649063),
                        List.of(new Order("ORD001", 12.9716, 77.5946, "MG Road, Bangalore, Karnataka, India", 10.0, Priority.HIGH))),
                new DispatchPlan(new DispatchVehicle("VEH002", 20.0, 3.9683163194217443),
                        List.of(new Order("ORD002", 13.0827, 80.2707, "Anna Salai, Chennai, Tamil Nadu, India", 20.0, Priority.MEDIUM)))
        );
        List<DispatchPlanResponse> dispatchPlanResponse = dispatchPlan.stream().map(DispatchPlanResponse::getResponse).toList();
        expectedPlan = new PlanResponse(dispatchPlanResponse,new ArrayList<>());
    }
}