package com.alpha.TaskOrbit.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alpha.TaskOrbit.dto.LocationDTO;
import com.alpha.TaskOrbit.dto.NotificationDTO;
import com.alpha.TaskOrbit.dto.QualificationDTO;
import com.alpha.TaskOrbit.services.IUserService;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService iUserService;

    @GetMapping("/test")
    public ResponseEntity<?> test() {
        return ResponseEntity.ok(" you have access now  ");
    }

    @PutMapping("/mobile-number")
    public ResponseEntity<?> addOrUpdateMobileNumber(@RequestParam String mobileNo, @RequestParam String emailId) {
        return iUserService.addOrUpdateMobileNumber(mobileNo, emailId);
    }

    @PostMapping("/location")
    public ResponseEntity<?> addLocation(@RequestParam String email, @RequestBody LocationDTO location) {
        return iUserService.addLocation(email, location);
    }

    @PostMapping("/notifications")
    public ResponseEntity<?> addNotification(@RequestParam String email, @RequestBody NotificationDTO notificationDTO) {
        return iUserService.addNotification(email, notificationDTO);
    }

    @PostMapping("/qualifications")
    public ResponseEntity<?> addQualification(@RequestParam String email,
            @RequestBody QualificationDTO qualificationDTO) {
        return iUserService.addQualification(email, qualificationDTO);
    }
}