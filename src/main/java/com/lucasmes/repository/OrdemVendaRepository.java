package com.lucasmes.repository;




import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lucasmes.entity.OrdemVenda;

public interface OrdemVendaRepository  extends JpaRepository<OrdemVenda,Long>{
public Optional<OrdemVenda> findByNumeroOV(String numeroOV);
public Optional<OrdemVenda> findByName(String name);

}
