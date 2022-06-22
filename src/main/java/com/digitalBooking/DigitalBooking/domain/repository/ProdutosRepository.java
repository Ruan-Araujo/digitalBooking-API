package com.digitalBooking.DigitalBooking.domain.repository;

import com.digitalBooking.DigitalBooking.domain.model.Produtos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutosRepository extends JpaRepository<Produtos, Long>{

    List<Produtos> consultarPorCategoria(Long id);

    List<Produtos> consultarPorCidade(Long id);
}
