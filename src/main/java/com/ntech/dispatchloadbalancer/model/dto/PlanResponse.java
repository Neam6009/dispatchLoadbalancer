package com.ntech.dispatchloadbalancer.model.dto;

import com.ntech.dispatchloadbalancer.model.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlanResponse {
    List<DispatchPlanResponse> dispatchPlan;
    List<Order> unassignedOrders;
}
