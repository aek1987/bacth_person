package com.example.Processor;




import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import com.example.demo.Person;

public class PersonItemProcessor implements ItemProcessor<Person, Person> {
	private static final Logger log = LoggerFactory.getLogger(PersonItemProcessor.class);
    @Override
    public Person process(Person person) throws Exception {
        String firstName = person.getFirstName().toUpperCase();
        String lastName = person.getLastName().toUpperCase();

        Person transformedPerson = new Person();
        transformedPerson.setFirstName(firstName);
        transformedPerson.setLastName(lastName);
        log.info("Converting ( {} ) into ( {} )", person, transformedPerson);
        return transformedPerson;
    }
}
