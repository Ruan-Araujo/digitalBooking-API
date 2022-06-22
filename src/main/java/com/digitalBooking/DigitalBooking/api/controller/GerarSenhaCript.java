package com.digitalBooking.DigitalBooking.api.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GerarSenhaCript {

    public static void main(String[] args) {
        BCryptPasswordEncoder codificador = new BCryptPasswordEncoder();
        System.out.println(codificador.encode("abc123"));
    }
}
