package com.lucasmes.mesprojeto.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.lucasmes.mesprojeto.DTO.OrdemProducao.OrdemProducaoDTO;
import com.lucasmes.mesprojeto.DTO.OrdemProducao.OrdemProducaoDTOOv;
import com.lucasmes.mesprojeto.DTO.ordemVenda.OrdemVendaDTO;
import com.lucasmes.mesprojeto.DTO.ordemVenda.OrdemVendaIdDTO;
import com.lucasmes.mesprojeto.entity.OrdemVenda;
import com.lucasmes.mesprojeto.service.OrdemVendaService;

@RestController
@RequestMapping("/ordemvenda")
public class OrdemVendaController {


    @Autowired
    private OrdemVendaService service;





    @GetMapping
    public ResponseEntity<List<OrdemVenda>> listarOrdemVendas(){
    
        
List<OrdemVenda> listaOrdemVendas = service.listarOrdemVendas();
if(listaOrdemVendas.isEmpty()){return ResponseEntity.noContent().build();}
         

           return ResponseEntity.ok(listaOrdemVendas);
    }



  @GetMapping("/{id}")
  public ResponseEntity<OrdemVenda> buscarOrdemVenda(@PathVariable Long id ){

    OrdemVenda ov = service.buscarOrdemVenda(id);
if(ov== null){
    return ResponseEntity.notFound().build();
}
    return ResponseEntity.ok(ov);
  }



  @PostMapping("/salvar")
  public ResponseEntity<OrdemVenda> salvarOrdemVenda(@RequestBody OrdemVendaDTO dto){
    OrdemVenda ov = service.salvarOrdemVenda(dto);
return ResponseEntity.ok(ov);

  }

  @PostMapping("/salvarTodos")
  public ResponseEntity<List<OrdemVenda>> salvarTodasOrdemVendas(@RequestBody List<OrdemVendaDTO> dto){

    List<OrdemVenda> ovs= service.salvarListaOrdemVenda(dto);

    if(ovs.isEmpty() || ovs==null){
        return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(ovs);

  }

  @DeleteMapping("/{id}/deletarOrdemVenda")
  public ResponseEntity<String> deletarOrdemVenda(@PathVariable Long id){

String message =  service.deletarOrdemVenda(id);
 return ResponseEntity.ok(message);

  }

  @DeleteMapping("/deletarOrdemVenda")
  public ResponseEntity<String> deletarOVs(@RequestBody OrdemVendaIdDTO dto){
   String messages = service.deletarOrdensVendas(dto.ids());

   return ResponseEntity.ok(messages);
  }
  
  @PatchMapping("/{id}")
  public ResponseEntity<String> atualizarNome(@PathVariable Long id ){
    String message = service.atualizarOrdemPorNome(id);
return ResponseEntity.ok(message);
  }



  @PostMapping("/{id}/adicionarOP")
  public ResponseEntity<String> gerarOP(@PathVariable Long id , @RequestBody OrdemProducaoDTOOv op){

    String message = service.gerarOrdemProducao(id,op);


    return ResponseEntity.ok(message);
  }

  @PostMapping("/{id}/adicionarOPs")
public ResponseEntity<String> gerarOps (@PathVariable Long id, @RequestBody List<OrdemProducaoDTOOv>dtos){
    String message= service.gerarOrdensProducao(id, dtos);
    return ResponseEntity.ok(message);
}







}
