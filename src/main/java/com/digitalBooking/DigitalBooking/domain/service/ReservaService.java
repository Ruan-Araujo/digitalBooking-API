package com.digitalBooking.DigitalBooking.domain.service;

import com.digitalBooking.DigitalBooking.domain.exception.EntidadeNaoEncontradaException;
import com.digitalBooking.DigitalBooking.domain.model.Cidades;
import com.digitalBooking.DigitalBooking.domain.model.Reservas;
import com.digitalBooking.DigitalBooking.domain.model.Usuarios;
import com.digitalBooking.DigitalBooking.domain.repository.ReservaRepository;
import com.digitalBooking.DigitalBooking.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
public class ReservaService {

    public static final String MSG_RESERVA_NAO_ENCONTRADA =
            "Não existe um cadastro de reserva com o código %d";
    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Transactional
    public Reservas salvar(Reservas reserva) {
        validarPedido(reserva);
        return reservaRepository.save(reserva);
    }

    private void validarPedido(Reservas reserva) {
        usuarioRepository
                .findById(reserva.getCliente().getId())
                .ifPresent(reserva::setCliente);
    }

    public Reservas acharOuFalhar(Long reservaId) {
        return reservaRepository.findById(reservaId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                        String.format(MSG_RESERVA_NAO_ENCONTRADA, reservaId)));
    }

    public List<Reservas> listar(Long id, Date dataInicio, Date dataFim) {
        return reservaRepository
                .findAllByProdutoCidadeIdAndDataIdaGreaterThanEqualAndDataVoltaLessThanEqual(id, dataInicio, dataFim);
    }
}
