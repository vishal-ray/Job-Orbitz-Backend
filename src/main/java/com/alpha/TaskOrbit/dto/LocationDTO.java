package com.alpha.TaskOrbit.dto;

import com.alpha.TaskOrbit.modules.City;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LocationDTO {
    private String houseNumber;
    private String locality;
    private String pincode;
    private String cityName;
    private String stateName;
    private String countryName;
}
