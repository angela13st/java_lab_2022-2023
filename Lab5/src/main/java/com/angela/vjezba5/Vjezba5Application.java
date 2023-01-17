package com.angela.vjezba5;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({ "com.angela.*" })
public class Vjezba5Application {
	

	public static void main(String[] args) {
		SpringApplication.run(Vjezba5Application.class, args);
	}

}
