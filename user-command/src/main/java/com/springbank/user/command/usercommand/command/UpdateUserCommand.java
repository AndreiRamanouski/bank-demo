package com.springbank.user.command.usercommand.command;

import com.springbank.user.core.usercore.models.User;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
public class UpdateUserCommand {

    @TargetAggregateIdentifier
    private String id;
    @Valid
    @NotNull(message = "User cannot be null")
    private User user;
}
