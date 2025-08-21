package com.ntech.dispatchloadbalancer.service;

import com.ntech.dispatchloadbalancer.model.Vehicle;
import com.ntech.dispatchloadbalancer.repo.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleService {
    VehicleRepository vehicleRepository;

    @Autowired
    public VehicleService(VehicleRepository vehicleRepository){
        this.vehicleRepository = vehicleRepository;
    }

    public void addVehicles(List<Vehicle> vehicles){
        vehicleRepository.saveAll(vehicles);
    }

    public List<Vehicle> getAllVehicles(){
        return vehicleRepository.findAll();
    }
}
