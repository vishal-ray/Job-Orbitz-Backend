package com.alpha.TaskOrbit.services;

import com.alpha.TaskOrbit.dto.LocationDTO;
import com.alpha.TaskOrbit.dto.NotificationDTO;
import com.alpha.TaskOrbit.dto.QualificationDTO;
import org.springframework.http.ResponseEntity;

public interface IUserService {
    public ResponseEntity<?> addOrUpdateMobileNumber(String mobileNo, String emailId);
    public ResponseEntity<?> addLocation(String email, LocationDTO location);
    public ResponseEntity<?> addNotification(String email, NotificationDTO notificationDTO);
    public ResponseEntity<?> addQualification(String email, QualificationDTO qualificationDTO);
}
