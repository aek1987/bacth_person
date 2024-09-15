package com.example.demo;

import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;

import jakarta.persistence.EntityManagerFactory;



@Configuration
@EnableBatchProcessing
public class BatchConfig {
	private final JobRepository jobRepository;
	private final PlatformTransactionManager transactionManager;	
	//private final CoffeeRepository coffeeRepository;
	private final PersonRepository personRepository;
     

    
    @Value("${file.input}")
    private String fileInput;

	public BatchConfig(JobRepository jobRepository, PlatformTransactionManager transactionManager, 
			PersonRepository personRepository) {
this.jobRepository = jobRepository;
this.transactionManager = transactionManager;
this.personRepository = personRepository;

}
	 /* 
	@Bean
    public FlatFileItemReader<Coffee> reader() {
		FlatFileItemReader<Coffee> reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource(fileInput));
        reader.setLinesToSkip(1);  // Skip header line

        DefaultLineMapper<Coffee> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setNames("coffeeId", "brand", "origin", "characteristics");

        BeanWrapperFieldSetMapper<Coffee> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Coffee.class);

        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        reader.setLineMapper(lineMapper);
        return reader;
    
    }
	
	  @Bean
	  public CoffeeItemProcessor processor() {
		  System.out.println("Processing processor: ");
			System.out.println("Processing processor: ");
			System.out.println("Processing processor: ");
	      return new CoffeeItemProcessor();
	  }
  
	  
	  @Bean
	    public RepositoryItemWriter<Coffee> writer(CoffeeRepository Repository) {
		  System.out.println("Processing writer: ");
			System.out.println("Processing writer: ");
			System.out.println("Processing writer: ");
	        RepositoryItemWriter<Coffee> writer = new RepositoryItemWriter<>();
	        writer.setRepository(Repository);
	        writer.setMethodName("save");
	        return writer;
	    }*/
	 
	  @Bean
	  public Job importUserJob(JobRepository jobRepository, JobCompletionNotificationListener listener, Step step1) {
	      return new JobBuilder("importUserJob", jobRepository)
	        .incrementer(new RunIdIncrementer())
	        .listener(listener)
	        .flow(step1)
	        .end()
	        .build();
	  }

	  @Bean
	  public Step step1(JobRepository jobRepository, PlatformTransactionManager transactionManager,PersonRepository Repository) {
	      return new StepBuilder("step1", jobRepository)
	        .<Person, Person> chunk(10, transactionManager)
	        .reader(reader())
	        .processor(processor())
	        .writer(writer(Repository))
	        .build();
	  }

	/*
	  @Bean
	    public ItemReader<Person> reader() {
	        List<Person> people = Arrays.asList(
	                new Person("John", "Doe"),
	                new Person("Jane", "Smith"),
	                new Person("Peter", "Parker")
	        );
	        return new ListItemReader<>(people);
	    }*/
	   @Bean
	    public FlatFileItemReader<Person> reader() {
			FlatFileItemReader<Person> reader = new FlatFileItemReader<>();
	        reader.setResource(new ClassPathResource(fileInput));
	        reader.setLinesToSkip(1);  // Skip header line

	        DefaultLineMapper<Person> lineMapper = new DefaultLineMapper<>();
	        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
	        tokenizer.setNames("Id", "firstName", "lastName", "age");

	        BeanWrapperFieldSetMapper<Person> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
	        fieldSetMapper.setTargetType(Person.class);

	        lineMapper.setLineTokenizer(tokenizer);
	        lineMapper.setFieldSetMapper(fieldSetMapper);	
	       
	      
	        reader.setLineMapper(lineMapper);
	        
	        return reader;
	    
	    }
	   @Bean
	    public PersonItemProcessor processor() {
	        return new PersonItemProcessor();
	    }

	   
	 /*  
	   @Bean
	    public ItemProcessor<Person, Person> processor() {
	        return person -> {
	            person.setFirstName(person.getFirstName().toUpperCase());
	            person.setLastName(person.getLastName().toUpperCase());
	            person.setAge(person.getAge());
	            System.out.println("person age: "+person.getAge());
	          
	            return person;
	        };
	    }*/

	    @Bean
	    public RepositoryItemWriter<Person> writer(PersonRepository Repository) {
	    	   System.out.println("Processing writer: ");				
		        RepositoryItemWriter<Person> writer = new RepositoryItemWriter<>();
		        writer.setRepository(Repository);
		        writer.setMethodName("save");
		        return writer;
	    }

  
	  
	  
	  

	
}
