package com.ntech.dispatchloadbalancer.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class Vehicle {
    @Id
    private String vehicleId;
    private int capacity;
    private double currentLatitude;
    private double currentLongitude;
    private String currentAddress;
}
