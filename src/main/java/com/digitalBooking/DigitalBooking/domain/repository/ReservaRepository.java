package com.digitalBooking.DigitalBooking.domain.repository;

import com.digitalBooking.DigitalBooking.domain.model.Reservas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reservas, Long> {
    List<Reservas>
        findAllByProdutoCidadeIdAndDataIdaGreaterThanEqualAndDataVoltaLessThanEqual
            (Long cidadeId, Date dataIda, Date dataVolta);
}
