package com.lucasmes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lucasmes.entity.Equipamento;

import java.util.Optional;


public interface EquipamentoRepository  extends JpaRepository<Equipamento,Long>{
 public Optional <Equipamento>  findByName(String name);
}
