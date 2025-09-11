package com.lucasmes.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lucasmes.entity.OrdemVenda;


@Repository
public interface OrdemVendaRepository  extends JpaRepository<OrdemVenda,Long>{
public Optional<OrdemVenda> findByNumeroOV(String numeroOV);
public Optional<OrdemVenda> findByCliente(String cliente);

}
