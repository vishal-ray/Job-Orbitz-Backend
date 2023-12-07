package com.alpha.TaskOrbit.repositories;

import com.alpha.TaskOrbit.modules.State;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepoState extends JpaRepository<State,Long> {
    State findByStateName(String StateName);
}
