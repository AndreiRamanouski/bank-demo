package com.springbank.user.command.usercommand.security;

public interface PasswordEncoder {
        String hashPassword(String password);
}
