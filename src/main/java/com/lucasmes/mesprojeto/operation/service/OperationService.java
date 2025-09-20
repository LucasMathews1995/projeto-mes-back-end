package com.lucasmes.mesprojeto.operation.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lucasmes.mesprojeto.entity.Batch;
import com.lucasmes.mesprojeto.entity.Operation;
import com.lucasmes.mesprojeto.entity.OperationId;
import com.lucasmes.mesprojeto.entity.ProductionOrder;
import com.lucasmes.mesprojeto.entity.Resource;
import com.lucasmes.mesprojeto.entity.enums.batch.FinalQuality;

import com.lucasmes.mesprojeto.entity.enums.operation.StatusOperation;
import com.lucasmes.mesprojeto.entity.enums.productiononder.StatusOP;
import com.lucasmes.mesprojeto.entity.enums.resource.CurrentStatus;
import com.lucasmes.mesprojeto.exceptions.NotAvailableBatchException;
import com.lucasmes.mesprojeto.exceptions.NotAvailableResourceException;
import com.lucasmes.mesprojeto.exceptions.NotFoundBatchException;
import com.lucasmes.mesprojeto.exceptions.NotFoundOperationException;
import com.lucasmes.mesprojeto.exceptions.NotFoundProductionOrderException;
import com.lucasmes.mesprojeto.exceptions.NotFoundResourceException;
import com.lucasmes.mesprojeto.exceptions.OutOfCapacityException;
import com.lucasmes.mesprojeto.operation.DTO.OperationDTOSender;
import com.lucasmes.mesprojeto.operation.DTO.OperationIdDTO;
import com.lucasmes.mesprojeto.repository.BatchRepository;
import com.lucasmes.mesprojeto.repository.OperationRepository;
import com.lucasmes.mesprojeto.repository.ProductionOrderRepository;
import com.lucasmes.mesprojeto.repository.ResourceRepository;

import jakarta.transaction.Transactional;

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



public List<OperationDTOSender> getAll(){
    List<Operation> operations = repository.findAll();
    List<OperationDTOSender> senders = operations.stream().map(it->{
        OperationDTOSender sender = new OperationDTOSender(it.getStartTime(),it.getProductionOrder().getProductionOrderName(),it.getResource().getNameArea(),it.getBatch().getBatchNumber());
        return sender;
    }).collect(Collectors.toList());

    

    return senders;
}
public Operation getOperation(String nameBatch, String resourceName, String opName){
    OperationId operationId = new OperationId();
    operationId.setResourceName(resourceName);
    operationId.setProductionOrder(opName);
    operationId.setBatchNumber(nameBatch);
    Operation op = repository.findById(operationId)
    .orElseThrow(()-> new NotFoundProductionOrderException("There's no production order with these params"));
    return op;
}

public OperationDTOSender setOperation (OperationIdDTO dto) throws OutOfCapacityException{

    if(dto.batchName()==null || dto.productionOrderName()==null || dto.resourceName()==null){
        throw new NotFoundOperationException("Send the params correctly");
    }

Operation op = new Operation();
OperationId operationId  = new OperationId(dto.batchName(),dto.resourceName(),dto.productionOrderName());
op.setId(operationId);

Resource resource = retrieveResource(dto.resourceName());
op.setResource(resource);

ProductionOrder productionOrder  = retrieveProductionOrder(dto.productionOrderName());
op.setProductionOrder(productionOrder);

Batch batch = retrieveBatch(dto.batchName());
productionOrder.addBatch(batch);

op.setStartTime(LocalDate.now());
op.setStatus(StatusOperation.READY);


 resource.verifyCapacity(batch); 
 op.addOP(productionOrder, batch, resource);
 OperationDTOSender opSender= new OperationDTOSender(op.getStartTime(),
 op.getProductionOrder().getProductionOrderName(),op.getResource().getNameResource(),
 op.getBatch().getBatchNumber());
repository.save(op);
return opSender;

}
@Transactional
public String  finishOperation(OperationIdDTO dto){

OperationId id = retrieveOperationId(dto);
Operation op = retrieveOperation(id);
Batch batch = retrieveBatch(dto.batchName());
ProductionOrder productionOrder = retrieveProductionOrder(dto.productionOrderName());
Resource resource = retrieveResource(dto.resourceName());



batch.finishBatch();
if(batch.getQuality()== FinalQuality.GOOD){productionOrder.addQuantityProduced();

    productionOrder.addQuantityProduced();
}else {
productionOrder.addQuantityRejected();
}
op.finishOperation();
resource.releasedResource();
productionOrder.removeBatch(batch);



repository.save(op);
return String.format("This operation : batchNumber ->%s and resource Name -> %s is finished", id.getBatchNumber(),id.getResourceName());

}


