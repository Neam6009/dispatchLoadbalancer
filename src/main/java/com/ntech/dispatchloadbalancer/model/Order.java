package com.ntech.dispatchloadbalancer.model;

import com.ntech.dispatchloadbalancer.model.annotation.LatitudeValidator;
import com.ntech.dispatchloadbalancer.model.annotation.LongitudeValidator;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Table(name = "orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    private String orderId;
    @LatitudeValidator
    private Double latitude;
    @LongitudeValidator
    private Double longitude;
    @NotBlank(message = "The address cannot be empty.")
    private String address;
    @Min(0)
    private Double packageWeight;
    @NotNull
    private Priority priority;
}
