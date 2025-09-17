package com.lucasmes.mesprojeto.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lucasmes.mesprojeto.DTO.OperationIdDTO;
import com.lucasmes.mesprojeto.entity.Batch;
import com.lucasmes.mesprojeto.entity.Operation;
import com.lucasmes.mesprojeto.entity.OperationId;

import com.lucasmes.mesprojeto.exceptions.NotFoundProductionOrder;
import com.lucasmes.mesprojeto.repository.BatchRepository;
import com.lucasmes.mesprojeto.repository.OperationRepository;
import com.lucasmes.mesprojeto.repository.ProductionOrderRepository;

@Service
public class OperationService {
@Autowired
private OperationRepository repository;
@Autowired
private ProductionOrderRepository opRepository;
@Autowired 
private BatchRepository batchRepository;



public List<Operation> getAll(){
    List<Operation> operations = repository.findAll();
    return operations;
}
public Operation getOperation(String nameBatch, String resourceName, String opName){
    OperationId operationId = new OperationId();
    operationId.setResourceName(resourceName);
    operationId.setOpName(opName);
    operationId.setBatchNumber(nameBatch);
    Operation op = repository.findById(operationId)
    .orElseThrow(()-> new NotFoundProductionOrder("There's no production order with these params"));
    return op;
}

public Operation saveOperation (OperationIdDTO dto) {
Operation op = new Operation();
OperationId operationId  = new OperationId(dto.batchName(),dto.resourceName(),dto.productionOrderName());

}


















private Batch retrieveBatch  (Batch batch) {
    
    return 
}
}
