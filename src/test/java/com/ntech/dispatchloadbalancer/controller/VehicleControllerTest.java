package com.ntech.dispatchloadbalancer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ntech.dispatchloadbalancer.model.Vehicle;
import com.ntech.dispatchloadbalancer.model.dto.VehicleRequestList;
import com.ntech.dispatchloadbalancer.service.VehicleService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VehicleController.class)
class VehicleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private VehicleService vehicleService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Valid vehicle list should return 200")
    void addVehicle_whenValidInput_thenReturns200() throws Exception {
        VehicleRequestList vehicleRequest = new VehicleRequestList();
        vehicleRequest.setVehicles(List.of(
                new Vehicle("VEH001", 100.0, 12.9716, 77.6413, "Indiranagar, Bangalore, Karnataka, India")
                ));
        doNothing().when(vehicleService).addVehicles(any(List.class));

        mockMvc.perform(post("/api/dispatch/vehicles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vehicleRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Vehicle details accepted."))
                .andExpect(jsonPath("$.status").value("success"));
    }

    @Test
    @DisplayName("Empty vehicle list should return 200")
    void addVehicle_whenEmptyList_thenReturns400() throws Exception {
        VehicleRequestList vehicleRequest = new VehicleRequestList();
        vehicleRequest.setVehicles(Collections.emptyList());

        mockMvc.perform(post("/api/dispatch/vehicles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vehicleRequest)))
                .andExpect(status().isBadRequest());
    }
}