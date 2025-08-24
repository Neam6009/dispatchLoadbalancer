package com.ntech.dispatchloadbalancer.service;

import com.ntech.dispatchloadbalancer.model.*;
import com.ntech.dispatchloadbalancer.model.dto.DispatchPlanResponse;
import com.ntech.dispatchloadbalancer.model.dto.PlanResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DispatchServiceTest {

    @Mock
    OrderService orderService;
    @Mock
    VehicleService vehicleService;

    @InjectMocks
    DispatchService dispatchService;

    @Test
    @DisplayName("Dispatch plan should assign order to closest vehicle")
    void testGetDispatchPlan_shouldAssignToClosestVehicle() {
        when(orderService.getAllOrders()).thenReturn(new ArrayList<>(List.of(highPriorityOrder)));
        when(vehicleService.getAllVehicles()).thenReturn(List.of(vehicle1, vehicle2));
        PlanResponse planResponse = dispatchService.getDispatchPlan();

        assertNotNull(planResponse);
        assertEquals(1, planResponse.getDispatchPlan().size());
        assertTrue(planResponse.getUnassignedOrders().isEmpty());
        assertEquals("VEH001", planResponse.getDispatchPlan().get(0).getVehicleId());
        assertEquals(1, planResponse.getDispatchPlan().get(0).getAssignedOrders().size());
    }

    @Test
    @DisplayName("Dispatch plan should prioritize high priority orders")
    void testGetDispatchPlan_shouldPrioritizeHighPriorityOrders() {
        Vehicle limitedCapacityVehicle = new Vehicle("VEH003", 30.0, 28.7041, 77.1025, "Karol Bagh, Delhi");
        when(orderService.getAllOrders()).thenReturn(new ArrayList<>(List.of(mediumPriorityOrder, highPriorityOrder))); // Unsorted list
        when(vehicleService.getAllVehicles()).thenReturn(List.of(limitedCapacityVehicle));
        PlanResponse planResponse = dispatchService.getDispatchPlan();

        assertEquals(1, planResponse.getDispatchPlan().size());
        assertEquals(1, planResponse.getUnassignedOrders().size());
        assertEquals("ORD001", planResponse.getDispatchPlan().get(0).getAssignedOrders().get(0).getOrderId());
        assertEquals("ORD002", planResponse.getUnassignedOrders().get(0).getOrderId());
    }

    @Test
    @DisplayName("Orders should be unassigned when package weight exceeds capacity")
    void testGetDispatchPlan_whenOrderExceedsCapacity_shouldBeUnassigned() {
        when(orderService.getAllOrders()).thenReturn(new ArrayList<>(List.of(oversizedOrder)));
        when(vehicleService.getAllVehicles()).thenReturn(List.of(vehicle1, vehicle2));
        PlanResponse planResponse = dispatchService.getDispatchPlan();

        assertTrue(planResponse.getDispatchPlan().isEmpty());
        assertEquals(1, planResponse.getUnassignedOrders().size());
        assertEquals("ORD003", planResponse.getUnassignedOrders().get(0).getOrderId());
    }

    @Test
    @DisplayName("Dispatch plan should return empty plan when there are no orders")
    void testGetDispatchPlan_whenNoOrders_shouldReturnEmptyPlan() {
        when(orderService.getAllOrders()).thenReturn(Collections.emptyList());
        when(vehicleService.getAllVehicles()).thenReturn(List.of(vehicle1));
        PlanResponse planResponse = dispatchService.getDispatchPlan();

        assertTrue(planResponse.getDispatchPlan().isEmpty());
        assertTrue(planResponse.getUnassignedOrders().isEmpty());
    }

    @Test
    @DisplayName("Dispatch plan should return all orders as unassigned orders when there are no vehicles")
    void testGetDispatchPlan_whenNoVehicles_allOrdersShouldBeUnassigned() {
        when(orderService.getAllOrders()).thenReturn(new ArrayList<>(List.of(highPriorityOrder)));
        when(vehicleService.getAllVehicles()).thenReturn(Collections.emptyList());
        PlanResponse planResponse = dispatchService.getDispatchPlan();

        assertTrue(planResponse.getDispatchPlan().isEmpty());
        assertEquals(1, planResponse.getUnassignedOrders().size());
        assertEquals("ORD001", planResponse.getUnassignedOrders().get(0).getOrderId());
    }


    private Vehicle vehicle1;
    private Vehicle vehicle2;
    private Order highPriorityOrder;
    private Order mediumPriorityOrder;
    private Order oversizedOrder;
    @BeforeEach
    void setUp() {
        vehicle1 = new Vehicle("VEH001", 100.0, 28.7041, 77.1025, "Karol Bagh, Delhi");
        vehicle2 = new Vehicle("VEH002", 120.0, 28.4595, 77.0266, "Cyber Hub, Gurgaon");
        highPriorityOrder = new Order("ORD001", 28.6139, 77.2090, "Connaught Place, Delhi", 15.0, Priority.HIGH);
        mediumPriorityOrder = new Order("ORD002", 28.7041, 77.1025, "Karol Bagh, Delhi", 20.0, Priority.MEDIUM);
        oversizedOrder = new Order("ORD003", 28.5355, 77.3910, "Sector 18, Noida", 200.0, Priority.LOW);
    }
}