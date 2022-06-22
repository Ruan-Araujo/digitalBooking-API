package com.digitalBooking.DigitalBooking.api.controller;

import com.digitalBooking.DigitalBooking.api.assembler.CidadeModelAssembler;
import com.digitalBooking.DigitalBooking.api.assembler.CidadesDisassembler;
import com.digitalBooking.DigitalBooking.api.model.CidadesModel;
import com.digitalBooking.DigitalBooking.api.model.input.CidadesInput;
import com.digitalBooking.DigitalBooking.core.security.CheckSecurity;
import com.digitalBooking.DigitalBooking.domain.exception.EntidadeNaoEncontradaException;
import com.digitalBooking.DigitalBooking.domain.exception.NegocioException;
import com.digitalBooking.DigitalBooking.domain.model.Cidades;
import com.digitalBooking.DigitalBooking.domain.repository.CidadesRepository;
import com.digitalBooking.DigitalBooking.domain.service.CidadesService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/cidades")
public class CidadeController {

    @Autowired
    private CidadesRepository cidadesRepository;

    @Autowired
    private CidadesService cidadesService;

    @Autowired
    private CidadeModelAssembler cidadeModelAssembler;

    @Autowired
    private CidadesDisassembler cidadesDisassembler;

    @CheckSecurity.Publico
    @GetMapping
    public List<CidadesModel> listar() {

        return cidadeModelAssembler.toCollectionModel(cidadesRepository.findAll());
    }

    @CheckSecurity.Publico
    @GetMapping("{cidadeId}")
    public CidadesModel buscar(@PathVariable Long cidadeId) {

        return cidadeModelAssembler.toModel(cidadesService.acharOuFalhar(cidadeId));
    }

    @CheckSecurity.ApenasAdmin
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CidadesModel adicionar(@RequestBody CidadesInput cidadsInput) {

        try {
            Cidades cidade = cidadesDisassembler.toDomainObject(cidadsInput);
            return cidadeModelAssembler.toModel(cidadesService.salvar(cidade));
        }
        catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }

    }

    @CheckSecurity.ApenasAdmin
    @PutMapping("{cidadeId}")
    public CidadesModel atualizar(@PathVariable Long cidadeId,
                                       @RequestBody CidadesInput cidadesInput) {
       try {
           Cidades cidade = cidadesDisassembler.toDomainObject(cidadesInput);
           Cidades cidadeAtual = cidadesService.acharOuFalhar(cidadeId);
           BeanUtils.copyProperties(cidade, cidadeAtual, "id");
           return cidadeModelAssembler.toModel(cidadesService.salvar(cidadeAtual));
       }
       catch (EntidadeNaoEncontradaException e) {
           throw new NegocioException(e.getMessage());
       }

    }

    @CheckSecurity.ApenasAdmin
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{cidadeId}")
    public void deletar(@PathVariable Long cidadeId) {
        cidadesService.excluir(cidadeId);
    }
}
