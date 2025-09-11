package com.lucasmes.mesprojeto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lucasmes.mesprojeto.entity.ItemVenda;


@Repository
public interface ItemVendaRepository  extends JpaRepository<ItemVenda, Long>{
 
}
