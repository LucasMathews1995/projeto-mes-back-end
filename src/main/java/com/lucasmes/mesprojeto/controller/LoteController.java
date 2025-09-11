package com.lucasmes.mesprojeto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lucasmes.mesprojeto.DTO.LoteDTO;
import com.lucasmes.mesprojeto.entity.Equipamento;
import com.lucasmes.mesprojeto.entity.Lote;
import com.lucasmes.mesprojeto.service.LoteService;

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
@PostMapping("/save-all")
public ResponseEntity<List<Lote>> saveAllLotes(List<LoteDTO> dto){
    List<Lote>  lotes = service.saveAllLotes(dto);
    if(lotes.isEmpty()){
         return ResponseEntity.noContent().build();
    }
    return ResponseEntity.ok(lotes);
   



}

@PutMapping("{id}/progresso")
public ResponseEntity<String> atualizarProgresso(@PathVariable Long id, @RequestParam Double progresso){
String message = service.atualizarProgresso(id,progresso);

return new ResponseEntity<>(message,HttpStatus.OK);
}
@PutMapping("{id}/transferirEquipamento")
public ResponseEntity<String> transferirEquipamento(@PathVariable Long id , @RequestParam String equipamento){

    String message = service.transferirEquipamento(id,equipamento);

    return ResponseEntity.ok(message);
}


@GetMapping("/{id}/equipamentoAtual")
public ResponseEntity<Equipamento> equipamentoAtualPeloLote(@PathVariable Long id){
Equipamento equipamento = service.equipamentoAtualPeloLote(id);
return ResponseEntity.ok(equipamento);
}
@PostMapping("/{id}/terminarLote")
public ResponseEntity<Lote> terminarLote(@PathVariable Long id ){
    Lote lote = service.terminarLote(id);


    return ResponseEntity.ok(lote);
}

@PostMapping("/{id}/repartirPeso")
public ResponseEntity<String> repartirPeso(@PathVariable Long id , @RequestParam double peso){
    String mensagem = service.repartirLote(id, peso);
    return new ResponseEntity<>(mensagem,HttpStatus.ACCEPTED);
    

}
@PostMapping("/{id}/cancelarLote")
public ResponseEntity<String> cancelarLote(@PathVariable Long id){
    Lote lote = service.cancelarLote(id);

    String message = String.format("Lote : %s foi cancelado", lote.getNumeroLote());

    return ResponseEntity.ok(message);
}





}
