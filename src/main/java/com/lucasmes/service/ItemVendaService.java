package com.lucasmes.service;


import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.lucasmes.DTO.ItemVendaDTO;
import com.lucasmes.entity.ItemVenda;
import com.lucasmes.entity.OrdemVenda;
import com.lucasmes.exception.OrdemVendaException;
import com.lucasmes.repository.ItemVendaRepository;
import com.lucasmes.repository.OrdemVendaRepository;

@Service
public class ItemVendaService {


@Autowired
    private ItemVendaRepository repository;


    @Autowired
    private OrdemVendaRepository ordemVendaRepository;






    public String saveItemVendas(ItemVendaDTO dto){
     OrdemVenda ordemVenda =   ordemVendaRepository.findByNumeroOV(dto.ordemVenda()).orElseThrow(()-> new OrdemVendaException("Ordem de venda não encontrada"));
        ItemVenda itemVenda = new ItemVenda(ordemVenda,dto.quantidade(),dto.precoUnitario());
        repository.save(itemVenda);

        return"O item de vendas foi criada";
    }


    public List<ItemVenda> listarItemVendas(){
        
        List<ItemVenda> itemVendas = repository.findAll();
        if(itemVendas.isEmpty() || itemVendas== null){
            return Collections.emptyList();
        }
        return itemVendas;
    }




}
