package com.digitalBooking.DigitalBooking.core.security;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.security.access.prepost.PreAuthorize;

public @interface CheckSecurity {

    @PreAuthorize("hasAuthority('ADMIN') and hasAuthority('SCOPE_WRITE')")
    @Retention(RUNTIME)
    @Target(METHOD)
    public @interface ApenasAdmin { }

    @PreAuthorize("isAuthenticated() and hasAuthority('SCOPE_READ')")
    @Retention(RUNTIME)
    @Target(METHOD)
    public @interface ApenasCliente { }

    @PreAuthorize("permitAll()")
    @Retention(RUNTIME)
    @Target(METHOD)
    public @interface Publico { }

    public @interface Reservas {
        @PreAuthorize("isAuthenticated()")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface PodeReservar { }
    }
}
