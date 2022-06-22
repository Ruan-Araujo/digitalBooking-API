package com.digitalBooking.DigitalBooking.api.controller;

import com.digitalBooking.DigitalBooking.domain.model.Funcoes;
import com.digitalBooking.DigitalBooking.domain.model.Usuarios;
import com.digitalBooking.DigitalBooking.domain.repository.UsuarioRepository;
import com.digitalBooking.DigitalBooking.domain.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping(value = "usuarios/{usuarioId}/funcoes")
public class UsuarioFuncaoController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public Set<Funcoes> listar(@PathVariable Long usuarioId) {
        Usuarios usuario = usuarioRepository.findById(usuarioId).orElse(null);

        assert usuario != null;
        return usuario.getFuncoes();

    }

    @DeleteMapping("/{funcaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociar(@PathVariable Long usuarioId, @PathVariable Long funcaoId) {
        usuarioService.desassociarFuncoes(usuarioId, funcaoId);
    }

    @PutMapping("/{funcaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associar(@PathVariable Long usuarioId, @PathVariable Long funcaoId) {
        usuarioService.associarFuncao(usuarioId, funcaoId);
    }
}