public String initiateOperation(OperationIdDTO dto){

 OperationId id = retrieveOperationId(dto);
Operation op = retrieveOperation(id);
op.initiateOperation();
Batch batch = retrieveBatch(dto.batchName());
ProductionOrder productionOrder = retrieveProductionOrder(dto.productionOrderName());
if(batch.initiateBatch()){
productionOrder.setStatusOP(StatusOP.EM_EXECUCAO);

  return "Operation was initialized ";
}else {
    return "Operation was not initialized because of the batch";
}

}
public List<Operation> setOperations(List<OperationIdDTO> dto){

    
    List<Operation> operations = dto.stream().map(it-> {
       if(it.batchName()==null || it.productionOrderName()==null || it.resourceName()==null){
        throw new NotFoundOperationException("Send the params correctly");
    }
      Operation op = new Operation();
      OperationId operationId = new OperationId(it.batchName(),it.resourceName(),it.productionOrderName());
      op.setId(operationId);
Resource resource = retrieveResource(it.resourceName());
op.setResource(resource);
ProductionOrder productionOrder  = retrieveProductionOrder(it.productionOrderName());
op.setProductionOrder(productionOrder);
productionOrder.addBatch(retrieveBatch(it.batchName()));
op.setStartTime(LocalDate.now());
op.setStatus(StatusOperation.READY);
return op;
    }).collect(Collectors.toList());


return operations;
}

public OperationDTOSender putInRework(String batch,String resourceName,String productionOrder){
   OperationId operationId= new OperationId(batch,resourceName,productionOrder); 
   Operation op = repository.findById(operationId)
   .orElseThrow(()-> new NotFoundOperationException("There's no operation with these params"));

    op.getBatch().putInRework();
    op.getResource().setNameArea("Rework");
    op.getProductionOrder().removeBatch(retrieveBatch(batch));
    op.getProductionOrder().addQuantityRejected();
    
repository.save(op);
OperationDTOSender opSender = new OperationDTOSender(
    op.getStartTime(),op.getProductionOrder().getProductionOrder()
,op.getResource().getNameResource(),op.getBatch().getBatchNumber());
    return opSender;

}
@Transactional
public void deleteOperation(String batch,String resourceName,String productionOrder){
    //only admin can be able to do this(later i will create login ADMIN and ROLE)
   OperationId operationId= new OperationId(batch,resourceName,productionOrder); 

   if(!repository.existsById(operationId)){
    throw new NotFoundOperationException("There's no opertion with theses params");
   }else {
    repository.existsById(operationId);
   }


 



}

@Transactional
public void deleteAllOperations(List<OperationIdDTO> dto){
    List<OperationId> operationId = dto.stream().map(it->{
       OperationId opId = new OperationId(it.batchName(),it.resourceName(),it.productionOrderName());

       if(repository.existsById(opId)){ return opId;}else {return null;
    }
      
    }).collect(Collectors.toList());

  repository.deleteAllById(operationId);

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
    operationId.setProductionOrder(dto.productionOrderName());
    operationId.setBatchNumber(dto.batchName());
    return operationId;
}
private Operation retrieveOperation (OperationId id ){
Operation operation = repository.findById(id).orElseThrow(()-> new NotFoundOperationException("There's no such operation in course"));
return operation;
}

}
