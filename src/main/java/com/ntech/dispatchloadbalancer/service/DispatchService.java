package com.ntech.dispatchloadbalancer.service;

import com.ntech.dispatchloadbalancer.model.DispatchPlan;
import com.ntech.dispatchloadbalancer.model.DispatchVehicle;
import com.ntech.dispatchloadbalancer.model.Order;
import com.ntech.dispatchloadbalancer.model.Vehicle;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class DispatchService {

    private final OrderService orderService;
    private final VehicleService vehicleService;

    private final int EARTH_RADIUS = 6371;

    public DispatchService(OrderService orderService, VehicleService vehicleService) {
        this.orderService = orderService;
        this.vehicleService = vehicleService;
    }

    double haversine(double val){
        return Math.pow(Math.sin(val/2),2);
    }

    private double getDistance(double startLat, double startLong, double endLat, double endLong){
        double latDistance = Math.toRadians((endLat - startLat));
        double longDistance = Math.toRadians((endLong - startLong));

        double startLatRad = Math.toRadians(startLat);
        double endLatRad = Math.toRadians(endLat);

        double a = haversine(latDistance) + Math.cos(startLatRad) * Math.cos(endLatRad) *haversine(longDistance);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c;
    }

    public List<DispatchPlan> getDispatchPlan() {
        List<Order> orders = orderService.getAllOrders();
        List<Vehicle> vehicles = vehicleService.getAllVehicles();
        orders.sort(Comparator.comparing(Order::getPriority));
        HashMap<String,DispatchPlan> vehicleIdMap = new HashMap<>();
        orders.forEach(order -> {
            double minDistance = Double.MAX_VALUE;
            Vehicle minVehicle = new Vehicle();
            for(Vehicle vehicle : vehicles){
                if(vehicle.getCapacity() > order.getPackageWeight()){
                    double currDistance = getDistance(vehicle.getCurrentLatitude(),vehicle.getCurrentLongitude(),
                            order.getLatitude(),order.getLongitude());
                    if( currDistance < minDistance){
                        minDistance = currDistance;
                        minVehicle = vehicle;
                    }
                }
            }
            Vehicle newVehicle = new Vehicle(minVehicle.getVehicleId(),minVehicle.getCapacity()-order.getPackageWeight(),
                    minVehicle.getCurrentLatitude(),minVehicle.getCurrentLongitude(),minVehicle.getCurrentAddress());
            if(minDistance !=  Double.MAX_VALUE){
                vehicles.set(vehicles.indexOf(minVehicle), newVehicle);
            DispatchPlan mapVal = vehicleIdMap.getOrDefault(minVehicle.getVehicleId(),
                    new DispatchPlan(new DispatchVehicle(minVehicle.getVehicleId(),0,0),new ArrayList<>()));
            double newTotalDistance = mapVal.getVehicle().getTotalDistance() + minDistance;
            int newTotalLoad = mapVal.getVehicle().getTotalLoad() + order.getPackageWeight();
            mapVal.getVehicle().setTotalDistance(newTotalDistance);
            mapVal.getVehicle().setTotalLoad(newTotalLoad);
            mapVal.getAssignedOrders().add(order);
            vehicleIdMap.put(minVehicle.getVehicleId(),mapVal);
            }

        });
        return vehicleIdMap.values().stream().toList();
    }

}
