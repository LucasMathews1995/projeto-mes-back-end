package com.lucasmes.mesprojeto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lucasmes.mesprojeto.entity.Operation;
import com.lucasmes.mesprojeto.entity.OperationId;

public interface OperationRepository extends JpaRepository<Operation,OperationId> {

}
