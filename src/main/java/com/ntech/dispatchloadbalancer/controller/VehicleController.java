package com.ntech.dispatchloadbalancer.controller;

import com.ntech.dispatchloadbalancer.model.Status;
import com.ntech.dispatchloadbalancer.model.Vehicle;
import com.ntech.dispatchloadbalancer.model.dto.PostResponse;
import com.ntech.dispatchloadbalancer.model.dto.VehicleRequestList;
import com.ntech.dispatchloadbalancer.service.VehicleService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/dispatch/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping
    public PostResponse addVehicle(@Valid @RequestBody VehicleRequestList vehicleRequestList) {
        vehicleService.addVehicles(vehicleRequestList.getVehicles());
        return new PostResponse("Vehicle details accepted.", Status.success);
    }
}
