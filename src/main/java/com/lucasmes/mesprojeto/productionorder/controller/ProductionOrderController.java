package com.lucasmes.mesprojeto.productionorder.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lucasmes.mesprojeto.productionorder.DTO.*;
import com.lucasmes.mesprojeto.entity.ProductionOrder;
import com.lucasmes.mesprojeto.exceptions.NotAvailableProductionOrderException;
import com.lucasmes.mesprojeto.exceptions.NotFoundProductionOrderException;

import com.lucasmes.mesprojeto.productionorder.service.*;

@RestController
@RequestMapping("production")
public class ProductionOrderController {

    
@Autowired
private ProductionOrderService service;


@PostMapping("/save")
public ResponseEntity<ProductionOrder> saveProductionOrder(@RequestBody ProductionOrderDTO dto){
ProductionOrder po =  service.saveProductionOrder(dto);
try{
return ResponseEntity.ok(po);
}catch(NotAvailableProductionOrderException e)
{
    e.printStackTrace();
    return ResponseEntity.ok(null);

}catch(NotFoundProductionOrderException e )
{
    e.printStackTrace();
    return ResponseEntity.ok(null);
}

}

@PostMapping("/saveall")
public ResponseEntity<List<ProductionOrder>> saveAllProductionOrder(@RequestBody List<ProductionOrderDTO> dto){
    List<ProductionOrder> poList = service.saveAllProductionOrder(dto);

    try{
return ResponseEntity.ok(poList);
}catch(NotAvailableProductionOrderException e)
{
    e.printStackTrace();
    return ResponseEntity.ok(null);

}catch(NotFoundProductionOrderException e )
{
    e.printStackTrace();
    return ResponseEntity.ok(null);
}
    
}
@GetMapping()
public ResponseEntity<List<ProductionOrder>> listAllProductionOrder(){
List<ProductionOrder> poList = service.listAll();
return ResponseEntity.ok(poList);
}
@GetMapping("/{id}")
public ResponseEntity<ProductionOrder> findProductionOrderById(@PathVariable String id){
    ProductionOrder po = service.findById(id);

    return ResponseEntity.ok(po);
}









}
