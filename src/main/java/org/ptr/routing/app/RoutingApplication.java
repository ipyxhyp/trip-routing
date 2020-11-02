package org.ptr.routing.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
@ComponentScan(basePackages = "org.ptr.routing")
public class RoutingApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoutingApplication.class);

    public static void main(String[] args) {
        try {
            SpringApplication.run(RoutingApplication.class, args);
        } catch (Exception e){
            LOGGER.error("CountryTripsApplication error", e);
            System.exit(1);
        }
    }


}
