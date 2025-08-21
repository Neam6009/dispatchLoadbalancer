package com.ntech.dispatchloadbalancer.model.dto;

import com.ntech.dispatchloadbalancer.model.Status;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostResponse {
    private String message;
    private Status status;
}
