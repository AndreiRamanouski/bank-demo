package com.springbank.user.command.usercommand.command;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
public class RemoveUserCommand {

    @TargetAggregateIdentifier
    private String id;
}
