package com.digitalBooking.DigitalBooking.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Produtos {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    private String descricao;

    @ManyToOne()
    @JoinColumn(name = "categoria_id", foreignKey = @ForeignKey(name = "fk_categoria_produto"))
    private Categorias categoria;

    @ManyToOne()
    @JoinColumn(name = "cidade_id", foreignKey = @ForeignKey(name = "fk_cidade_produto"))
    private Cidades cidade;

    //@JsonIgnore
    @OneToMany (cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Imagens> imagens = new ArrayList<>();

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "produtos_caracteristicas",
               joinColumns = @JoinColumn(name = "produtos_id"),
               inverseJoinColumns = @JoinColumn(name = "caracteristicas_id"))
    private List<Caracteristicas> caracteristicas = new ArrayList<>();
}
