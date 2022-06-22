package com.digitalBooking.DigitalBooking.api.assembler;

import com.digitalBooking.DigitalBooking.api.model.input.CidadesInput;
import com.digitalBooking.DigitalBooking.domain.model.Cidades;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CidadesDisassembler {
    @Autowired
    private ModelMapper modelMapper;

    public Cidades toDomainObject(CidadesInput cidadesInput) {
        return modelMapper.map(cidadesInput, Cidades.class);
    }
}
