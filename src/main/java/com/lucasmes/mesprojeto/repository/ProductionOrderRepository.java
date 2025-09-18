package com.lucasmes.mesprojeto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lucasmes.mesprojeto.entity.ProductionOrder;

import java.util.Optional;




public interface ProductionOrderRepository  extends JpaRepository<ProductionOrder,String>{
public Optional<ProductionOrder> findByProductionOrder(String productionOrder);
}
