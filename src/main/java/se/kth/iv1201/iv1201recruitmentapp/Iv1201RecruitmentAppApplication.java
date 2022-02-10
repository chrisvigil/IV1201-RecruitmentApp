package se.kth.iv1201.iv1201recruitmentapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Starts the Recruitment application.
 */
@SpringBootApplication
@EnableJpaRepositories
public class Iv1201RecruitmentAppApplication {

    /**
     * Entrypoint for the Recruitment application.
     *
     * @param args Options.
     */
    public static void main(String[] args) {
        SpringApplication.run(Iv1201RecruitmentAppApplication.class, args);
    }

}
