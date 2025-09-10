package com.lucasmes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lucasmes.entity.ItemVenda;
import com.lucasmes.service.ItemVendaService;

@RestController
@RequestMapping("/itemvenda")
public class ItemVendaController {


@Autowired
private ItemVendaService service;


@GetMapping()
public ResponseEntity<List<ItemVenda>> listarItemVendas(){
    List<ItemVenda> itemVendas = service.listarItemVendas();

    if(itemVendas.isEmpty()){
return ResponseEntity.noContent().build();
    }
    return ResponseEntity.ok(itemVendas);
}



    
}
