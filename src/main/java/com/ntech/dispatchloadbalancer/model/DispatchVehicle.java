package com.ntech.dispatchloadbalancer.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DispatchVehicle {
    private String vehicleId;
    private Double totalLoad;
    private Double totalDistance;
}
