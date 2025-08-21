package com.ntech.dispatchloadbalancer.controller;

import com.ntech.dispatchloadbalancer.model.DispatchPlan;
import com.ntech.dispatchloadbalancer.model.DispatchVehicle;
import com.ntech.dispatchloadbalancer.service.DispatchService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/dispatch/plan")
public class PlanController {

    private final DispatchService dispatchService;

    public PlanController(DispatchService dispatchService) {
        this.dispatchService = dispatchService;
    }

    @GetMapping
    public List<DispatchPlan> dispatchPlan(){
        return dispatchService.getDispatchPlan();
    }
}
