package com.lucasmes.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.lucasmes.entity.OrdemProducao;
import com.lucasmes.entity.OrdemVenda;

import java.util.List;



@Repository
public interface OrdemProducaoRepository extends JpaRepository<OrdemProducao,Long> {


    public Optional<OrdemProducao>  findByNumeroOP(String numeroOP);
    public Optional<List<OrdemProducao>> findByOrdemVenda(OrdemVenda ordemVenda);
}
