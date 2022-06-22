package com.digitalBooking.DigitalBooking.domain.repository;

import com.digitalBooking.DigitalBooking.domain.model.Imagens;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImagensRepository extends JpaRepository<Imagens, Long> {
}
