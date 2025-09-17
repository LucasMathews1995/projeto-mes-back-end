package com.lucasmes.mesprojeto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lucasmes.mesprojeto.entity.Batch;

@Repository
public interface BatchRepository  extends JpaRepository<Batch,String>{

}
