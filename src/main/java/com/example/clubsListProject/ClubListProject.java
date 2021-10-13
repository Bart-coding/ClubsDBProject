package com.example.clubsListProject;

import com.example.clubsListProject.ClubPackage.Club;
import com.example.clubsListProject.ClubPackage.ClubsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ClubListProject {

    private static final Logger log =
            LoggerFactory.getLogger(ClubListProject.class);

    public static void main (String[] args) {
        SpringApplication.run(ClubListProject.class, args);
    }

    @Bean
    public CommandLineRunner demo(ClubsRepository repository) {
        return (args) -> {
            //save a few clubs
            log.info(repository.save(new Club(95,"Chelsea", "England")).toString());
            repository.save(new Club("Chelsea", "England2"));
            repository.save(new Club("Chelsea3", "England3"));

            //fetch all clubs
            log.info("Clubs found: ");
            log.info("------------");
            for (Club club : repository.findAll()) {
                log.info(club.toString());
            }
            log.info(" ");

            //fetch a club by ID
            log.info("Club found: ");
            log.info("------------");
            Club club = repository.findById(95).get();
            if (club!=null)
                log.info(club.toString());
            log.info(" ");

            //fetch a club by name
            log.info("Club found: ");
            log.info("------------");
            repository.findByName("Chelsea").forEach(chelsea -> {
                log.info(chelsea.toString());
            });
            log.info(" ");

        };
    }
}
