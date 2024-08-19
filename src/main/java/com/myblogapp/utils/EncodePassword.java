package com.myblogapp.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Clock;

public class EncodePassword {
    public static void main(String[] args) {
        PasswordEncoder encodePassword = new BCryptPasswordEncoder();
        System.out.println(encodePassword.encode("admin"));
    }
}
