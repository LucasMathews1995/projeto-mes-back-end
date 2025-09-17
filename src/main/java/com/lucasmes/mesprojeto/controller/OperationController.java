package com.lucasmes.mesprojeto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.lucasmes.mesprojeto.entity.Operation;
import com.lucasmes.mesprojeto.service.OperationService;

@RestController
@RequestMapping("operation")
public class OperationController {

    @Autowired
    private OperationService service;


@GetMapping()
    public ResponseEntity<List<Operation>> getAllOperation(){
        List<Operation> operations = service.getAll();


        return ResponseEntity.ok(operations);
    }

    @GetMapping("/each")
    public ResponseEntity<Operation> getOperation(@RequestParam (required = false) String nameBatch,
    @RequestParam(required = false) String resourceName, 
    @RequestParam(required = false) String OpName ){
        Operation operation = service.getOperation(nameBatch, resourceName, OpName);

        return ResponseEntity.ok(operation);

    }

}
