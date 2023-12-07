package com.alpha.TaskOrbit.controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.alpha.TaskOrbit.modules.Job;
import com.alpha.TaskOrbit.repositories.RepoJob;

@CrossOrigin(origins = "http://localhost:3000/")
@RestController
public class JobController {
    @Autowired
    private RepoJob repoJob;

    @PostMapping("/jobs")
    public String addJob(@RequestBody Job job) {
        Job save = this.repoJob.save(job);
        return "Successful";
    }

    @GetMapping("/jobs")
    public Collection<Job> getAllJobs() {
        return this.repoJob.findAll();
    }
}
