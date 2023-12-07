package com.alpha.TaskOrbit.services;

import com.alpha.TaskOrbit.dto.LocationDTO;
import com.alpha.TaskOrbit.dto.NotificationDTO;
import com.alpha.TaskOrbit.dto.QualificationDTO;
import com.alpha.TaskOrbit.modules.*;
import com.alpha.TaskOrbit.repositories.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService implements IUserService{
    @Autowired
    private RepoUser repoUser;
    @Autowired
    private RepoCity repoCity;
    @Autowired
    private RepoState repoState;
    @Autowired
    private RepoCountry repoCountry;
    @Autowired
    private RepoUserLocation repoUserLocation;
    @Autowired
    private RepoNotificationPreference repoNotificationPreference;
    @Autowired
    private RepoUserQualification repoUserQualification;
    public ResponseEntity<?> addOrUpdateMobileNumber(String mobileNo, String emailId)
    {
        Optional<User> user = repoUser.findByEmailID(emailId);
        if(user.isEmpty())
            return new ResponseEntity<>("User Not Present", HttpStatus.BAD_REQUEST);
        user.get().setMobileNo(mobileNo);
        return new ResponseEntity<>("Mobile Number Updated Successfully", HttpStatus.ACCEPTED);
    }

    public ResponseEntity<?> addLocation(String email, LocationDTO location)
    {
        Optional<User> user = repoUser.findByEmailID(email);
        if(user.isEmpty())
            return new ResponseEntity<>("Location Not Added", HttpStatus.BAD_REQUEST);

        Country country = new Country();
        Country existingCountry = repoCountry.findByCountryName(location.getCountryName().toUpperCase());

        if(existingCountry != null)
        {
            country = existingCountry;
        }
        else {
            country.setCountryName(location.getCountryName().toUpperCase());
            repoCountry.saveAndFlush(country);
        }

        State state = new State();
        State existingState = repoState.findByStateName(location.getStateName().toUpperCase());

        if(existingState != null)
        {
            state = existingState;
        }
        else {
            state.setStateName(location.getStateName());
            state.setCountry(repoCountry.findByCountryName(location.getCountryName().toUpperCase()));
            repoState.saveAndFlush(state);
        }

        City city = new City();
        city.setCityName(location.getCityName());
        city.setState(repoState.findByStateName(location.getStateName().toUpperCase()));
        repoCity.saveAndFlush(city);

        UserLocation userLocation = new UserLocation();
        userLocation.setUser(user.get());
        userLocation.setHouseNumber(location.getHouseNumber());
        userLocation.setLocality(location.getLocality());
        userLocation.setPincode(location.getPincode());
        userLocation.setCity(repoCity.findByCityName(location.getCityName().toUpperCase()));

        return new ResponseEntity<>("Location Added", HttpStatus.CREATED);
    }

    public ResponseEntity<?> addNotification(String email, NotificationDTO notificationDTO)
    {
        Optional<User> user= repoUser.findByEmailID(email);
        if(user.isEmpty())
            return new ResponseEntity<>("User not present",HttpStatus.BAD_REQUEST);

        NotificationPreference notificationPreference = new NotificationPreference();
        notificationPreference.setUser(user.get());
        notificationPreference.setEntityType(notificationDTO.getEntityType().toUpperCase());
        notificationPreference.setEntityId(notificationDTO.getEntityId());

        repoNotificationPreference.save(notificationPreference);
        return new ResponseEntity<>("Notification Preferences Added",HttpStatus.CREATED);
    }

    public ResponseEntity<?> addQualification(String email, QualificationDTO qualificationDTO)
    {
        Optional<User> user= repoUser.findByEmailID(email);
        if(user.isEmpty())
            return new ResponseEntity<>("User not present",HttpStatus.BAD_REQUEST);

        UserQualification qualification = new UserQualification();
        qualification.setUser(user.get());
        qualification.setQualificationId(qualificationDTO.getQualificationId());
        qualification.setSessionFrom(qualificationDTO.getSessionFrom());
        qualification.setSessionTo(qualificationDTO.getSessionTo());

        repoUserQualification.save(qualification);
        return new ResponseEntity<>("Qualification Added Successfully",HttpStatus.CREATED);
    }
}