package com.digitalBooking.DigitalBooking.api.assembler;

import com.digitalBooking.DigitalBooking.api.model.input.CidadesInput;
import com.digitalBooking.DigitalBooking.api.model.input.ProdutosInput;
import com.digitalBooking.DigitalBooking.domain.model.Cidades;
import com.digitalBooking.DigitalBooking.domain.model.Produtos;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProdutosDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Produtos toDomainObject(ProdutosInput produtosInput) {
        return modelMapper.map(produtosInput, Produtos.class);
    }
}
