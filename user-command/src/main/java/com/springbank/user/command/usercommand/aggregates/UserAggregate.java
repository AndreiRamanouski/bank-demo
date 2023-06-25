package com.springbank.user.command.usercommand.aggregates;

import com.springbank.user.command.usercommand.command.RegisterUserCommand;
import com.springbank.user.command.usercommand.command.RemoveUserCommand;
import com.springbank.user.command.usercommand.command.UpdateUserCommand;
import com.springbank.user.command.usercommand.security.PasswordEncoder;
import com.springbank.user.command.usercommand.security.PasswordEncoderImpl;
import com.springbank.user.core.usercore.events.UserRegisteredEvent;
import com.springbank.user.core.usercore.events.UserRemovedEvent;
import com.springbank.user.core.usercore.events.UserUpdateEvent;
import com.springbank.user.core.usercore.models.User;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
@Slf4j
public class UserAggregate {

    @AggregateIdentifier
    private String id;
    private User user;

    private final PasswordEncoder passwordEncoder = new PasswordEncoderImpl();

    public UserAggregate() {
    }

    @CommandHandler
    public UserAggregate(RegisterUserCommand registerUserCommand) {
        User createUser = registerUserCommand.getUser();
        createUser.setId(registerUserCommand.getId());
        String password = passwordEncoder.hashPassword(createUser.getAccount().getPassword());
        createUser.getAccount().setPassword(password);
        log.info("Register user {}", createUser);

        AggregateLifecycle.apply(UserRegisteredEvent.builder()
                .id(registerUserCommand.getId())
                .user(createUser)
                .build());
    }

    @CommandHandler
    public void handle(UpdateUserCommand updateUserCommand) {

        User updateUser = updateUserCommand.getUser();
        updateUser.setId(updateUserCommand.getId());
        String password = passwordEncoder.hashPassword(updateUser.getAccount().getPassword());
        updateUser.getAccount().setPassword(password);
        log.info("Update user {}", updateUser);

        AggregateLifecycle.apply(UserUpdateEvent.builder()
                .id(UUID.randomUUID().toString())
                .user(updateUser)
                .build());
    }

    @CommandHandler
    public void handle(RemoveUserCommand removeUserCommand) {
        log.info("Remove user with id {}", removeUserCommand.getId());
        AggregateLifecycle.apply(new UserRemovedEvent(removeUserCommand.getId()));
    }


    @EventSourcingHandler
    public void on(UserRegisteredEvent userRegisteredEvent) {
        log.info("Set aggregate fields for user {}", userRegisteredEvent);
        this.id = userRegisteredEvent.getId();
        this.user = userRegisteredEvent.getUser();
    }

    @EventSourcingHandler
    public void on(UserUpdateEvent userUpdateEvent) {
        log.info("Update aggregate fields for user id {}", userUpdateEvent.getId());
        this.user = userUpdateEvent.getUser();
    }

    @EventSourcingHandler
    public void on(UserRemovedEvent userRemovedEvent) {
        log.info("Delete aggregate for user id {}", userRemovedEvent.getId());
        AggregateLifecycle.markDeleted();
    }


}
