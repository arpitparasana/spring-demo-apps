package com.example.async.demo;

import java.util.concurrent.Executor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * EnableAsync turns on spring's ability to run tasks on threads in background using threadpool
 * One of the way it does this is by looking for a bean of type Executor using "taskExecutor" method 
 */

@SpringBootApplication
@EnableAsync
public class DemoAsyncApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoAsyncApplication.class, args).close();
	}

	@Bean
	public Executor taskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(2);
		executor.setMaxPoolSize(2);
		executor.setQueueCapacity(500);
		executor.setThreadNamePrefix("GithubLookup-");
		executor.initialize();
		
		return executor;

	}

}
