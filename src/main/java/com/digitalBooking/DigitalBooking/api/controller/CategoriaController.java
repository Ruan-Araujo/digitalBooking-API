package com.digitalBooking.DigitalBooking.api.controller;

import com.digitalBooking.DigitalBooking.api.assembler.CategoriasDisassembler;
import com.digitalBooking.DigitalBooking.api.assembler.CategoriasModelAssembler;
import com.digitalBooking.DigitalBooking.api.model.CategoriasModel;
import com.digitalBooking.DigitalBooking.api.model.input.CategoriasInput;
import com.digitalBooking.DigitalBooking.core.security.CheckSecurity;
import com.digitalBooking.DigitalBooking.domain.exception.EntidadeNaoEncontradaException;
import com.digitalBooking.DigitalBooking.domain.exception.NegocioException;
import com.digitalBooking.DigitalBooking.domain.model.Categorias;
import com.digitalBooking.DigitalBooking.domain.repository.CategoriasRepository;
import com.digitalBooking.DigitalBooking.domain.service.CategoriasService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private CategoriasRepository categoriaRepository;

    @Autowired
    private CategoriasService categoriaService;

    @Autowired
    private CategoriasModelAssembler categoriasAssembler;

    @Autowired
    private CategoriasDisassembler categoriasDisassembler;

    @CheckSecurity.Publico
    @GetMapping
    public List<CategoriasModel> listar(){

        return categoriasAssembler.toCollectionModel(categoriaRepository.findAll());
    }

    @CheckSecurity.Publico
    @GetMapping("/{categoriaId}")
    public CategoriasModel buscar(@PathVariable Long categoriaId) {

        return categoriasAssembler.toModel(categoriaService.acharOuFalhar(categoriaId));
    }

    @CheckSecurity.ApenasAdmin
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CategoriasModel adicionar(@RequestBody CategoriasInput categoriasInput) {
        try {
            Categorias categorias = categoriasDisassembler.toDomainObject(categoriasInput);
            return categoriasAssembler.toModel(categoriaService.salvar(categorias));
        }
        catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @CheckSecurity.ApenasAdmin
    @PutMapping("/{categoriaId}")
    public CategoriasModel atualizar(@PathVariable Long categoriaId,
                                       @RequestBody CategoriasInput categoria) {
        try {
            Categorias categorias = categoriasDisassembler.toDomainObject(categoria);
            Categorias categoriaAtual = categoriaService.acharOuFalhar(categoriaId);
            // Copiando propriedades de categoria -> categoriaAtual
            BeanUtils.copyProperties(categorias, categoriaAtual, "id");
            return categoriasAssembler.toModel(categoriaService.salvar(categoriaAtual));
        }
        catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @CheckSecurity.ApenasAdmin
    @DeleteMapping("{categoriaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long categoriaId) {
        categoriaService.excluir(categoriaId);
    }

    @CheckSecurity.ApenasAdmin
    @PatchMapping("{categoriaId}")
    public CategoriasModel atualizarParcial(@PathVariable Long categoriaId,
                                       @RequestBody Map<String, Object> campos) {

        Categorias categoriaAtual = categoriaService.acharOuFalhar(categoriaId);
        merge(campos, categoriaAtual);
        return atualizar(categoriaId, categoriasDisassembler.toInput(categoriaAtual));
    }

    private void merge(Map<String, Object> dadosOrigem, Categorias categoriaDestino) {

        ObjectMapper objectMapper = new ObjectMapper();

        Categorias categoriaOrigem = objectMapper.convertValue(dadosOrigem, Categorias.class);

        dadosOrigem.forEach((nomePropriedade, valorPropriedade) -> {

            Field field = ReflectionUtils.findField(Categorias.class, nomePropriedade);
            field.setAccessible(true);

            Object novoValor = ReflectionUtils.getField(field, categoriaOrigem);

            ReflectionUtils.setField(field, categoriaDestino, novoValor);
        });
    }
}
