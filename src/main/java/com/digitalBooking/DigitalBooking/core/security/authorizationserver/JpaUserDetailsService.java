package com.digitalBooking.DigitalBooking.core.security.authorizationserver;

import com.digitalBooking.DigitalBooking.domain.model.Usuarios;
import com.digitalBooking.DigitalBooking.domain.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Usuarios usuario = usuarioRepository.findByEmail(username)
                .orElseThrow(()
                        -> new UsernameNotFoundException("Usuário não encontrado"));

        return new AuthUser(usuario, getAuthorities(usuario));
    }

    private Collection<GrantedAuthority> getAuthorities(Usuarios usuario) {
        return usuario.getFuncoes().stream()
                .map(funcoes -> new SimpleGrantedAuthority(funcoes.getNome()
                        .toUpperCase())).collect(Collectors.toSet());
    }
}
