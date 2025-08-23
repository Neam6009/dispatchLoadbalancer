package com.ntech.dispatchloadbalancer.model.dto;

import com.ntech.dispatchloadbalancer.model.DispatchPlan;
import com.ntech.dispatchloadbalancer.model.DispatchVehicle;
import com.ntech.dispatchloadbalancer.model.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DispatchPlanResponse {
    private String vehicleId;
    private Double totalLoad;
    private String totalDistance;
    private List<Order> assignedOrders;

    public static DispatchPlanResponse getResponse(DispatchPlan dispatchPlan){
        return new DispatchPlanResponse(
                dispatchPlan.getVehicle().getVehicleId(),
                dispatchPlan.getVehicle().getTotalLoad(),
                Math.round(dispatchPlan.getVehicle().getTotalDistance()) + " km",
                dispatchPlan.getAssignedOrders()
        );
    }
}
