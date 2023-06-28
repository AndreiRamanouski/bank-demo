package com.springbank.account.command.controllers;

import com.springbank.account.command.commands.CloseAccountCommand;
import com.springbank.account.core.dto.BaseResponse;
import com.springbank.account.core.dto.OpenAccountResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/open-bank-account")
@Slf4j
public class CloseAccountController {


    private final CommandGateway commandGateway;

    @DeleteMapping("{id}")
    public ResponseEntity<BaseResponse> closeAccount(@PathVariable String id) {
        log.info("Close account {}", id);
        String message;
        try {
            commandGateway.sendAndWait(CloseAccountCommand.builder().id(id).build());
            message = String.format("Account %s closed.",id);
            return ResponseEntity.ok().body(OpenAccountResponse.builder().message(message).build());
        } catch (Exception exception) {
            message = String.format("Exception during account closure: %s and id %s", exception.getLocalizedMessage(),
                    id);
            log.error(message);
            return ResponseEntity.internalServerError().body(new BaseResponse(message));
        }

    }
}
