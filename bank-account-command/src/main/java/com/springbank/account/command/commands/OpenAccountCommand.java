package com.springbank.account.command.commands;

import com.springbank.account.core.models.AccountType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
public class OpenAccountCommand {
    @TargetAggregateIdentifier
    private String id;
    @NotBlank(message = "Holder ID Cannot be empty")
    private String accountHolderId;
    @NotNull(message = "Account type cannot be empty")
    private AccountType accountType;
    @Min(value = 10, message = "Opening balance cannot be less than ten")
    private BigDecimal openingBalance;

}
