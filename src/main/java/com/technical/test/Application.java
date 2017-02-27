package com.technical.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.Month;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * @author Tom Longdon
 */

@SpringBootApplication
public class Application {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args){
        SpringApplication.run(Application.class);
    }

    @Bean
    public CommandLineRunner loadData(final ContactRepository contactRepository,
                                             final ActivityRepository activityRepository){
        return args -> {
            //Add some contacts
            contactRepository.save(new Contact("Tom", "Longdon", "tomlongdon@hotmail.co.uk", "07756379908",
                    "44 Belgravia Court", "Abbey Foregate", "Shrewsbury", "SY2 6BW"));
            contactRepository.save(new Contact("Millie", "Gough", "milliegough@gmail.com", "07751254968",
                    "33 Brick Lane", "Monkmoor", "Shrewsbury", "SY1 4ER"));
            contactRepository.save(new Contact("Dave", "Rogers", "dave@rogers.com", "01239056349", "10 Candle Street",
                    "Lighting", "Conventry", "CY1 3WE"));


            //Needed to build dummy data
            Contact contactExampleTom = contactRepository.findOne(1L);
            Contact contactExampleDave = contactRepository.findOne(3L);

            log.info(contactExampleTom.toString());
            log.info(contactExampleDave.toString());

            //Add some activities
            activityRepository.save(new Activity(1L, "Work Meeting", "Meeting with clients", ActivityType.MEETING,
                    LocalDate.of(2017, Month.MARCH, 29)));
            activityRepository.save(new Activity(3L, "Weekly Telco", "Architecture telco", ActivityType.CALL,
                    LocalDate.of(2017, Month.MAY, 16)));

            for (Contact contact : contactRepository.findAll()){
                log.info(contact.toString());
            }

            for(Activity activity: activityRepository.findActivityByTitle("Weekly Telco")){
                log.info(activity.toString());
            }
        };
    }

}
