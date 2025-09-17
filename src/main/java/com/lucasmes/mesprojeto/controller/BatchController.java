package com.lucasmes.mesprojeto.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lucasmes.mesprojeto.DTO.BatchDTO;
import com.lucasmes.mesprojeto.entity.Batch;
import com.lucasmes.mesprojeto.exceptions.NotAvailableBatchException;
import com.lucasmes.mesprojeto.exceptions.NotFoundBatchException;
import com.lucasmes.mesprojeto.service.BatchService;



@RestController
@RequestMapping("/batch")
public class BatchController {

@Autowired
private BatchService service;


@PostMapping("/save")
public ResponseEntity<Batch> saveBatch(BatchDTO dto){
    Batch batch = service.saveBatch(dto);
    try {
         return  ResponseEntity.status(HttpStatus.CREATED).body(batch);
    } catch (NotAvailableBatchException e) {
     return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(batch);
    }
    
}
@GetMapping()
public  ResponseEntity<List<Batch>> getAllBatches(){
    List<Batch> batch = service.getAllBatches();

    try {
        return ResponseEntity.status(HttpStatus.CREATED).body(batch);
    } catch (NotFoundBatchException e) {
       return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }catch(NotAvailableBatchException e ){
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(null);  
    }
}
@GetMapping("/{id}")
public ResponseEntity<Batch> getBatchByName(@PathVariable String id){
Batch batch = service.getBatchByName(id);
return ResponseEntity.ok(batch);
}

@PostMapping("/withdrawbatch/{id}")
public ResponseEntity<Batch> withdrawBatchRework(@PathVariable String id){
    Batch batch = service.withdrawBatchRework(id);
    return ResponseEntity.ok(batch);
}

@PostMapping("/reworkbatch/{id}")
public ResponseEntity<String> reworkBatch(@PathVariable String id){
    String message = service.reworkBatch(id);
    return ResponseEntity.ok(message);
}





}
