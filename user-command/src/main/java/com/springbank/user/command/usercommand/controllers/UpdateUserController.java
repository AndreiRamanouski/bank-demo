package com.springbank.user.command.usercommand.controllers;

import com.springbank.user.command.usercommand.command.UpdateUserCommand;
import com.springbank.user.core.usercore.dto.BaseResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/update-user")
@Slf4j
@RequiredArgsConstructor
public class UpdateUserController {

    private final CommandGateway commandGateway;

    @PutMapping("{id}")
    public ResponseEntity<BaseResponse> updateUser(@RequestBody @Valid UpdateUserCommand updateUserCommand,
            @PathVariable String id) {
        log.info("Update the user with id: {}", id);
        String message;
        try {
            updateUserCommand.setId(id);
            updateUserCommand.getUser().setId(id);
            commandGateway.sendAndWait(updateUserCommand);
            message = String.format("User successfully updated. User id: %s", updateUserCommand.getId());
            return ResponseEntity.ok().body(BaseResponse.builder().message(message).build());
        } catch (Exception exception) {
            message = String.format("Error processing register user with id %s. %s",
                    updateUserCommand.getUser(), exception.getLocalizedMessage());
            log.error(message);
            return ResponseEntity.internalServerError().body(BaseResponse.builder().message(message).build());
        }
    }

}
