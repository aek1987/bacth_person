package com.example.demo;

import org.springframework.batch.item.ItemProcessor;

public class PersonItemProcessor implements ItemProcessor<Person, Person> {

    @Override
    public Person process(Person person) throws Exception {
    	
    	System.out.println("Processed person: " + person.getFirstName() + " " + person.getLastName() + ", Age: " + person.getAge());
        person.setFirstName(person.getFirstName().toUpperCase());
        person.setLastName(person.getLastName().toUpperCase());
        if (person.getAge() == null) {
           // System.out.println("Skipping person due to invalid age: " + person.getFirstName() + " " + person.getLastName());
            return null;  // Skip this person
        }
        
       // System.out.println("Processed person: " + person.getFirstName() + " " + person.getLastName() + ", Age: " + person.getAge());
        return person;
    }
}
