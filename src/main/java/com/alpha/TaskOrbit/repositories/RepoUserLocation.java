package com.alpha.TaskOrbit.repositories;

import com.alpha.TaskOrbit.modules.UserLocation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepoUserLocation extends JpaRepository<UserLocation, Long> {
}
