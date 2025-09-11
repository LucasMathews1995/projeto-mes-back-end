package com.lucasmes.entity;

import java.util.ArrayList;
import java.util.List;

import com.lucasmes.DTO.OrdemProducao.OrdemProducaoDTO;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name= "tb_ordem-venda")
@Getter
@Setter
@NoArgsConstructor
public class OrdemVenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(nullable = false)
    private String cliente;
    @NonNull
    @Column(nullable = false)
    private String numeroOV;

    @OneToMany(mappedBy = "ordemVenda", cascade = CascadeType.ALL)
    private List<OrdemProducao> ordensProducao;

    @OneToMany(mappedBy = "ordemVenda",cascade = CascadeType.ALL)
    private List<ItemVenda> itemVendas;


    public OrdemVenda (String cliente, String numeroOV){
        this.cliente=cliente;
        this.numeroOV=numeroOV;
      
    }


    public void addOP(OrdemProducao op){
        this.ordensProducao.add(op);
        op.setOrdemVenda(this);

    }
    public void removeOP(OrdemProducao op){
        this.ordensProducao.remove(op);
        op.setOrdemVenda(null);

    }

    public String gerarOrdensProducao(List<OrdemProducaoDTO> ops){

        if(this.ordensProducao!=null && !this.ordensProducao.isEmpty()){
           return "Produções já foram iniciadas para venda";
        }
        this.ordensProducao = new ArrayList<>();
        for(OrdemProducaoDTO op : ops ){
            OrdemProducao ordensP = new OrdemProducao(op.numeroOP(),op.material(),this);
            addOP(ordensP);
            
        }
        return "Produções iniciadas para vendas";
    }
   public double receitaLiquida(){
    double result =0;
    for(ItemVenda  v : itemVendas){
      result= result+   v.getValorTotal();
    }
    return result;
    
   }
   

}
