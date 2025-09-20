package com.lucasmes.mesprojeto.operation.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lucasmes.mesprojeto.entity.Operation;
import com.lucasmes.mesprojeto.exceptions.NotFoundOperationException;
import com.lucasmes.mesprojeto.exceptions.OutOfCapacityException;
import com.lucasmes.mesprojeto.operation.DTO.OperationDTOSender;
import com.lucasmes.mesprojeto.operation.DTO.OperationIdDTO;
import com.lucasmes.mesprojeto.operation.service.OperationService;

@RestController
@RequestMapping("/operation")
public class OperationController {

    @Autowired
    private OperationService service;


@GetMapping()
    public ResponseEntity<List<OperationDTOSender>> getAllOperation(){
        List<OperationDTOSender> operations = service.getAll();


        return ResponseEntity.ok(operations);
    }

    @GetMapping("/each")
    public ResponseEntity<Operation> getOperation(@RequestParam (required = false) String nameBatch,
    @RequestParam(required = false) String resourceName, 
    @RequestParam(required = false) String OpName ){
        Operation operation = service.getOperation(nameBatch, resourceName, OpName);

        return ResponseEntity.ok(operation);

    }
    @PostMapping("/setOperation")
    public ResponseEntity<OperationDTOSender> beginsOperation(@RequestBody OperationIdDTO dto){
            OperationDTOSender operation = service.setOperation(dto);


        try {
            return  ResponseEntity.status(HttpStatus.ACCEPTED).body(operation);
        } catch (NotFoundOperationException e) {
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        catch(OutOfCapacityException e ){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping("/finishOperation")
 public ResponseEntity<String> finishOperation(@RequestBody OperationIdDTO dto){
 
  String operation = service.finishOperation(dto);

try {
            return  ResponseEntity.status(HttpStatus.ACCEPTED).body(operation);
        } catch (NotFoundOperationException e) {
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

}

    @PostMapping("/initializeOperation")
    public ResponseEntity<String> initiateOperation(@RequestBody OperationIdDTO dto){
         String operation = service.initiateOperation(dto);

try {
            return  ResponseEntity.status(HttpStatus.ACCEPTED).body(operation);
        } catch (NotFoundOperationException e) {
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    


 @PostMapping("/setOperations")
    public ResponseEntity<List<Operation>> beginsOperations(@RequestBody List<OperationIdDTO> dto){
            List<Operation> operations = service.setOperations(dto);


        try {
            return  ResponseEntity.status(HttpStatus.ACCEPTED).body(operations);
        } catch (NotFoundOperationException e) {
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    
    
    
    }

    @PutMapping("quality/{batchNumber}/{resourceName}/{productionOrder}")
    public ResponseEntity<OperationDTOSender> putInQuality(@PathVariable String batchNumber,@PathVariable String resourceName, @PathVariable String productionOrder){
        OperationDTOSender sender = service.putInRework(batchNumber, resourceName, productionOrder);
        
       
        return ResponseEntity.ok(sender);
        
    }

    @DeleteMapping("delete/{batchNumber}/{resourceName}/{productionOrder}")
    public ResponseEntity<Void> deleteOperation(@PathVariable String batchNumber,@PathVariable String resourceName, @PathVariable String productionOrder){
        service.deleteOperation(batchNumber,resourceName,productionOrder);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<Void> deleteOperations(@RequestBody List<OperationIdDTO> dtos){
        service.deleteAllOperations(dtos);
        return ResponseEntity.noContent().build();

    }


   
    
}
