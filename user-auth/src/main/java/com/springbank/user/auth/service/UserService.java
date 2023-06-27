package com.springbank.user.auth.service;

import com.springbank.user.auth.repository.UserRepository;
import com.springbank.user.core.usercore.models.Account;
import com.springbank.user.core.usercore.models.User;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Load by username: {}", username);
        User byAccountUsername = userRepository.findByAccountUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Incorrect Username or Password supplied"));

        Account account = byAccountUsername.getAccount();

        return org.springframework.security.core.userdetails.User
                .withUsername(account.getUsername())
                .password(account.getPassword())
                .authorities(account.getRoles())
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }
}
