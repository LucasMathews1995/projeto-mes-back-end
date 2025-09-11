package com.lucasmes.mesprojeto.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
import lombok.NonNull;

@Entity
@Table(name= "tb_lotes")
@Getter
@Setter
@NoArgsConstructor
public class Lote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String numeroLote;
   @Column(nullable = false)
    private Double peso;
     @Column(nullable = false)
    private Double largura;
      @Column(nullable = false)
    private Double espessura;
      
    private LocalDate dataCriacao;
   
    private LocalDate dataFinalizada;




      @Enumerated(EnumType.STRING)
    private StatusLote  status;
    private Double progresso;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "op_id")
    private OrdemProducao ordemProducao;


    @OneToMany(mappedBy = "lote", cascade = CascadeType.ALL)
    private List<Equipamento> equipamentos;


    public Lote(String numeroLote,double peso, double largura,double espessura, OrdemProducao ordemProducao){
        this.numeroLote = numeroLote;
        this.peso= peso;
        this.largura=largura;
        this.espessura=espessura;
        this.progresso =0.0;
        this.status=  null;
        this.ordemProducao = ordemProducao;
        this.equipamentos = new ArrayList<>();


    }


    public void finalizarLote(){
    if(progresso < 100){
            throw new  IllegalStateException("O lote ainda não completou 100% de seu progresso");

    }else {
        this.status= StatusLote.FINALIZADO;
        this.dataFinalizada = LocalDate.now();

    }
    }


    public void iniciarLote(){

        if(this.status==StatusLote.INICIADO){
            throw new IllegalStateException("O lote já foi iniciado");
        }else {
            this.status = StatusLote.INICIADO;
            this.dataCriacao = LocalDate.now();
        }

    }


    public StatusLote  getStatus (){
       return this.status;
    }


    public void cancelarLote(){

        if(this.status==StatusLote.CANCELADO){
            throw new IllegalStateException("Lote já foi cancelado");
        }else {
            this.status = StatusLote.INICIADO;
            this.dataFinalizada = LocalDate.now();
        }
    }
    public void registrarAvanco(double progresso){
        if(this.status!=StatusLote.INICIADO && this.status!=StatusLote.PROCESSANDO){
            throw new IllegalArgumentException("Lote já está finalizado ou cancelado");

        }
        if (progresso < 0 || progresso > 100) {
            throw new IllegalArgumentException("Lote se encontra com erro");
        }
        this.progresso= progresso;
        this.status = StatusLote.PROCESSANDO;
    }


    public List<Lote> repartirPeso(double pesoRepartido){
        List<Lote> lotes= new ArrayList<>();
        if(this.peso > pesoRepartido){
         double  pesoRestante = peso-pesoRepartido;
         Lote l1 = new Lote(getNumeroLote()+1,pesoRepartido,getLargura(),getEspessura(),getOrdemProducao());
         l1.setDataCriacao(LocalDate.now());
         Lote l2 = new Lote(getNumeroLote()+2,pesoRestante,getLargura(),getEspessura(),getOrdemProducao());
         l2.setDataCriacao(LocalDate.now());
         lotes.add(l1);
        lotes.add(l2);
            return lotes;
        }else {
            throw new IllegalStateException("Impossível repartir  devido ao peso do lote mãe ser maior que o lote filho");
        }
        
    }


    public void addEquipamento(Equipamento ep ){
        this.equipamentos.add(ep);
        ep.setLote(this);
    }
    public void removerEquipamento(Equipamento ep){
        this.equipamentos.remove(ep);
        ep.setLote(null);
    }



}






