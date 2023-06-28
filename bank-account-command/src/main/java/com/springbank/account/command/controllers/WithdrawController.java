package com.springbank.account.command.controllers;

import com.springbank.account.command.commands.WithdrawFundsCommand;
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
@RequestMapping("api/v1/open-bank-account/withdrawal")
@Slf4j
public class WithdrawController {


    private final CommandGateway commandGateway;

    @PostMapping("{id}")
    public ResponseEntity<BaseResponse> withdraw(@PathVariable String id,
            @RequestBody @Valid WithdrawFundsCommand withdrawFundsCommand) {
        log.info("Withdraw money from account {}", id);
        String message;
        withdrawFundsCommand.setId(id);
        try {
            commandGateway.send(withdrawFundsCommand).get();
            message = String.format("Withdrawn amount: %s. Account id: %s", withdrawFundsCommand.getAmount(), id);
            return ResponseEntity.ok().body(OpenAccountResponse.builder().message(message).build());

        } catch (Exception exception) {
            message = String.format("Exception during withdrawal: %s and id: %s", exception.getLocalizedMessage(),
                    id);
            log.error(message);
            return ResponseEntity.internalServerError().body(new BaseResponse(message));
        }

    }
}
