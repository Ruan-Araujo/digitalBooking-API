package com.digitalBooking.DigitalBooking.api.model.input;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuariosInput {

    private String nome;
    private String sobrenome;
    private String email;
    private String senha;
}
