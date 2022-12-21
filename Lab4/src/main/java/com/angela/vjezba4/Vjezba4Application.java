package com.angela.vjezba4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan({ "com.angela.*" })


public class Vjezba4Application {

	public static void main(String[] args) {

		SpringApplication.run(Vjezba4Application.class, args);
	}
}




