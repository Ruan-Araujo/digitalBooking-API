package com.digitalBooking.DigitalBooking.api.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ReservasModel {

    private Long id;
    private Date dataIda;
    private Date dataVolta;
    private UsuariosModel cliente;
    private ProdutosModel produto;

}
