package com.alpha.TaskOrbit.repositories;

import com.alpha.TaskOrbit.modules.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RepoUser extends JpaRepository<User,Long> {


    public Optional<User> findByEmailID(String emailID);


}
