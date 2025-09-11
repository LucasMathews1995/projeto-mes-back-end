package com.lucasmes.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.lucasmes.DTO.OrdemProducao.OrdemProducaoDTO;
import com.lucasmes.entity.Lote;
import com.lucasmes.entity.OrdemProducao;
import com.lucasmes.entity.OrdemVenda;
import com.lucasmes.exception.OrdemProducaoNotFoundException;
import com.lucasmes.exception.OrdemVendaNotFoundException;
import com.lucasmes.repository.LoteRepository;
import com.lucasmes.repository.OrdemProducaoRepository;
import com.lucasmes.repository.OrdemVendaRepository;

import jakarta.transaction.Transactional;

@Service
public class OrdemProducaoService {
@Autowired
private OrdemProducaoRepository repository;

@Autowired
private OrdemVendaRepository ovRepository;

@Autowired
private LoteRepository lotesRepository;

 public List<OrdemProducao> listarOP(){
        
        List<OrdemProducao> listaOP = repository.findAll();
        if(listaOP.isEmpty() || listaOP== null){
            return Collections.emptyList();
        }
        return listaOP;
    }
    public List<OrdemProducao> salvarTodasOP(List<OrdemProducaoDTO> dtos){
        List<OrdemProducao> listaProducao = new ArrayList<>();
        for(OrdemProducaoDTO dto: dtos){  

            OrdemVenda ordemVenda = ovRepository.findByName(dto.ordemVenda()).orElseThrow(()-> new OrdemVendaNotFoundException("Não foi encontrada nenhuma ordem de venda"));
            OrdemProducao producao = new OrdemProducao(dto.numeroOP(),dto.material(),ordemVenda);
            listaProducao.add(producao);
        
        }
        return repository.saveAll(listaProducao);
      
       
    }
    public OrdemProducao salvarOP(OrdemProducaoDTO dto) {
  OrdemVenda ordemVenda = ovRepository.findByName(dto.ordemVenda()).orElseThrow(()-> new OrdemVendaNotFoundException("Não foi encontrada nenhuma ordem de venda"));
            OrdemProducao producao = new OrdemProducao(dto.numeroOP(),dto.material(),ordemVenda);
            return repository.save(producao);
        
    }


    public List<OrdemProducao> buscarPorOrdemVenda(String ovName) {
        OrdemVenda ordemVenda = ovRepository.findByName(ovName).orElseThrow(()-> new OrdemVendaNotFoundException("Não foi encontrada nenhuma ordem de venda"));
        List<OrdemProducao> listaProducao = repository.findByOrdemVenda(ordemVenda).orElseThrow(()-> new OrdemVendaNotFoundException("não existe nenhuma OV associada  essa OP"));
        if(listaProducao==null || listaProducao.isEmpty()){
            return null;
        }
        return listaProducao;

    }
    @Transactional
    public OrdemProducao mudarOP(Long id, OrdemProducao opAtualizada) {
       
        OrdemProducao op  = repository.findById(id).orElseThrow(()-> new OrdemVendaNotFoundException("Ordem de produção não encontrada"));

        op.setLotes(opAtualizada.getLotes());
        op.setMaterial(opAtualizada.getMaterial());
        op.setNumeroOP(opAtualizada.getNumeroOP());
        op.setOrdemVenda(opAtualizada.getOrdemVenda());
        op.setQuantidade(opAtualizada.getQuantidade());
        
       
       return repository.save(op);
    

    }
    @Transactional
    public OrdemProducao adicionarLotes(Long opId, List<Long> idLotes){
        OrdemProducao op = repository.findById(opId).orElseThrow(()-> new OrdemProducaoNotFoundException("Não foi encontrada nenhuma Ordem de Produção com esse id"));
        List<Lote> lotes =lotesRepository.findAllById(idLotes);
     
     for(Lote l : lotes)
     {       op.addLotes(l);
    
    }
    return repository.save(op);


    }
    @Transactional
public OrdemProducao deletarLotes(Long opId, List<Long> idLotes){
        OrdemProducao op = repository.findById(opId).orElseThrow(()-> new OrdemProducaoNotFoundException("Não foi encontrada nenhuma Ordem de Produção com esse id"));
        List<Lote> lotes =lotesRepository.findAllById(idLotes);
     
     for(Lote l : lotes)
     {       op.removeLotes(l);
    
    }
    return repository.save(op);


    }
  @Transactional
    public OrdemProducao buscarPorId(Long id ){

        OrdemProducao  op = repository.findById(id).orElseThrow(()-> new OrdemProducaoNotFoundException(("Não foi encontrada nenhuma Ordem de Produção com esse id")));


        return op;
    }
    
  public void deletarOrdemProducao(Long id) {
    
    repository.deleteById(id);
   
  }


 

}
