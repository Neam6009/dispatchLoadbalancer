package com.ntech.dispatchloadbalancer.service;

import com.ntech.dispatchloadbalancer.model.DispatchPlan;
import com.ntech.dispatchloadbalancer.model.DispatchVehicle;
import com.ntech.dispatchloadbalancer.model.Order;
import com.ntech.dispatchloadbalancer.model.Vehicle;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

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

    Double haversine(Double val){
        return Math.pow(Math.sin(val/2),2);
    }

    private Double getDistance(Double startLat, Double startLong, Double endLat, Double endLong){
        Double latDistance = Math.toRadians((endLat - startLat));
        Double longDistance = Math.toRadians((endLong - startLong));

        double startLatRad = Math.toRadians(startLat);
        double endLatRad = Math.toRadians(endLat);

        double a = haversine(latDistance) + Math.cos(startLatRad) * Math.cos(endLatRad) *haversine(longDistance);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c;
    }

    public List<DispatchPlan> getDispatchPlan() {
        List<Order> orders = orderService.getAllOrders();
        List<Vehicle> vehicles = vehicleService.getAllVehicles();
        orders.sort(Comparator.comparing(Order::getPriority).reversed());
        Map<String,Vehicle> vehicleStateMap = vehicles.stream().collect(Collectors.toConcurrentMap(Vehicle::getVehicleId, vehicle->vehicle));
        HashMap<String,DispatchPlan> dispatchPlanMap = new HashMap<>();

        orders.forEach(order -> {
            Optional<Vehicle> vehicle_with_minimum_distance = vehicleStateMap.values().parallelStream()
                    .filter(vehicle -> vehicle.getCapacity() >= order.getPackageWeight())
                    .min(Comparator.comparingDouble(vehicle ->
                            getDistance(vehicle.getCurrentLatitude(),vehicle.getCurrentLongitude(),
                                    order.getLatitude(),order.getLongitude())));

            if(vehicle_with_minimum_distance.isPresent()){
                Vehicle carrierVehicle = vehicle_with_minimum_distance.get();
                double minimumDistance = getDistance(carrierVehicle.getCurrentLatitude(),
                        carrierVehicle.getCurrentLongitude(),order.getLatitude(),order.getLongitude());
                //update the values for the vehicle with the minimum distance
                carrierVehicle.setCapacity(carrierVehicle.getCapacity()-order.getPackageWeight());
                carrierVehicle.setCurrentLatitude(order.getLatitude());
                carrierVehicle.setCurrentLongitude(order.getLongitude());
                carrierVehicle.setCurrentAddress(order.getAddress());
                //add or update the order and vehicle to the plan
                DispatchPlan plan = dispatchPlanMap.computeIfAbsent(carrierVehicle.getVehicleId(),
                        vehicleId -> new DispatchPlan(new DispatchVehicle(vehicleId,0.0,0.0),new ArrayList<>()));
                plan.getAssignedOrders().add(order);
                DispatchVehicle dispatchVehicle = plan.getVehicle();
                dispatchVehicle.setTotalDistance(dispatchVehicle.getTotalDistance()+minimumDistance);
                dispatchVehicle.setTotalLoad(dispatchVehicle.getTotalLoad() + order.getPackageWeight());
            }
        });

        return new ArrayList<>(dispatchPlanMap.values());
    }

}
