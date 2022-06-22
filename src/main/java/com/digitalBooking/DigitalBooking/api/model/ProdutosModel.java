package com.digitalBooking.DigitalBooking.api.model;

import com.digitalBooking.DigitalBooking.domain.model.Imagens;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProdutosModel {

    private Long id;
    private String nome;
    private String descricao;
    private CategoriasModel categoria;
    private CidadesModel cidade;
    private List<Imagens> imagens;

}
