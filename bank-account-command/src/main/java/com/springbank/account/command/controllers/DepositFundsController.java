package com.springbank.account.command.controllers;

import com.springbank.account.command.commands.DepositFoundCommand;
import com.springbank.account.core.dto.BaseResponse;
import com.springbank.account.core.dto.OpenAccountResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/open-bank-account")
@Slf4j
public class DepositFundsController {

    private final CommandGateway commandGateway;

    @PostMapping("{id}")
    public ResponseEntity<BaseResponse> depositAccount(@PathVariable String id,
            @RequestBody @Valid DepositFoundCommand depositFoundCommand) {
        log.info("Deposit account {}", id);
        String message;
        depositFoundCommand.setId(id);
        try {
            commandGateway.sendAndWait(depositFoundCommand);
            message = String.format("Account topped up. Account id %s", id);
            return ResponseEntity.ok().body(OpenAccountResponse.builder().message(message).build());

        } catch (Exception exception) {
            message = String.format("Exception during account replenishment: %s and id %s",
                    exception.getLocalizedMessage(),
                    id);
            log.error(message);
            return ResponseEntity.internalServerError().body(new BaseResponse(message));
        }

    }

}
