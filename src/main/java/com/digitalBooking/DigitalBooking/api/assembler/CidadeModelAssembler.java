package com.digitalBooking.DigitalBooking.api.assembler;

import com.digitalBooking.DigitalBooking.api.model.CidadesModel;
import com.digitalBooking.DigitalBooking.domain.model.Cidades;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CidadeModelAssembler {

    @Autowired
    private ModelMapper modelMapper;
    public CidadesModel toModel(Cidades cidades) {
        return modelMapper.map(cidades, CidadesModel.class);
    }

    public List<CidadesModel> toCollectionModel(List<Cidades> cidades) {
        return cidades.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }

}
