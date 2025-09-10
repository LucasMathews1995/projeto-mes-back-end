package com.lucasmes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.lucasmes.entity.OrdemProducao;
import com.lucasmes.service.OrdemProducaoService;

@RestController
@RequestMapping("/ordem-producao")
public class OrdemProducaoController {




    @Autowired
    private OrdemProducaoService service;



    @GetMapping()
public ResponseEntity<List<OrdemProducao>> listarOrdemProducao(){
    List<OrdemProducao> ordensProducao = service.listarOP();

    if(ordensProducao.isEmpty()){
return ResponseEntity.noContent().build();
    }
    return ResponseEntity.ok(ordensProducao);
}



}
