package com.alpha.TaskOrbit.modules;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "jobs")
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String title;
    private String institute;
    private String location;
    private String department;
    private Long payscale;
    private Date lastDateToApply;
    private String applyLink;
    private Long vacancyNumber;
    private String minCriteria;
    private String vacancyDetails;
    private String selectionProcessSteps;
    private String exam_dates;
    private String syllabus;
    private String advertisementLink;
    private String applicationFees;
    private Long job_sector_id;
    private Long job_department_id;
    private Long state_id;
    private List<Long> qualificationIds;

    @Column(name = "status")
    private Boolean status = true;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}