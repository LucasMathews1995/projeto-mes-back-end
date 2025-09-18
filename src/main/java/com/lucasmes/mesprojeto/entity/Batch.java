package com.lucasmes.mesprojeto.entity;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.lucasmes.mesprojeto.entity.enums.FinalQuality;
import com.lucasmes.mesprojeto.entity.enums.StatusBatch;
import com.lucasmes.mesprojeto.exceptions.BadQualityException;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_lote")
@Getter
@Setter
@NoArgsConstructor
public class Batch {

    @Id
    private String batchNumber;  
  @Column(nullable = false)
     private Double weight;
     
     @Column(nullable = false)
    private Double width;

    @Column(nullable = false)
    private Double thickness;

    @Column(nullable = false)
    private LocalDate startTime;

    private LocalDate finalDate;
    @Enumerated(EnumType.STRING)
    private StatusBatch status;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private FinalQuality quality;
    @Column(nullable = false)
    private Double progress;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "production_order")
  ProductionOrder productionOrder;


  @OneToMany(mappedBy = "batch")
    private List<Operation> operations= new ArrayList<>();


  

      public boolean finishBatch(){
        if(this.getQuality()==FinalQuality.BAD ||this.getQuality()==FinalQuality.REWORK){
          throw new BadQualityException("Batch with bad quality");
        }else{

          this.setProgress(100.0);
          this.setFinalDate(LocalDate.now());
          return true;
        }
      }
      public void putInRework(){

        StringBuilder st=  new StringBuilder();
    String batch=  st.append("RW").append(this.getBatchNumber()).toString();
   this.setBatchNumber(batch);
   
    }

      public String giveBatchNumber(){
     StringBuilder st=  new StringBuilder();
     Random random = new Random();
     int randomNumber = random.nextInt(100000, 999999);
     return st.append("E").append(randomNumber).toString();
    }
    public boolean initiateBatch(){
      if(this.getStatus()== StatusBatch.CONCLUIDO || this.getStatus()== StatusBatch.CANCELADO ||this.getStatus()== StatusBatch.DUPLICADO){
       throw new IllegalStateException("This batch is in conflict with status");
      }else {
        this.setStatus(StatusBatch.PROCESSANDO);
        return true;
      }
    }
    

      }

      
  

  
   


   



   


