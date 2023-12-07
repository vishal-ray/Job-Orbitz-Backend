package com.alpha.TaskOrbit.repositories;

import com.alpha.TaskOrbit.modules.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepoCity extends JpaRepository<City,Long> {
    City findByCityName(String cityName);
}
