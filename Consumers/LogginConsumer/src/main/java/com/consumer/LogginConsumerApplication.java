package com.consumer;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@EnableRabbit
@SpringBootApplication
public class LogginConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(LogginConsumerApplication.class, args);
	}

}
