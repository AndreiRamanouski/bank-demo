package com.springbank.account.command;

import com.springbank.account.core.config.AxonConfig;
import com.springbank.account.core.security.SecurityConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Import;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@Import({AxonConfig.class, SecurityConfig.class})
public class BankAccountCommandApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankAccountCommandApplication.class, args);
    }

}
