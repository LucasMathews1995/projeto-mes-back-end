package com.lucasmes.mesprojeto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lucasmes.mesprojeto.entity.Lote;


@Repository
public interface LoteRepository  extends JpaRepository<Lote,Long>{

}
