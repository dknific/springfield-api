package com.simpsonfans.simpsons_list;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class, UserDetailsServiceAutoConfiguration.class})
public class SimpsonsCharacterListApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimpsonsCharacterListApplication.class, args);
	}

}
