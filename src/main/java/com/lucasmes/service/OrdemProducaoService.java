package com.lucasmes.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.lucasmes.entity.OrdemProducao;
import com.lucasmes.repository.OrdemProducaoRepository;

@Service
public class OrdemProducaoService {
@Autowired
private OrdemProducaoRepository repository;


 public List<OrdemProducao> listarOP(){
        
        List<OrdemProducao> listaOP = repository.findAll();
        if(listaOP.isEmpty() || listaOP== null){
            return Collections.emptyList();
        }
        return listaOP;
    }

 

}
