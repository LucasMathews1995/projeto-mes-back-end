package com.lucasmes.mesprojeto.service;


import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lucasmes.mesprojeto.DTO.OrdemProducao.OrdemProducaoDTOOv;
import com.lucasmes.mesprojeto.DTO.ordemVenda.OrdemVendaDTO;
import com.lucasmes.mesprojeto.entity.OrdemProducao;
import com.lucasmes.mesprojeto.entity.OrdemVenda;
import com.lucasmes.mesprojeto.exception.OrdemProducaoNotFoundException;
import com.lucasmes.mesprojeto.exception.OrdemVendaNotFoundException;
import com.lucasmes.mesprojeto.repository.OrdemProducaoRepository;
import com.lucasmes.mesprojeto.repository.OrdemVendaRepository;

import jakarta.transaction.Transactional;

@Service
public class OrdemVendaService {

@Autowired
private OrdemVendaRepository repository;
@Autowired
private OrdemProducaoRepository opRepository;





public List<OrdemVenda> listarOrdemVendas(){


    List<OrdemVenda> ordemVendas = repository.findAll();
    if(ordemVendas==null || ordemVendas.isEmpty()){
        return  Collections.emptyList();
    }
    return ordemVendas;

}





public OrdemVenda buscarOrdemVenda(Long id) {

    OrdemVenda ov  = repository.findById(id).orElseThrow(()-> new OrdemVendaNotFoundException("Ordem de Venda não encontrada"));
    return ov;
}
@Transactional
public OrdemVenda salvarOrdemVenda(OrdemVendaDTO dto){
    OrdemVenda ov = new OrdemVenda();
    ov.setCliente(dto.cliente());
    ov.setNumeroOV(dto.numeroOV());
   
    return repository.save(ov);
}
@Transactional
public List<OrdemVenda> salvarListaOrdemVenda(List<OrdemVendaDTO> dtos){
    

   List<OrdemVenda> ovs=    dtos.stream().map(dto-> {
        OrdemVenda ov = new OrdemVenda();
        ov.setCliente(dto.cliente());
        ov.setNumeroOV(dto.numeroOV());
        return ov;
    }).collect(Collectors.toList());


   if(ovs.isEmpty() || ovs==null){
    throw new OrdemVendaNotFoundException("Ordem de Venda não encontradas");

   }
   return repository.saveAll(ovs);

}





public String deletarOrdemVenda(Long id) {
    OrdemVenda ov  =repository.findById(id).orElseThrow(()-> new OrdemVendaNotFoundException("Ordem de venda não encontrada"));
 if(ov==null){
    return "Nenhuma Ordem de venda encontrada";
 }
   repository.delete(ov);
  
   return String.format("A ordem de venda %s foi deletada com sucesso ", ov.getNumeroOV());
}
public String deletarOrdensVendas(List<Long> ids){
    List<OrdemVenda> ovs =repository.findAllById(ids);
    StringBuilder messageCombo= new StringBuilder();
    if(ovs.isEmpty()|| ovs==null){
        return "Nenhuma Ordem de venda foi encontrada";
    }
    
        repository.deleteAll(ovs);
        for (OrdemVenda ordemVenda : ovs) {
              String message =String.format("Ordem de Venda %s deletada%n", ordemVenda.getNumeroOV());  
              messageCombo.append(message).append("/n");
             
        }
        return messageCombo.toString();
    

}










public String atualizarOrdemPorNome(Long id) {
      OrdemVenda ov = repository.findById(id).orElseThrow(()-> new OrdemVendaNotFoundException("Ordem de venda não encontrada")); 
      Random random = new Random();
      Integer  i =random.nextInt(0);
      StringBuilder st = new StringBuilder();
      st.append("alterada").append(i.toString());  
   String nameOVold = ov.getNumeroOV();
   ov.setNumeroOV(st.toString());
   repository.save(ov);
  return String.format("A ordem foi atualizada de %s para %s", nameOVold,ov.getNumeroOV());

}

public String gerarOrdemProducao(Long id , OrdemProducaoDTOOv op ){
     OrdemVenda ov = repository.findById(id).orElseThrow(()-> new OrdemVendaNotFoundException("Ordem de venda não encontrada")); 
     OrdemProducao opAtualizada = opRepository.findByNumeroOP(op.numeroOP()).orElseThrow(()-> new OrdemProducaoNotFoundException("OP não encontrada , tente outro nome"));
     ov.addOP(opAtualizada);
     
     return String.format("Ordem de Produção : %s foi vinculada á Ordem de Venda %s ", opAtualizada.getNumeroOP(),ov.getNumeroOV());

}

public String gerarOrdensProducao(Long id, List<OrdemProducaoDTOOv> dtos){
     OrdemVenda ov = repository.findById(id).orElseThrow(()-> new OrdemVendaNotFoundException("Ordem de venda não encontrada")); 
     StringBuilder st =new StringBuilder();
     
     for(OrdemProducaoDTOOv dto : dtos){
        OrdemProducao op = new OrdemProducao();
        op.setMaterial(dto.material());
        op.setNumeroOP(dto.numeroOP());
        op.setOrdemVenda(ov);
        String message = String.format("Ordem de Produção adicionada : %s", op.getNumeroOP());
        st.append(message).append("%n");
         ov.addOP(op);
       
     }
     st.append("Na ordem de Venda" + ov.getNumeroOV());


     return st.toString();
     
    

}



}
