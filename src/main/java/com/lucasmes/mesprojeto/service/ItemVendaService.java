package com.lucasmes.mesprojeto.service;



import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.lucasmes.mesprojeto.DTO.ItemVendaDTO.ItemVendaDTO;
import com.lucasmes.mesprojeto.DTO.ItemVendaDTO.ItemVendaDTOSender;
import com.lucasmes.mesprojeto.entity.ItemVenda;
import com.lucasmes.mesprojeto.entity.OrdemVenda;
import com.lucasmes.mesprojeto.exception.OrdemVendaNotFoundException;
import com.lucasmes.mesprojeto.repository.ItemVendaRepository;
import com.lucasmes.mesprojeto.repository.OrdemVendaRepository;

import jakarta.transaction.Transactional;

@Service
public class ItemVendaService {


@Autowired
    private ItemVendaRepository repository;


    @Autowired
    private OrdemVendaRepository ordemVendaRepository;





    @Transactional
    public ItemVenda saveItemVendas(ItemVendaDTO dto){
     OrdemVenda ordemVenda =   ordemVendaRepository.findByNumeroOV(dto.ordemVenda()).orElseThrow(()-> new OrdemVendaNotFoundException("Ordem de venda não encontrada"));
        ItemVenda itemVenda = new ItemVenda(ordemVenda,dto.quantidade(),dto.precoUnitario());
        

        return repository.save(itemVenda);
    }


    public List<ItemVenda> listarItemVendas(){
        
        List<ItemVenda> itemVendas = repository.findAll();
        if(itemVendas.isEmpty() || itemVendas== null){
            return Collections.emptyList();
        }
        return itemVendas;
    }

@Transactional
    public List<ItemVendaDTOSender> saveAll(String numeroOv,List<ItemVendaDTO> dto){
        OrdemVenda ordemVenda = ordemVendaRepository.findByNumeroOV(numeroOv)
           .orElseThrow(()-> new OrdemVendaNotFoundException("Ordem de venda não encontrada"));
        List<ItemVenda> itemVendas =dto.stream().map(it->{
            
              ItemVenda itemVenda = new ItemVenda();
              itemVenda.setOrdemVenda(ordemVenda);
              itemVenda.setPrecoUnitario(it.precoUnitario());
              itemVenda.setQuantidade(it.quantidade());
              
              return itemVenda;
        }).collect(Collectors.toList());
 if(itemVendas.isEmpty() || itemVendas==null){
    throw new OrdemVendaNotFoundException("Ordem de Venda não encontradas");

   }   
   List<ItemVendaDTOSender> itemVendaDTOSenders = new ArrayList<>();
   
 repository.saveAll(itemVendas);
 for (ItemVenda itemVenda : itemVendas) {
    ItemVendaDTOSender itemVendaDTOSender = new ItemVendaDTOSender(itemVenda.getId(),itemVenda.getOrdemVenda().getCliente(),itemVenda.getQuantidade(),itemVenda.getPrecoUnitario());
    itemVendaDTOSenders.add(itemVendaDTOSender);
   }
 return itemVendaDTOSenders;

       
        
    }



}
