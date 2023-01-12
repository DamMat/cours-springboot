package com.example.demo;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
// ici on indique ou scanner les entites a mapper en db
// @EntityScan("com.example.demo.*")
public class CoursSpringbootApplication {

    @Bean
    ModelMapper modelMapper() {
        return new ModelMapper();
    }

	public static void main(String[] args) {
		SpringApplication.run(CoursSpringbootApplication.class, args);
	}

}
