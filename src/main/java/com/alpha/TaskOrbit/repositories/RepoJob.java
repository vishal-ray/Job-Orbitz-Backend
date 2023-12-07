package com.alpha.TaskOrbit.repositories;

import com.alpha.TaskOrbit.modules.Job;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

public interface RepoJob extends MongoRepository<Job,String> {
}
