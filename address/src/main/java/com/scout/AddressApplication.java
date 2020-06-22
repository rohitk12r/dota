package com.scout;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * To start the springboot application
 * 
 * @author Kushagra Mathur
 *
 */
@EnableJpaRepositories
@SpringBootApplication
public class AddressApplication {

    /**
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(AddressApplication.class, args);
    }
}
