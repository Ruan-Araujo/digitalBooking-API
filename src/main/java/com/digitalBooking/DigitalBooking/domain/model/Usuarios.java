package com.digitalBooking.DigitalBooking.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Usuarios {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String sobrenome;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String senha;

    @JsonIgnore
    @CreationTimestamp
    @Column(nullable = false, columnDefinition = "dateTime")
    private OffsetDateTime dataCadastro;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "usuario_funcao",
               joinColumns = @JoinColumn(name = "usuarios_id"),
               inverseJoinColumns = @JoinColumn(name = "funcoes_id"))
    private Set<Funcoes> funcoes = new HashSet<>();

    public boolean removerFuncao(Funcoes funcao) {
        return getFuncoes().remove(funcao);
    }

    public boolean adicionarFuncao(Funcoes funcao) {
        return getFuncoes().add(funcao);
    }
    public boolean isNovo() {
        return getId() == null;
    }
}
