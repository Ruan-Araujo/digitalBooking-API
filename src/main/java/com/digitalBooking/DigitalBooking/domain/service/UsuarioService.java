package com.digitalBooking.DigitalBooking.domain.service;

import com.digitalBooking.DigitalBooking.domain.exception.EntidadeEmUsoException;
import com.digitalBooking.DigitalBooking.domain.exception.EntidadeNaoEncontradaException;
import com.digitalBooking.DigitalBooking.domain.model.Funcoes;
import com.digitalBooking.DigitalBooking.domain.model.Reservas;
import com.digitalBooking.DigitalBooking.domain.model.Usuarios;
import com.digitalBooking.DigitalBooking.domain.repository.FuncoesRepository;
import com.digitalBooking.DigitalBooking.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UsuarioService {

    public static final String MSG_JA_EXISTE_USUARIO_CADASTRADO =
            "Já existe um usuário cadastrado com o e-mail %s";

    public static final String MSG_USUARIO_NAO_ENCONTRADO =
            "Não existe um cadastro de usuario com o código %d";
    public static final String SENHA_ATUAL_INFORMADA_NÃO_COINCIDE_COM_A_SENHA_DO_USUÁRIO =
            "Senha atual informada não coincide com a senha do usuário.";

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private FuncoesRepository funcoesRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Usuarios salvar(Usuarios usuario) {
        Optional<Usuarios> usuarioExiste = usuarioRepository.findByEmail(usuario.getEmail());

        if (usuarioExiste.isPresent() && !usuarioExiste.get().equals(usuario)) {
            throw new EntidadeEmUsoException(String.format(
                    MSG_JA_EXISTE_USUARIO_CADASTRADO, usuario.getEmail()
            ));
        }

        if (usuario.isNovo()) {
            usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        }

        return usuarioRepository.save(usuario);
    }

    @Transactional
    public void desassociarFuncoes(Long usuarioId, Long funcaoId) {
        Usuarios usuario = acharOuFalhar(usuarioId);
        Funcoes funcao = funcoesRepository.findById(funcaoId).orElse(null);

        if (usuario != null && funcao != null) {
            usuario.removerFuncao(funcao);
        }
    }

    @Transactional
    public void associarFuncao(Long usuarioId, Long funcaoId) {
        Usuarios usuario = acharOuFalhar(usuarioId);
        Funcoes funcao = funcoesRepository.findById(funcaoId).orElse(null);

        if (usuario != null && funcao != null) {
            usuario.adicionarFuncao(funcao);
        }
    }

    @Transactional
    public void alterarSenha(Long usuarioId, String senhaAtual, String novaSenha) {
        Usuarios usuario = acharOuFalhar(usuarioId);

        if (!passwordEncoder.matches(senhaAtual, usuario.getSenha())) {
            throw new EntidadeNaoEncontradaException(SENHA_ATUAL_INFORMADA_NÃO_COINCIDE_COM_A_SENHA_DO_USUÁRIO);
        }

        usuario.setSenha(passwordEncoder.encode(novaSenha));
    }

    public Usuarios acharOuFalhar(Long usuarioId) {
        return usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                        String.format(MSG_USUARIO_NAO_ENCONTRADO, usuarioId)));
    }
}
