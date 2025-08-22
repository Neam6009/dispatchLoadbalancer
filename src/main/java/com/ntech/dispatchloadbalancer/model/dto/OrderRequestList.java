package com.ntech.dispatchloadbalancer.model.dto;

import com.ntech.dispatchloadbalancer.model.Order;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class OrderRequestList {
    @NotEmpty
    private List<@Valid Order> orders;
}
