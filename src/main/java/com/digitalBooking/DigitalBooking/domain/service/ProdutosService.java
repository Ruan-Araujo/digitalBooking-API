package com.digitalBooking.DigitalBooking.domain.service;

import com.digitalBooking.DigitalBooking.domain.exception.EntidadeEmUsoException;
import com.digitalBooking.DigitalBooking.domain.exception.EntidadeNaoEncontradaException;
import com.digitalBooking.DigitalBooking.domain.model.*;
import com.digitalBooking.DigitalBooking.domain.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class ProdutosService {

    public static final String MSG_PRODUTO_NAO_ENCONTRADO =
            "Não existe um cadastro de produto com o código %d";
    public static final String MSG_PRODUTO_EM_USO =
            "Produto de código %d não pode ser removido, pois está em uso";
    public static final String MSG_CIDADE_NAO_ENCONTRADA =
            "Não existe cadastro de cidade com código %d";
    public static final String MSG_CATEGORIA_NAO_ENCONTRADA =
            "Não existe cadastro de categoria com código %d";
    @Autowired
    private ProdutosRepository produtosRepository;

    @Autowired
    private CidadesRepository cidadesRepository;

    @Autowired
    private CategoriasRepository categoriasRepository;

    public Produtos salvar(Produtos produto) {

        Long cidadeId = produto.getCidade().getId();
        Cidades cidade = cidadesRepository.findById(cidadeId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                        String.format(MSG_CIDADE_NAO_ENCONTRADA, cidadeId)
                ));

        produto.setCidade(cidade);

        Long categoriaId = produto.getCategoria().getId();
        Categorias categoria = categoriasRepository.findById(categoriaId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                        String.format(MSG_CATEGORIA_NAO_ENCONTRADA, categoriaId)
                ));

        produto.setCategoria(categoria);

        return produtosRepository.save(produto);
    }

    public void deletar(Long produtoId) {
        try {
            produtosRepository.deleteById(produtoId);
        }
        catch (EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException(
                String.format(MSG_PRODUTO_NAO_ENCONTRADO,
                        produtoId)
            );
        }
        catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format(MSG_PRODUTO_EM_USO, produtoId)
            );
        }
    }

    public Produtos acharOuFalhar(Long produtoId) {
        return produtosRepository.findById(produtoId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                        String.format(MSG_PRODUTO_NAO_ENCONTRADO, produtoId)));
    }
}
