package com.springbank.user.command.usercommand.controllers;

import com.springbank.user.command.usercommand.command.RemoveUserCommand;
import com.springbank.user.core.usercore.dto.BaseResponse;
import com.springbank.user.command.usercommand.dto.RegisterUserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/delete-user")
@Slf4j
@RequiredArgsConstructor
public class RemoveUserController {

    private final CommandGateway commandGateway;

    @DeleteMapping("{id}")
    public ResponseEntity<BaseResponse> deleteUser(@PathVariable String id) {
        log.info("Delete user with id {}", id);
        String message;
        try {
            commandGateway.sendAndWait(RemoveUserCommand.builder().id(id).build());
            message = String.format("User with id %s successfully deleted.", id);
            return ResponseEntity.ok().body(BaseResponse.builder().message(message).build());
        } catch (Exception exception) {
            message = String.format("Error processing register user with id %s. %s", id,
                    exception.getLocalizedMessage());
            log.error(message);
            return ResponseEntity.internalServerError().body(RegisterUserResponse.builder().message(message).build());
        }
    }

}
