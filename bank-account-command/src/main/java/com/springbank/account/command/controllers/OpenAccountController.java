package com.springbank.account.command.controllers;

import com.springbank.account.command.commands.OpenAccountCommand;
import com.springbank.account.core.dto.BaseResponse;
import com.springbank.account.core.dto.OpenAccountResponse;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/open-bank-account")
@Slf4j
public class OpenAccountController {

    private final CommandGateway commandGateway;

    @PostMapping
    public ResponseEntity<BaseResponse> openAccount(@RequestBody @Valid OpenAccountCommand openAccountCommand) {
        log.info("Open account");
        String message;
        String accountId = UUID.randomUUID().toString();
        openAccountCommand.setId(accountId);
        try {
            commandGateway.sendAndWait(openAccountCommand);
            message = String.format("Account opened. Account id %s", accountId);
            return new ResponseEntity<>(OpenAccountResponse.builder().id(accountId).message(message).build(),
                    HttpStatus.CREATED);

        } catch (Exception exception) {
            message = String.format("Exception during account creation: %s and id %s", exception.getLocalizedMessage(),
                    accountId);
            log.error(message);
            return ResponseEntity.internalServerError().body(new BaseResponse(message));
        }

    }

}
