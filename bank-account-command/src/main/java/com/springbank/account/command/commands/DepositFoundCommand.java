package com.springbank.account.command.commands;

import jakarta.validation.constraints.Min;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
public class DepositFoundCommand {

    @TargetAggregateIdentifier
    private String id;
    @Min(value = 1, message = "Cannot be less than one")
    private BigDecimal amount;
}
