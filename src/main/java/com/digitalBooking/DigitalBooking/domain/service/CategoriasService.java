package com.digitalBooking.DigitalBooking.domain.service;

import com.digitalBooking.DigitalBooking.domain.exception.EntidadeEmUsoException;
import com.digitalBooking.DigitalBooking.domain.exception.EntidadeNaoEncontradaException;
import com.digitalBooking.DigitalBooking.domain.model.Categorias;
import com.digitalBooking.DigitalBooking.domain.repository.CategoriasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CategoriasService {

    public static final String MSG_CATEGORIA_NAO_ENCONTRADA =
            "Não existe um cadastro de categoria com o código %d";
    public static final String MSG_CATEGORIA_EM_USO =
            "Categoria de código %d não pode ser removido, pois está em uso";

    @Autowired
    private CategoriasRepository categoriaRepository;

    public Categorias salvar(Categorias categoria) {

        return categoriaRepository.save(categoria);
    }

    public void excluir(Long categoriaId) {
        try {
            categoriaRepository.deleteById(categoriaId);
        }
        catch (EmptyResultDataAccessException e) {

            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format(MSG_CATEGORIA_NAO_ENCONTRADA,
                            categoriaId));

        }
        catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format(MSG_CATEGORIA_EM_USO, categoriaId)
            );
        }
    }

    public Categorias acharOuFalhar(Long categoriaId) {
        return categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                        String.format(MSG_CATEGORIA_NAO_ENCONTRADA,
                           categoriaId)));
    }
}
