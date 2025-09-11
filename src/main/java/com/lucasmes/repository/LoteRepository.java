package com.lucasmes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.lucasmes.entity.Lote;


@Repository
public interface LoteRepository  extends JpaRepository<Lote,Long>{

}
