package com.digitalBooking.DigitalBooking.api.assembler;

import com.digitalBooking.DigitalBooking.api.model.input.CidadesInput;
import com.digitalBooking.DigitalBooking.api.model.input.UsuariosInput;
import com.digitalBooking.DigitalBooking.domain.model.Cidades;
import com.digitalBooking.DigitalBooking.domain.model.Usuarios;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsuariosDisAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public Usuarios toDomainObject(UsuariosInput usuariosInput) {
        return modelMapper.map(usuariosInput, Usuarios.class);
    }

}
