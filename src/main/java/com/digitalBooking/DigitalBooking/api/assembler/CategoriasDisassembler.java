package com.digitalBooking.DigitalBooking.api.assembler;

import com.digitalBooking.DigitalBooking.api.model.input.CategoriasInput;
import com.digitalBooking.DigitalBooking.domain.model.Categorias;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CategoriasDisassembler {

    @Autowired
    private ModelMapper modelMapper;
    public Categorias toDomainObject(CategoriasInput categoriasInput) {
        return modelMapper.map(categoriasInput, Categorias.class);
    }

    public CategoriasInput toInput(Categorias categoria) {
        return modelMapper.map(categoria, CategoriasInput.class);
    }
}
