package com.digitalBooking.DigitalBooking.domain.service;

import com.digitalBooking.DigitalBooking.domain.exception.EntidadeEmUsoException;
import com.digitalBooking.DigitalBooking.domain.exception.EntidadeNaoEncontradaException;
import com.digitalBooking.DigitalBooking.domain.model.Cidades;
import com.digitalBooking.DigitalBooking.domain.repository.CidadesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class CidadesService {

    public static final String MSG_CIDADE_NAO_ENCONTRADA =
            "Não existe um cadastro de cidade com o código %d";
    public static final String MSG_CIDADE_EM_USO =
                        "Cidade de código %d não pode ser removida, pois está em uso";
    @Autowired
    private CidadesRepository cidadesRepository;

    public Cidades salvar(Cidades cidade) {
        return cidadesRepository.save(cidade);
    }

    public void excluir(Long cidadeId) {

        try {
            cidadesRepository.deleteById(cidadeId);
        }
        catch (EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException(
                    String.format(MSG_CIDADE_NAO_ENCONTRADA,
                            cidadeId)
            );
        }
        catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format(MSG_CIDADE_EM_USO, cidadeId)
            );
        }
    }

    public Cidades acharOuFalhar(Long cidadeId) {
        return cidadesRepository.findById(cidadeId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                        String.format(MSG_CIDADE_NAO_ENCONTRADA, cidadeId)));
    }
 }
