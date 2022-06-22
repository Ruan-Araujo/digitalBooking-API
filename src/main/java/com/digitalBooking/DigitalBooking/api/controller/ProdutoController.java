package com.digitalBooking.DigitalBooking.api.controller;

import com.digitalBooking.DigitalBooking.api.assembler.ProdutosDisassembler;
import com.digitalBooking.DigitalBooking.api.assembler.ProdutosModelAssembler;
import com.digitalBooking.DigitalBooking.api.model.ProdutosModel;
import com.digitalBooking.DigitalBooking.api.model.input.ProdutosInput;
import com.digitalBooking.DigitalBooking.core.security.CheckSecurity;
import com.digitalBooking.DigitalBooking.domain.exception.EntidadeNaoEncontradaException;
import com.digitalBooking.DigitalBooking.domain.exception.NegocioException;
import com.digitalBooking.DigitalBooking.domain.model.Produtos;
import com.digitalBooking.DigitalBooking.domain.repository.ProdutosRepository;
import com.digitalBooking.DigitalBooking.domain.service.ProdutosService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutosRepository produtosRepository;

    @Autowired
    private ProdutosService produtosService;

    @Autowired
    private ProdutosModelAssembler produtosModelAssembler;

    @Autowired
    private ProdutosDisassembler produtosDisassembler;

    @CheckSecurity.Publico
    @GetMapping
    public List<ProdutosModel> listar() {
        return produtosModelAssembler.toCollectionModel(produtosRepository.findAll());
    }

    @CheckSecurity.Publico
    @GetMapping("{produtoId}")
    public ProdutosModel buscar(@PathVariable Long produtoId) {

        return produtosModelAssembler.toModel(produtosService.acharOuFalhar(produtoId));
    }

    @CheckSecurity.ApenasAdmin
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ProdutosModel adicionar(@RequestBody ProdutosInput produtosInput) {
        try {
            Produtos produto = produtosDisassembler.toDomainObject(produtosInput);
            return produtosModelAssembler.toModel(produtosService.salvar(produto));
        }
        catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @CheckSecurity.ApenasAdmin
    @PutMapping("{produtoId}")
    public ProdutosModel atualizar(@PathVariable Long produtoId,
                                        @RequestBody ProdutosInput produtoInput) {

        Produtos produto = produtosDisassembler.toDomainObject(produtoInput);
        Produtos produtoAtual = produtosService.acharOuFalhar(produtoId);
        BeanUtils.copyProperties(produto, produtoAtual, "id");
        try {
            return produtosModelAssembler.toModel(produtosService.salvar(produtoAtual));
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @CheckSecurity.ApenasAdmin
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{produtoId}")
    public void deletar(@PathVariable Long produtoId) {
        produtosService.deletar(produtoId);
    }

    @CheckSecurity.Publico
    @GetMapping("/categorias/{categoriaId}")
    public List<Produtos> buscarPorCategoria(@PathVariable Long categoriaId) {
        return produtosRepository.consultarPorCategoria(categoriaId);
    }

    @CheckSecurity.Publico
    @GetMapping("/cidades/{cidadeId}")
    public List<Produtos> buscarPorCidade(@PathVariable Long cidadeId) {
        return produtosRepository.consultarPorCidade(cidadeId);
    }
}
