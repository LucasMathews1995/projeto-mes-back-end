package com.lucasmes.mesprojeto.service;





import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.lucasmes.mesprojeto.entity.Batch;


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



    
    

    
    




  






  
   
}
