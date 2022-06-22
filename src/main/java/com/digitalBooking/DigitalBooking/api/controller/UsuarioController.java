package com.digitalBooking.DigitalBooking.api.controller;

import com.digitalBooking.DigitalBooking.api.assembler.UsuariosDisAssembler;
import com.digitalBooking.DigitalBooking.api.assembler.UsuariosModelAssembler;
import com.digitalBooking.DigitalBooking.api.model.UsuariosModel;
import com.digitalBooking.DigitalBooking.api.model.input.UsuariosInput;
import com.digitalBooking.DigitalBooking.core.security.CheckSecurity;

import com.digitalBooking.DigitalBooking.domain.exception.EntidadeNaoEncontradaException;
import com.digitalBooking.DigitalBooking.domain.exception.NegocioException;
import com.digitalBooking.DigitalBooking.domain.model.Usuarios;
import com.digitalBooking.DigitalBooking.domain.repository.UsuarioRepository;
import com.digitalBooking.DigitalBooking.domain.service.UsuarioService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuariosModelAssembler usuariosModelAssembler;

    @Autowired
    private UsuariosDisAssembler usuariosDisAssembler;

    @CheckSecurity.ApenasAdmin
    @GetMapping
    public List<UsuariosModel> listar() {
        return usuariosModelAssembler.toCollectionModel(usuarioRepository.findAll());
    }

    @CheckSecurity.ApenasCliente
    @GetMapping("{usuarioId}")
    public UsuariosModel buscar(@PathVariable Long usuarioId){

        return usuariosModelAssembler.toModel(usuarioService.acharOuFalhar(usuarioId));
    }

    @CheckSecurity.Publico
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public UsuariosModel adicionar(@RequestBody UsuariosInput usuariosInput) {
        try {
            Usuarios usuario = usuariosDisAssembler.toDomainObject(usuariosInput);
            usuarioService.salvar(usuario);
            usuarioService.associarFuncao(usuario.getId(), 5L);
            return usuariosModelAssembler.toModel(usuario);
        }
        catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @CheckSecurity.ApenasCliente
    @PutMapping("{usuarioId}")
    public UsuariosModel atualizar(@PathVariable Long usuarioId,
                                        @RequestBody UsuariosInput usuariosInput) {

        Usuarios usuario = usuariosDisAssembler.toDomainObject(usuariosInput);
        Usuarios usuarioAtual = usuarioService.acharOuFalhar(usuarioId);
        BeanUtils.copyProperties(usuario, usuarioAtual, "id");
        try {
            return usuariosModelAssembler.toModel(usuarioService.salvar(usuarioAtual));
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }
}
