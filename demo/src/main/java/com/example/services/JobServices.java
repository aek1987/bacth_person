package com.example.services;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobServices {

    private final JobLauncher jobLauncher;
    private final Job importUserJob;

    @Autowired
    public JobServices(JobLauncher jobLauncher, Job importUserJob) {
    	System.out.println("Job Status :1 "  );
        this.jobLauncher = jobLauncher;
        this.importUserJob = importUserJob;
    }

    public void runJob() throws Exception {
    	System.out.println("Job Status :2 "  );
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("startAt", System.currentTimeMillis())
                .toJobParameters();
        JobExecution jobExecution = jobLauncher.run(importUserJob, jobParameters);
        System.out.println("Job Status : jobExecution" + jobExecution.getStatus());
    }
}