package com.ntech.dispatchloadbalancer.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ntech.dispatchloadbalancer.model.annotation.LatitudeValidator;
import com.ntech.dispatchloadbalancer.model.annotation.LongitudeValidator;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vehicle {
    @Id
    private String vehicleId;
    @Min(0)
    private Double capacity;
    @LatitudeValidator
    private Double currentLatitude;
    @LongitudeValidator
    private Double currentLongitude;
    @NotNull
    @NotEmpty
    private String currentAddress;
}
