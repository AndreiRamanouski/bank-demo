package com.springbank.user.command.usercommand.controllers;

import com.springbank.user.command.usercommand.command.RegisterUserCommand;
import com.springbank.user.command.usercommand.dto.RegisterUserResponse;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("api/v1/register-user")
public class RegisterUserController {

    private final CommandGateway commandGateway;

    //use get mapping temporally as the latest wersion of spring security is tricky to configure.
    @PostMapping
    public ResponseEntity<RegisterUserResponse> registerUser(@RequestBody @Valid RegisterUserCommand registerUserCommand) {
        log.info("Register new user with name: {}", registerUserCommand.getUser().getFirstname());
        String message;
        try {
            registerUserCommand.setId(UUID.randomUUID().toString());
            commandGateway.sendAndWait(registerUserCommand);
            message = String.format("User successfully registered. User id: %s", registerUserCommand.getId());
            return new ResponseEntity<>(
                    RegisterUserResponse.builder().message(message).id(registerUserCommand.getId()).build(),
                    HttpStatus.CREATED);
        } catch (Exception exception) {
            message = String.format("Error processing register user with id %s. %s",
                    registerUserCommand.getUser(), exception.getLocalizedMessage());
            log.error(message);
            return ResponseEntity.internalServerError().body(RegisterUserResponse.builder().message(message).build());
        }
    }

}
