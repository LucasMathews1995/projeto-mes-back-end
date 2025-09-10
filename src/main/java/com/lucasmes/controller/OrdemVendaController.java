package com.lucasmes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lucasmes.entity.OrdemVenda;
import com.lucasmes.service.OrdemVendaService;

@RestController
@RequestMapping("/ordem-venda")
public class OrdemVendaController {


    @Autowired
    private OrdemVendaService service;





    @GetMapping()
    public ResponseEntity<List<OrdemVenda>> listarOrdemVendas(){
    
        
List<OrdemVenda> listaOrdemVendas = service.listarOrdemVendas();
if(listaOrdemVendas.isEmpty()){return ResponseEntity.noContent().build();}
         

           return ResponseEntity.ok(listaOrdemVendas);
    }
  

}
