package com.lucasmes.mesprojeto.entity;

import java.util.ArrayList;
import java.util.List;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Entity
@Table(name ="tb_ordemproducao")
@Getter
@Setter
@NoArgsConstructor
public class OrdemProducao {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
@Column(unique = true)
private String numeroOP;
@Column(nullable = false)
private Integer quantidade;
@Column(nullable = false)
private String material;

@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "ov_id")
private OrdemVenda ordemVenda;


@OneToMany(mappedBy = "ordemProducao",cascade = CascadeType.ALL)
private List<Lote> lotes;




public OrdemProducao(String numeroOP,Integer quantidade,String material,OrdemVenda ordemVenda){
this.numeroOP=numeroOP;
this.material=material;
this.ordemVenda=ordemVenda;
lotes= new ArrayList<>();
this.quantidade=lotes.size();
this.quantidade = quantidade;
}
public OrdemProducao(String numeroOP,Integer quantidade,String material){
this.numeroOP=numeroOP;
this.material=material;

lotes= new ArrayList<>();
this.quantidade=lotes.size();
this.quantidade = quantidade;
}


public void addLotes(Lote lote){
    this.lotes.add(lote);
    lote.setOrdemProducao(this);
}
public void removeLotes(Lote lote){
    this.lotes.remove(lote);
    lote.setOrdemProducao(null);
}



public String verificarStatus (){
    if(lotes==null || lotes.isEmpty()){
       return "Nenhum lote retornado";
    }
    boolean todosFinalizados = true;
    boolean algumIniciado = false;
    boolean algumProcessando = false;

    for(Lote lote : lotes){

        if(lote.getStatus()==StatusLote.INICIADO){
            algumIniciado= true;
        }
        if(lote.getStatus()==StatusLote.PROCESSANDO){
             algumProcessando= true;
        }
        if(lote.getStatus()== StatusLote.FINALIZADO){
            todosFinalizados = false;
        }
        
    }
    if(todosFinalizados){
        return "Todos finalizados";

    }
    else if(algumIniciado ||algumProcessando){
        return "Processando";

    }
    else {return "Nenhum lote iniciado ";}

    
}
 



}
