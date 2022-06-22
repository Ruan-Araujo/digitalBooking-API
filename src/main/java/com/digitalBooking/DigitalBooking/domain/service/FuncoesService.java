package com.digitalBooking.DigitalBooking.domain.service;

import com.digitalBooking.DigitalBooking.domain.exception.EntidadeEmUsoException;
import com.digitalBooking.DigitalBooking.domain.exception.EntidadeNaoEncontradaException;
import com.digitalBooking.DigitalBooking.domain.model.Cidades;
import com.digitalBooking.DigitalBooking.domain.model.Funcoes;
import com.digitalBooking.DigitalBooking.domain.repository.FuncoesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class FuncoesService {

    public static final String MSG_FUNCAO_NAO_ENCONTRADA =
            "Não existe um cadastro de função com o código %d";
    public static final String MSG_FUNCAO_EM_USO =
            "Função de código %d não pode ser removido, pois está em uso";
    @Autowired
    private FuncoesRepository funcoesRepository;

    public Funcoes salvar(Funcoes funcao) {
        return funcoesRepository.save(funcao);
    }

    public void deletar(Long funcaoId) {
        try {
            funcoesRepository.deleteById(funcaoId);
        }
        catch (EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException(
                    String.format(MSG_FUNCAO_NAO_ENCONTRADA,
                            funcaoId)
            );
        }
        catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format(MSG_FUNCAO_EM_USO, funcaoId)
            );
        }
    }

    public Funcoes acharOuFalhar(Long funcaoId) {
        return funcoesRepository.findById(funcaoId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                        String.format(MSG_FUNCAO_NAO_ENCONTRADA, funcaoId)));
    }
}
