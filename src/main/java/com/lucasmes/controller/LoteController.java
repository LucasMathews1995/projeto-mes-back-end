package com.lucasmes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lucasmes.entity.Lote;
import com.lucasmes.service.LoteService;

@RestController
@RequestMapping("/lote")
public class LoteController {



    @Autowired
    private LoteService service;




    @GetMapping()
public ResponseEntity<List<Lote>> listarLotes(){
    List<Lote> lotes = service.listarLotes();

    if(lotes.isEmpty()){
return ResponseEntity.noContent().build();
    }
    return ResponseEntity.ok(lotes);
}

}
