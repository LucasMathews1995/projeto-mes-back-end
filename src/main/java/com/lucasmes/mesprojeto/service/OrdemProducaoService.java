package com.lucasmes.mesprojeto.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.lucasmes.mesprojeto.DTO.OrdemProducao.OrdemProducaoDTO;
import com.lucasmes.mesprojeto.DTO.OrdemProducao.OrdemProducaoDTOSender;
import com.lucasmes.mesprojeto.entity.Lote;
import com.lucasmes.mesprojeto.entity.OrdemProducao;
import com.lucasmes.mesprojeto.entity.OrdemVenda;
import com.lucasmes.mesprojeto.exception.OrdemProducaoNotFoundException;
import com.lucasmes.mesprojeto.exception.OrdemVendaNotFoundException;
import com.lucasmes.mesprojeto.repository.LoteRepository;
import com.lucasmes.mesprojeto.repository.OrdemProducaoRepository;
import com.lucasmes.mesprojeto.repository.OrdemVendaRepository;

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
    public List<OrdemProducaoDTOSender> salvarTodasOP(List<OrdemProducaoDTO> dtos){
        List<OrdemProducao> listaProducao = new ArrayList<>();
         List<OrdemProducaoDTOSender> senders = new ArrayList<>();
        for(OrdemProducaoDTO dto: dtos){  
 Random random = new Random();
    Integer numeroAleatorio=   random.nextInt(100000000);
  StringBuilder st =new StringBuilder();
  String messagefinal = st.append("OP").append(numeroAleatorio.toString()).toString();
            OrdemVenda ordemVenda = ovRepository.findByCliente(dto.clienteOrdemVenda()).orElseThrow(()-> new OrdemVendaNotFoundException("Não foi encontrada nenhuma ordem de venda"));
            OrdemProducao producao = new OrdemProducao(messagefinal,dto.quantidade(),dto.material(),ordemVenda);
            OrdemProducaoDTOSender  sender= new OrdemProducaoDTOSender(messagefinal,producao.getQuantidade(),producao.getMaterial(),producao.getOrdemVenda().getNumeroOV());
            listaProducao.add(producao);
            senders.add(sender);
        }
      
       
       repository.saveAll(listaProducao);
        return senders;
       
    }
    public OrdemProducaoDTOSender salvarOP(OrdemProducaoDTO dto) {
  OrdemVenda ordemVenda = ovRepository.findByCliente(dto.clienteOrdemVenda()).orElseThrow(()-> new OrdemVendaNotFoundException("Não foi encontrada nenhuma ordem de venda"));
 Random random = new Random();
    Integer numeroAleatorio=   random.nextInt(100000000);
  StringBuilder st =new StringBuilder();
  String messagefinal = st.append("OP").append(numeroAleatorio.toString()).toString();
            OrdemProducao producao = new OrdemProducao(messagefinal,dto.quantidade(),dto.material(),ordemVenda);
repository.save(producao);
        return new   OrdemProducaoDTOSender(producao.getNumeroOP(),producao.getQuantidade(),producao.getMaterial(),producao.getOrdemVenda().getNumeroOV());
           
        
    }


    public List<OrdemProducaoDTOSender> buscarPorOrdemVenda(String ovName) {
        OrdemVenda ordemVenda = ovRepository.findByCliente(ovName).orElseThrow(()-> new OrdemVendaNotFoundException("Não foi encontrada nenhuma ordem de venda"));
        List<OrdemProducao> listaProducao = repository.findByOrdemVenda(ordemVenda).orElseThrow(()-> new OrdemVendaNotFoundException("não existe nenhuma OV associada  essa OP"));
        List<OrdemProducaoDTOSender> sender = new ArrayList<>();
        for (OrdemProducao ordemProducao : listaProducao) {
          OrdemProducaoDTOSender op = new OrdemProducaoDTOSender(ordemProducao.getNumeroOP(),ordemProducao.getQuantidade(),ordemProducao.getMaterial(),ordemProducao.getOrdemVenda().getCliente());
          sender.add(op);
        }
        
        
        
        if(listaProducao==null || listaProducao.isEmpty()){
            return null;
        }
        return sender;

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
