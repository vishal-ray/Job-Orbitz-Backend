package com.alpha.TaskOrbit.repositories;

import com.alpha.TaskOrbit.modules.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepoCountry extends JpaRepository<Country,Long> {
    Country findByCountryName(String countryName);
}
