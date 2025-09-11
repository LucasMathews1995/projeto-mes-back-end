package com.lucasmes.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lucasmes.DTO.LoteDTO;
import com.lucasmes.entity.Equipamento;
import com.lucasmes.entity.Lote;
import com.lucasmes.entity.OrdemProducao;
import com.lucasmes.exception.EquipamentoNotFoundException;
import com.lucasmes.exception.LoteNotFoundException;
import com.lucasmes.exception.OrdemVendaNotFoundException;
import com.lucasmes.repository.EquipamentoRepository;
import com.lucasmes.repository.LoteRepository;
import com.lucasmes.repository.OrdemProducaoRepository;

import jakarta.transaction.Transactional;

@Service
public class LoteService {


    @Autowired
    private LoteRepository repository;

    @Autowired 
    private OrdemProducaoRepository ordemProducaoRepository;
    @Autowired 
    private EquipamentoRepository equipamentoRepository;


 public List<Lote> listarLotes(){
        
        List<Lote> lotes = repository.findAll();
        if(lotes.isEmpty() || lotes== null){
            return Collections.emptyList();
        }
        return lotes;
    }

 public List<Lote> saveAllLotes(List<LoteDTO> dto){
        List<Lote> lotes = new ArrayList<>();
       for(LoteDTO lote : dto){
            OrdemProducao ordemProducao = ordemProducaoRepository
            .findByNumeroOP(lote.ordemProducao())
           .orElseThrow(()-> new OrdemVendaNotFoundException("Ordem de produção não encontrada"));

            Lote loteEach = new Lote(lote.numeroLote(),lote.peso(),lote.largura()
            ,lote.espessura(),ordemProducao);

            lotes.add(loteEach);
       }
       if(lotes.isEmpty() || lotes==null){
        return null;
       }

       return repository.saveAll(lotes);
        
        
    }


    public String atualizarProgresso(Long id, Double progresso){

        Lote lote = repository.findById(id).orElseThrow(()-> new LoteNotFoundException("Lote não encontrado"));
        lote.registrarAvanco(progresso);
        repository.save(lote);
        return String.format("O lote : %s foi atualizado com o progresso de %.2f" , lote.getNumeroLote(), lote.getProgresso());
    }

    public String transferirEquipamento(Long id, String equipamentoName) {
       Lote lote = repository.findById(id).orElseThrow(()-> new LoteNotFoundException("Lote não encontrado"));
       Equipamento equipamento = equipamentoRepository.findByName(equipamentoName).orElseThrow(()-> new EquipamentoNotFoundException("O equipamento não foi encontrado com esse nome") );
       lote.addEquipamento(equipamento);
       if(lote== null || equipamento==null){
        return "não há equipamento ou lote encontrado";
       }
       return String.format("O lote %s foi adicionado ao equipamento %s. ", lote.getNumeroLote(),equipamento.getName());
    }

    public Equipamento equipamentoAtualPeloLote(Long id) {
      Lote lote = repository.findById(id).orElseThrow(()-> new LoteNotFoundException("O equipamento não foi encontrado com esse nome") );

      Equipamento equipamento = lote.getEquipamentos().getLast();
        
      return equipamento;
      
    }
@Transactional
   public String repartirLote(Long id , double peso){
    Lote lote = repository.findById(id).orElseThrow(()-> new LoteNotFoundException("O equipamento não foi encontrado com esse nome"));
   List <Lote> lotes =  lote.repartirPeso(peso);
  repository.saveAll(lotes);
  repository.delete(lote);
  return String.format("Gerou dois lotes de %.1f toneladas e %.1f toneladas com os numeros do lote de %s e %s respectivamente", lotes.getFirst().getPeso(),lotes.getLast().getPeso(),lotes.getFirst().getNumeroLote(), lotes.getLast().getNumeroLote());


   }


    public Lote cancelarLote(Long id ){
          Lote lote = repository.findById(id).orElseThrow(()-> new LoteNotFoundException("O equipamento não foi encontrado com esse nome") );
        lote.cancelarLote();
       
       return repository.save(lote);
    }
    public Lote terminarLote(Long id){
        Lote lote = repository.findById(id).orElseThrow(()-> new LoteNotFoundException("O equipamento não foi encontrado com esse nome") );
        lote.finalizarLote();
        return repository.save(lote);
    }





}
