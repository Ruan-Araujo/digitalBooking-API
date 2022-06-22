package com.digitalBooking.DigitalBooking.domain.repository;

import com.digitalBooking.DigitalBooking.domain.model.Caracteristicas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CaracateristicasRepository extends JpaRepository<Caracteristicas, Long> {
}
