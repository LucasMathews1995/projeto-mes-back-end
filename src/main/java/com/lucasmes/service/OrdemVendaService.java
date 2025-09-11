package com.lucasmes.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lucasmes.entity.OrdemVenda;
import com.lucasmes.repository.OrdemVendaRepository;

@Service
public class OrdemVendaService {
@Autowired
private OrdemVendaRepository repository;





public List<OrdemVenda> listarOrdemVendas(){


    List<OrdemVenda> ordemVendas = repository.findAll();
    if(ordemVendas==null || ordemVendas.isEmpty()){
        return  Collections.emptyList();
    }
    return ordemVendas;

}



}
