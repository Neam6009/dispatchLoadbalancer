package com.ntech.dispatchloadbalancer.service;

import com.ntech.dispatchloadbalancer.model.Vehicle;
import com.ntech.dispatchloadbalancer.repo.VehicleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VehicleServiceTest {

    @Mock
    private VehicleRepository vehicleRepository;

    @InjectMocks
    private VehicleService vehicleService;

    private List<Vehicle> sampleVehicles;

    @BeforeEach
    void setup() {
        sampleVehicles = List.of(
                new Vehicle("VEH001", 100.0, 12.9716, 77.6413, "Indiranagar"),
                new Vehicle("VEH002", 150.0, 13.0674, 80.2376, "T Nagar")
        );
    }

    @Test
    @DisplayName("Get vehicles should return proper list of vehicles")
    void testGetAllVehicles_shouldReturnListOfVehicles() {
        when(vehicleRepository.findAll()).thenReturn(sampleVehicles);
        List<Vehicle> result = vehicleService.getAllVehicles();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("VEH001", result.get(0).getVehicleId());
    }

    @Test
    @DisplayName("Get vehicles should return empty list when there are no vehicles")
    void testGetAllVehicles_whenNoVehicles_shouldReturnEmptyList() {
        when(vehicleRepository.findAll()).thenReturn(Collections.emptyList());
        List<Vehicle> result = vehicleService.getAllVehicles();

        assertNotNull(result);
        assertEquals(0, result.size());
    }
}