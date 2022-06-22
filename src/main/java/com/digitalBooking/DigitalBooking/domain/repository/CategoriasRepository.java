package com.digitalBooking.DigitalBooking.domain.repository;
import com.digitalBooking.DigitalBooking.domain.model.Categorias;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface CategoriasRepository extends JpaRepository<Categorias, Long> {
    List<Categorias> findByTitulo(String titulo);
}
