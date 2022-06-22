package com.digitalBooking.DigitalBooking.core.security.authorizationserver;

import com.digitalBooking.DigitalBooking.domain.model.Usuarios;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
public class AuthUser extends User {

    private static final long serialVersionUID = 1L;

    private String firstName;
    private String lastName;
    private Long userId;
    private String userEmail;

    public AuthUser(Usuarios usuarios, Collection<? extends GrantedAuthority> authorities) {
        super(usuarios.getEmail(), usuarios.getSenha(), authorities);
        this.firstName = usuarios.getNome();
        this.lastName = usuarios.getSobrenome();
        this.userId = usuarios.getId();
        this.userEmail = usuarios.getEmail();
    }

}
