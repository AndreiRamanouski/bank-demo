package com.springbanking.user.query.userquery.handlers;

import com.springbank.user.core.usercore.events.UserRegisteredEvent;
import com.springbank.user.core.usercore.events.UserRemovedEvent;
import com.springbank.user.core.usercore.events.UserUpdateEvent;
import com.springbanking.user.query.userquery.rerpositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@ProcessingGroup("user-group")
@RequiredArgsConstructor
public class UserEventHandlerImpl implements UserEventHandler {

    private final UserRepository userRepository;

    @Override
    @EventHandler
    public void on(UserRegisteredEvent userRegisteredEvent) {
        log.info("Register user {}", userRegisteredEvent.getUser());
        userRepository.save(userRegisteredEvent.getUser());
    }

    @Override
    @EventHandler
    public void on(UserUpdateEvent userUpdateEvent) {
        log.info("Update user {}", userUpdateEvent.getUser());
        userRepository.save(userUpdateEvent.getUser());
    }

    @Override
    @EventHandler
    public void on(UserRemovedEvent userRemovedEvent) {
        log.info("Remove user {}", userRemovedEvent.getId());
        userRepository.deleteById(userRemovedEvent.getId());
    }
}
