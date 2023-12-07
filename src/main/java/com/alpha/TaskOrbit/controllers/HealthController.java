package com.alpha.TaskOrbit.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @GetMapping("/health/ping")
    public String ping() {
        return "Pong";
    }

}
