package com.lucasmes.mesprojeto.batch.service;





import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lucasmes.mesprojeto.batch.DTO.BatchDTO;
import com.lucasmes.mesprojeto.batch.DTO.BatchDTOReceiver;
import com.lucasmes.mesprojeto.batch.DTO.BatchDTOSender;
import com.lucasmes.mesprojeto.entity.Batch;
import com.lucasmes.mesprojeto.entity.enums.batch.FinalQuality;
import com.lucasmes.mesprojeto.entity.enums.batch.StatusBatch;
import com.lucasmes.mesprojeto.exceptions.NotFoundBatchException;
import com.lucasmes.mesprojeto.repository.BatchRepository;

@Service
public class BatchService {

@Autowired
    private BatchRepository repository;


public List<Batch> getAllBatches(){
    List <Batch> batch = repository.findAll();
    if(batch.isEmpty()){
        throw new NotFoundBatchException("There are no batches ");
    }
    return batch;
}

public Batch getBatchByName(String batchName){
    Batch batch = repository.findById(batchName)
    .orElseThrow(()->new NotFoundBatchException("There's no batch with this name "));

    return batch;
}

public Batch saveBatch(BatchDTO dto){
    Batch batch = new Batch();
    batch.setBatchNumber(batch.giveBatchNumber());
    batch.setFinalDate(null);
    batch.setProgress(0.0);
    batch.setQuality(FinalQuality.GOOD);
    batch.setStartTime(LocalDate.now());
    batch.setThickness(dto.thickness());
    batch.setWeight(dto.weight());
    batch.setWidth(dto.width());
    batch.setStatus(StatusBatch.PROCESSANDO);
    batch.setQuality(FinalQuality.GOOD);
    return repository.save(batch);
}

public List<Batch> saveAllBatches(List<BatchDTO> dto) {
 List<Batch> batches = dto.stream().map(it-> {
 Batch batch = new Batch();
    batch.setBatchNumber(batch.giveBatchNumber());
    batch.setFinalDate(null);
    batch.setProgress(0.0);
    batch.setQuality(FinalQuality.GOOD);
    batch.setStartTime(LocalDate.now());
    batch.setThickness(it.thickness());
    batch.setWeight(it.weight());
    batch.setWidth(it.width());
    batch.setStatus(StatusBatch.PROCESSANDO);
    batch.setQuality(FinalQuality.GOOD);
    return batch;
 }).collect(Collectors.toList());
 return repository.saveAll(batches);
}

public List<BatchDTOSender> splitBatch(BatchDTOReceiver dto,Double weightSplit){
   Batch batch =  repository.findById(dto.batchNumber()).orElseThrow(()-> new NotFoundBatchException("There are no batches"));

 List<Batch> batches = batch.splitBatch(weightSplit, batch);
    List<BatchDTOSender> batchSenderList = batches.stream().map(it->{
        BatchDTOSender batchDTOSender= transformBatchDTOSender(it);
    return batchDTOSender;
    }).collect(Collectors.toList());


    repository.delete(batch);
    repository.saveAll(batches);

    return batchSenderList;
} 

public BatchDTOSender deleteBatch(String batchNumber){
    Batch b = repository.findById(batchNumber).orElseThrow
    (()-> new NotFoundBatchException("There's no batch with this number"));

    repository.delete(b);

BatchDTOSender batchDTOSender= transformBatchDTOSender(b);
    return batchDTOSender;
}





private BatchDTOSender transformBatchDTOSender(Batch b ){
 BatchDTOSender batchDTOSender = new BatchDTOSender(b.getBatchNumber(),b.getWeight()
        ,b.getWidth(),b.getThickness()
        ,b.getQuality(),b.getProgress(),b.getStatus());
        return batchDTOSender;
}

    
    

    
    




  






  
   
}
