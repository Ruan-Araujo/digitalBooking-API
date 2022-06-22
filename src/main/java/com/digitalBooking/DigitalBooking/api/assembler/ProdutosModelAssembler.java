package com.digitalBooking.DigitalBooking.api.assembler;

import com.digitalBooking.DigitalBooking.api.model.CidadesModel;
import com.digitalBooking.DigitalBooking.api.model.ProdutosModel;
import com.digitalBooking.DigitalBooking.domain.model.Cidades;
import com.digitalBooking.DigitalBooking.domain.model.Produtos;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProdutosModelAssembler {

    @Autowired
    private ModelMapper modelMapper;
    public ProdutosModel toModel(Produtos produtos) {

        return modelMapper.map(produtos, ProdutosModel.class);
    }

    public List<ProdutosModel> toCollectionModel(List<Produtos> produtos) {
        return produtos.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }

}
