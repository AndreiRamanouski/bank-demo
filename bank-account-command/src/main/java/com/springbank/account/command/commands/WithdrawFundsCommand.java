package com.springbank.account.command.commands;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
public class WithdrawFundsCommand {

    @TargetAggregateIdentifier
    private String id;
    private BigDecimal amount;
}
