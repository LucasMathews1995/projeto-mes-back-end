package com.lucasmes.mesprojeto.service;





import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lucasmes.mesprojeto.DTO.BatchDTO;
import com.lucasmes.mesprojeto.entity.Batch;
import com.lucasmes.mesprojeto.entity.enums.FinalQuality;
import com.lucasmes.mesprojeto.entity.enums.StatusBatch;
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



    
    

    
    




  






  
   
}
