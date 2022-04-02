package com.msgrJava;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class MsgrJavaApplication {

	public static void main(String[] args) {
		System.out.println("*****************************************" +
				org.hibernate.Version.getVersionString() +
				"*****************************************");
		SpringApplication.run(MsgrJavaApplication.class, args);
	}

}
