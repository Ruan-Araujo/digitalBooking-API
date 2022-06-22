package com.digitalBooking.DigitalBooking.domain.repository;

import com.digitalBooking.DigitalBooking.domain.model.Funcoes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FuncoesRepository extends JpaRepository<Funcoes, Long> {
}
