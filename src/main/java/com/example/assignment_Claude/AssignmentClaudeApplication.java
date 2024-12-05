package com.example.assignment_Claude;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class AssignmentClaudeApplication {

	public static void main(String[] args) {
		SpringApplication.run(AssignmentClaudeApplication.class, args);
	}

}
