package com.springbank.account.query;

import com.springbank.account.core.config.AxonConfig;
import com.springbank.account.core.security.SecurityConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({AxonConfig.class, SecurityConfig.class})
@EntityScan(basePackages = "com.springbank.account.core.models")
public class BankAccountQueryApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankAccountQueryApplication.class, args);
    }

}
