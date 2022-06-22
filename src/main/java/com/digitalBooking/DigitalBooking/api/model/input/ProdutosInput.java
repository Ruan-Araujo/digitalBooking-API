package com.digitalBooking.DigitalBooking.api.model.input;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdutosInput {

    private String nome;
    private CategoriasIdInput categoria;
    private CidadesIdInput cidade;

}
