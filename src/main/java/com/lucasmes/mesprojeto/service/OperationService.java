package com.lucasmes.mesprojeto.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lucasmes.mesprojeto.DTO.OperationIdDTO;
import com.lucasmes.mesprojeto.entity.Batch;
import com.lucasmes.mesprojeto.entity.Operation;
import com.lucasmes.mesprojeto.entity.OperationId;
import com.lucasmes.mesprojeto.entity.ProductionOrder;
import com.lucasmes.mesprojeto.entity.Resource;
import com.lucasmes.mesprojeto.entity.enums.CurrentStatus;
import com.lucasmes.mesprojeto.entity.enums.FinalQuality;
import com.lucasmes.mesprojeto.entity.enums.StatusBatch;
import com.lucasmes.mesprojeto.entity.enums.StatusOperation;
import com.lucasmes.mesprojeto.exceptions.NotAvailableBatchException;
import com.lucasmes.mesprojeto.exceptions.NotAvailableResourceException;
import com.lucasmes.mesprojeto.exceptions.NotFoundBatchException;
import com.lucasmes.mesprojeto.exceptions.NotFoundOperationException;
import com.lucasmes.mesprojeto.exceptions.NotFoundProductionOrderException;
import com.lucasmes.mesprojeto.exceptions.NotFoundResourceException;
import com.lucasmes.mesprojeto.repository.BatchRepository;
import com.lucasmes.mesprojeto.repository.OperationRepository;
import com.lucasmes.mesprojeto.repository.ProductionOrderRepository;
import com.lucasmes.mesprojeto.repository.ResourceRepository;

@Service
public class OperationService {
@Autowired
private OperationRepository repository;
@Autowired
private ProductionOrderRepository opRepository;
@Autowired 
private BatchRepository batchRepository;
@Autowired 
private ResourceRepository resourceRepository;



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
    .orElseThrow(()-> new NotFoundProductionOrderException("There's no production order with these params"));
    return op;
}

public Operation setOperation (OperationIdDTO dto) {

    if(dto.batchName()==null || dto.productionOrderName()==null || dto.resourceName()==null){
        throw new NotFoundOperationException("Send the params correctly");
    }
Operation op = new Operation();
OperationId operationId  = new OperationId(dto.batchName(),dto.resourceName(),dto.productionOrderName());
op.setId(operationId);
Batch batch  = retrieveBatch(dto.batchName());
op.setBatch(batch);
Resource resource = retrieveResource(dto.resourceName());
op.setResource(resource);
ProductionOrder productionOrder  = retrieveProductionOrder(dto.productionOrderName());
op.setProductionOrder(productionOrder);
op.setStartTime(LocalDate.now());
op.setStatus(StatusOperation.READY);


return repository.save(op);
}

public Operation finishOperation(OperationIdDTO dto){

    Operation op = repository.findById(retrieveOperationId(dto))
    .orElseThrow(()-> new NotFoundProductionOrderException("There's no production order with these params"));
    
    op.setEndTime(LocalDate.now());
    op.setStatus(StatusOperation.FINISHED);
    op.getBatch().setFinalDate(LocalDate.now());
    op.getBatch().setResource(null);
    op.getBatch().setProgress(100.0);
    op.getBatch().setStatus(StatusBatch.CONCLUIDO);
  
    return repository.save(op);
}
public Operation initiateOperation(OperationIdDTO dto){
     Operation op = repository.findById(retrieveOperationId(dto))
    .orElseThrow(()-> new NotFoundProductionOrderException("There's no production order with these params"));

    op.setStatus(StatusOperation.PROCESSING);
    op.getBatch().setStartTime(LocalDate.now());

    return repository.save(op);
}
 























private Batch retrieveBatch  (String batchName) {
   Batch batch =  batchRepository.findById(batchName).orElseThrow(()-> new NotFoundBatchException("There's no such batch"));

  if(batch.getQuality()==FinalQuality.BAD){

    throw new NotAvailableBatchException("This batch is in bad condition");
  }
    return batch;

}
  



private Resource retrieveResource(String resourceName){
  Resource resource =  resourceRepository.findById(resourceName).orElseThrow(()-> new NotFoundResourceException("There's no such resource"));
if(resource.getCurrentStatus() == CurrentStatus.MANUTENCAO 
|| resource.getCurrentStatus()==CurrentStatus.EM_ESPERA|| resource.getCurrentStatus()==CurrentStatus.QUEBRADO){
    throw new NotAvailableResourceException("The resource is not available");
   
}
return resource;

}

private ProductionOrder retrieveProductionOrder (String productionOrderName){
return opRepository.findById(productionOrderName).orElseThrow(()-> new NotFoundProductionOrderException("There's no such Production Order"));
}
private OperationId retrieveOperationId(OperationIdDTO dto){
 OperationId operationId = new OperationId();
 operationId.setResourceName(dto.resourceName());
    operationId.setOpName(dto.productionOrderName());
    operationId.setBatchNumber(dto.batchName());
    return operationId;
}

}
