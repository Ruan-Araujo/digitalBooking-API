package com.digitalBooking.DigitalBooking.api.controller;

import com.digitalBooking.DigitalBooking.api.assembler.ReservasModelAssembler;
import com.digitalBooking.DigitalBooking.api.model.ReservasModel;
import com.digitalBooking.DigitalBooking.core.security.CheckSecurity;
import com.digitalBooking.DigitalBooking.core.security.DbSecurity;
import com.digitalBooking.DigitalBooking.domain.exception.EntidadeNaoEncontradaException;
import com.digitalBooking.DigitalBooking.domain.exception.NegocioException;
import com.digitalBooking.DigitalBooking.domain.model.Reservas;
import com.digitalBooking.DigitalBooking.domain.model.Usuarios;
import com.digitalBooking.DigitalBooking.domain.repository.ReservaRepository;
import com.digitalBooking.DigitalBooking.domain.service.ReservaService;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/reservas")
public class ReservaController {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private ReservaService reservaService;

    @Autowired
    private DbSecurity security;

    @Autowired
    private ReservasModelAssembler reservasModelAssembler;

    @CheckSecurity.ApenasAdmin
    @GetMapping
    public List<ReservasModel> listar() {

        return reservasModelAssembler.toCollectionModel(reservaRepository.findAll());
    }

    @CheckSecurity.Reservas.PodeReservar
    @GetMapping("{reservaId}")
    public ReservasModel buscar(@PathVariable Long reservaId) {

        return reservasModelAssembler.toModel(reservaService.acharOuFalhar(reservaId));
    }

    @CheckSecurity.Reservas.PodeReservar
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ReservasModel adicionar(@RequestBody Reservas reservas) {
        try {
            reservas.setCliente(new Usuarios());
            reservas.getCliente().setId(security.getUsuarioId());
            return reservasModelAssembler.toModel(reservaService.salvar(reservas));
        }
        catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }
    @CheckSecurity.Publico
    @GetMapping("/reservarPorCidadeEData")
    public List<Reservas> filtrar(@RequestParam(value="cidadeId") Long cidadeId,
                                  @RequestParam(value="dataIda" ) @JsonFormat(pattern = "dd/MM/yyyy") Date dataIda,
                                  @RequestParam(value="dataVolta") @JsonFormat(pattern = "dd/MM/yyyy") Date dataVolta) {
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return reservaRepository
                .findAllByProdutoCidadeIdAndDataIdaGreaterThanEqualAndDataVoltaLessThanEqual(cidadeId, dataIda, dataVolta);
    }
}
