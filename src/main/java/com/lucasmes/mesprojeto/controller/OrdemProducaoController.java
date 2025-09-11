package com.lucasmes.mesprojeto.controller;

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

import org.springframework.web.bind.annotation.RestController;

import com.lucasmes.mesprojeto.DTO.OrdemProducao.AdicionarLotesDTO;
import com.lucasmes.mesprojeto.DTO.OrdemProducao.DeletarLotesDTO;
import com.lucasmes.mesprojeto.DTO.OrdemProducao.OrdemProducaoDTO;
import com.lucasmes.mesprojeto.entity.OrdemProducao;
import com.lucasmes.mesprojeto.service.OrdemProducaoService;

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
@PostMapping("/save-all")
public ResponseEntity<List<OrdemProducao>> salvarTodasOP(List<OrdemProducaoDTO> dto){
List<OrdemProducao> ordensProducao = service.salvarTodasOP(dto);


if(ordensProducao.isEmpty() || ordensProducao==null){
    return ResponseEntity.noContent().build();


}
return ResponseEntity.ok(ordensProducao);
}


@PostMapping("/save")
public ResponseEntity<OrdemProducaoDTO> salvarOP(@RequestBody OrdemProducaoDTO dto){
    OrdemProducaoDTO op = service.salvarOP(dto);


      return new ResponseEntity<>(op, HttpStatus.CREATED);
    
}


@GetMapping("buscarOV/{ovName}")
public ResponseEntity<List<OrdemProducao>> buscarPorOV(@PathVariable String ovName){

    List<OrdemProducao> listProducao = service.buscarPorOrdemVenda(ovName);
    if(listProducao==null ||listProducao.isEmpty()){
        return ResponseEntity.noContent().build();
    }
    return ResponseEntity.ok(listProducao);
}

@PutMapping("/mudarOP/{id}")
public ResponseEntity<OrdemProducao> mudarOP(@PathVariable Long id, @RequestBody OrdemProducao opAtualizada){
    OrdemProducao producao = service.mudarOP(id,opAtualizada);


    return ResponseEntity.ok(producao);

}
@PostMapping("/{opIds}/adicionarLotes")
public ResponseEntity<OrdemProducao> adicionarLotes(@PathVariable Long opIds, @RequestBody AdicionarLotesDTO adicionarLotes)
{

    OrdemProducao op = service.adicionarLotes(opIds, adicionarLotes.lotesIds());

    return ResponseEntity.ok(op);
}
@DeleteMapping("/{opIds}/deletarLotes")
public ResponseEntity<OrdemProducao> removerLotes(@PathVariable Long opIds, @RequestBody DeletarLotesDTO removerLotes){
    OrdemProducao op = service.deletarLotes(opIds,removerLotes.deletarIds());
    return ResponseEntity.ok(op);
}
@GetMapping("/{id}")
public ResponseEntity<OrdemProducao> buscarPorId(@PathVariable Long id) {
    OrdemProducao op = service.buscarPorId(id);
    
    
    return ResponseEntity.ok(op);
}
@DeleteMapping("/{id}")
public ResponseEntity<Void> deletar(@PathVariable Long id){
    service.deletarOrdemProducao(id);
        return ResponseEntity.noContent().build();
}




}
