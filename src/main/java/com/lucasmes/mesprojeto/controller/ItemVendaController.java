package com.lucasmes.mesprojeto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lucasmes.mesprojeto.DTO.ItemVendaDTO.ItemVendaDTO;
import com.lucasmes.mesprojeto.DTO.ItemVendaDTO.ItemVendaDTOSender;
import com.lucasmes.mesprojeto.entity.ItemVenda;
import com.lucasmes.mesprojeto.service.ItemVendaService;

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

@PostMapping("{numeroOV}/save-all")
public ResponseEntity<List<ItemVendaDTOSender>> saveItens(@PathVariable String numeroOV,@RequestBody List<ItemVendaDTO> dto){
    List<ItemVendaDTOSender> itemVendas = service.saveAll(numeroOV,dto);
    if(itemVendas.isEmpty()){
       return ResponseEntity.noContent().build();

    }
    return ResponseEntity.ok(itemVendas);
}


    
}
