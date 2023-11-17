package com.payroll.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.payroll.app.model.Employee;
import com.payroll.app.repository.EmployeeRepository;

@Configuration
public class LoadDatabase {

	private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);
	
	/**
	 * Spring boot will run all CommandLineRunner beans once the application context is loaded
	 * @param repository
	 * @return @CommandLineRunner
	 */
	@Bean
	CommandLineRunner initDatabase(EmployeeRepository repository) {
		return args -> {
			log.info("Preloading " + repository.save(new Employee("Bilbo Baggins","burglar")));
			log.info("Preloading " + repository.save(new Employee("Gendalf the Grey","guide")));
		};
	}
}
