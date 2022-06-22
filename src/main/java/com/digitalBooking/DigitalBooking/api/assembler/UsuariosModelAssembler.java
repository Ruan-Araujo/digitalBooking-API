package com.digitalBooking.DigitalBooking.api.assembler;

import com.digitalBooking.DigitalBooking.api.model.CategoriasModel;
import com.digitalBooking.DigitalBooking.api.model.UsuariosModel;
import com.digitalBooking.DigitalBooking.domain.model.Categorias;
import com.digitalBooking.DigitalBooking.domain.model.Usuarios;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UsuariosModelAssembler {
    @Autowired
    private ModelMapper modelMapper;
    public UsuariosModel toModel(Usuarios usuarios) {
        return modelMapper.map(usuarios, UsuariosModel.class);
    }

    public List<UsuariosModel> toCollectionModel(List<Usuarios> usuarios) {
        return usuarios.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }

}
