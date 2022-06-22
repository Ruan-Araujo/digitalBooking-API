package com.digitalBooking.DigitalBooking.api.assembler;

import com.digitalBooking.DigitalBooking.api.model.CategoriasModel;
import com.digitalBooking.DigitalBooking.domain.model.Categorias;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CategoriasModelAssembler {

    @Autowired
    private ModelMapper modelMapper;
    public CategoriasModel toModel(Categorias categoria) {
        return modelMapper.map(categoria, CategoriasModel.class);
    }

    public List<CategoriasModel> toCollectionModel(List<Categorias> categorias) {
        return categorias.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }
}
