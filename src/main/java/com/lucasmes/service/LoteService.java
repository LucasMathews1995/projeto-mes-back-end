package com.lucasmes.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lucasmes.entity.Lote;
import com.lucasmes.repository.LoteRepository;

@Service
public class LoteService {


    @Autowired
    private LoteRepository repository;



 public List<Lote> listarLotes(){
        
        List<Lote> lotes = repository.findAll();
        if(lotes.isEmpty() || lotes== null){
            return Collections.emptyList();
        }
        return lotes;
    }





}
