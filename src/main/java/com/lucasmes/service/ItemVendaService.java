package com.lucasmes.service;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.lucasmes.DTO.ItemVendaDTO;
import com.lucasmes.entity.ItemVenda;
import com.lucasmes.entity.OrdemVenda;
import com.lucasmes.exception.OrdemVendaNotFoundException;
import com.lucasmes.repository.ItemVendaRepository;
import com.lucasmes.repository.OrdemVendaRepository;

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


    public List<ItemVenda> saveAll(List<ItemVendaDTO> dto){
        List<ItemVenda> itemVendas = new ArrayList<>();
       for(ItemVendaDTO iv : dto){
           OrdemVenda ordemVenda = ordemVendaRepository.findByNumeroOV(iv.ordemVenda())
           .orElseThrow(()-> new OrdemVendaNotFoundException("Ordem de venda não encontrada"));
            ItemVenda itemVenda = new ItemVenda(ordemVenda,iv.quantidade(),iv.precoUnitario());
            itemVendas.add(itemVenda);
       }
       if(itemVendas.isEmpty() || itemVendas==null){
        return null;
       }
       return repository.saveAll(itemVendas);
        
        
    }



}
