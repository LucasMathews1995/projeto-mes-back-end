package com.lucasmes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lucasmes.DTO.ItemVendaDTO;
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

@PostMapping("/save")
public ResponseEntity<ItemVenda> saveItem( @RequestBody ItemVendaDTO dto){
    ItemVenda itemVenda = service.saveItemVendas(dto);

    if(itemVenda==null){
        return ResponseEntity.noContent().build();
    }
    return ResponseEntity.ok(itemVenda);
}

@PostMapping("/save-all")
public ResponseEntity<List<ItemVenda>> saveItens(@RequestBody List<ItemVendaDTO> dto){
    List<ItemVenda> itemVendas = service.saveAll(dto);
    if(itemVendas.isEmpty()){
       return ResponseEntity.noContent().build();

    }
    return ResponseEntity.ok(itemVendas);
}


    
}
