package com.digitalBooking.DigitalBooking.api.assembler;

import com.digitalBooking.DigitalBooking.api.model.CidadesModel;
import com.digitalBooking.DigitalBooking.api.model.ReservasModel;
import com.digitalBooking.DigitalBooking.domain.model.Cidades;
import com.digitalBooking.DigitalBooking.domain.model.Reservas;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReservasModelAssembler {

    @Autowired
    private ModelMapper modelMapper;
    public ReservasModel toModel(Reservas reservas) {

        return modelMapper.map(reservas, ReservasModel.class);
    }

    public List<ReservasModel> toCollectionModel(List<Reservas> reservas) {
        return reservas.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }

}
