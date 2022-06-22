package com.digitalBooking.DigitalBooking.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Reservas {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue
    private Long id;

    @JsonIgnore
    @CreationTimestamp
    private LocalTime horaDaReserva;

    @Column(columnDefinition = "dateTime")
    private Date dataIda;

    @Column(columnDefinition = "dateTime")
    private Date dataVolta;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cliente_id")
    private Usuarios cliente;

    @ManyToOne
    @JoinColumn(name = "produto_reservado")
    private Produtos produto;
}
