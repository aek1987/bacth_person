package com.example.demo;



import java.util.Date;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BatchController {
	
	@Autowired
	private final JobLauncher jobLauncher;
    private final Job job;

    @Autowired
    public BatchController(JobLauncher jobLauncher, Job job) {
        this.jobLauncher = jobLauncher;
        this.job = job;
    }

	    @GetMapping("/start")
	    @ResponseBody
	    public String handle() {
	        try {
	            JobParameters jobParameters = new JobParametersBuilder()
	                    .addDate("launchDate", new Date())
	                    .addLong("time", System.currentTimeMillis())
	                    .toJobParameters();
	            jobLauncher.run(job, jobParameters);
	            return "Job started successfully!";
	        } catch (JobExecutionAlreadyRunningException | JobInstanceAlreadyCompleteException | JobParametersInvalidException | org.springframework.batch.core.repository.JobRestartException e) {
	            return "Job failed to start: " + e.getMessage();
	        }
	    }
}

