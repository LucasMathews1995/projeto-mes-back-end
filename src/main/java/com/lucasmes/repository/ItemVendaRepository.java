package com.lucasmes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.lucasmes.entity.ItemVenda;


@Repository
public interface ItemVendaRepository  extends JpaRepository<ItemVenda, Long>{
 
}
