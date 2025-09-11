package com.lucasmes.mesprojeto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lucasmes.mesprojeto.entity.Equipamento;

import java.util.Optional;

@Repository
public interface EquipamentoRepository  extends JpaRepository<Equipamento,Long>{
 public Optional <Equipamento>  findByName(String name);
}
