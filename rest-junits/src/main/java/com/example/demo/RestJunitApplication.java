package com.example.demo;

import com.example.demo.config.dateformatter.LocalDateDeserializer;
import com.example.demo.config.dateformatter.LocalDateSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import java.time.LocalDate;


@SpringBootApplication
public class RestJunitApplication {
	@Autowired
	private ApplicationContext applicationContext;

	public static void main(String[] args) {
		SpringApplication.run(RestJunitApplication.class, args);
	}

	@Bean
	@Primary
	public ObjectMapper objectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		JavaTimeModule javaTimeModule = new JavaTimeModule();
		javaTimeModule.addSerializer(LocalDate.class, this.applicationContext.getBean(LocalDateSerializer.class)); // can be inject the bean without using application context
		javaTimeModule.addDeserializer(LocalDate.class, this.applicationContext.getBean(LocalDateDeserializer.class));
		objectMapper.registerModule(javaTimeModule);
		return objectMapper;
	}
}
