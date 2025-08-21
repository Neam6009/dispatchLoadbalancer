package com.ntech.dispatchloadbalancer.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class DispatchPlan {
    private DispatchVehicle vehicle;
    private List<Order> assignedOrders;
}
