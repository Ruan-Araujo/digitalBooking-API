package com.digitalBooking.DigitalBooking.api.controller;

import com.digitalBooking.DigitalBooking.core.security.CheckSecurity;
import com.digitalBooking.DigitalBooking.domain.exception.EntidadeNaoEncontradaException;
import com.digitalBooking.DigitalBooking.domain.exception.NegocioException;
import com.digitalBooking.DigitalBooking.domain.model.Funcoes;
import com.digitalBooking.DigitalBooking.domain.repository.FuncoesRepository;
import com.digitalBooking.DigitalBooking.domain.service.FuncoesService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/funcoes")
public class FuncaoController {

    @Autowired
    private FuncoesRepository funcoesRepository;

    @Autowired
    private FuncoesService funcoesService;

    @CheckSecurity.ApenasAdmin
    @GetMapping
    public List<Funcoes> listar() {
        return funcoesRepository.findAll();
    }

    @CheckSecurity.ApenasAdmin
    @GetMapping("{funcaoId}")
    public Funcoes buscar(@PathVariable Long funcaoId) {
        return funcoesService.acharOuFalhar(funcaoId);
    }

    @CheckSecurity.ApenasAdmin
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Funcoes adicionar(@RequestBody Funcoes funcao) {
        try {
            return funcoesService.salvar(funcao);
        }
        catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @CheckSecurity.ApenasAdmin
    @PutMapping("{funcaoId}")
    public Funcoes atualizar(@PathVariable Long funcaoId,
                                        @RequestBody Funcoes funcao) {

        Funcoes funcaoAtual = funcoesService.acharOuFalhar(funcaoId);
        BeanUtils.copyProperties(funcao, funcaoAtual, "id");
        return funcoesService.salvar(funcaoAtual);
    }

    @CheckSecurity.ApenasAdmin
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{funcaoId}")
    public void deletar(@PathVariable Long funcaoId) {
        funcoesService.deletar(funcaoId);
    }
}
