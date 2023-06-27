package com.springbanking.user.query.userquery;

import com.springbank.user.core.usercore.configuration.AxonConfig;
import com.springbank.user.core.usercore.security.SecurityConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;


@Import({AxonConfig.class, SecurityConfig.class})
@SpringBootApplication
public class UserQueryApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserQueryApplication.class, args);
	}

}
