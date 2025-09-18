package com.lucasmes.mesprojeto.entity;

import java.time.LocalDate;
import java.util.List;

import org.springframework.cglib.core.Local;

import com.lucasmes.mesprojeto.entity.enums.StatusOperation;
import com.lucasmes.mesprojeto.exceptions.NotFoundOperationException;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_operation")
@NoArgsConstructor
@Getter
@Setter
public class Operation {


      @EmbeddedId
    private OperationId id;

    @ManyToOne
    @MapsId("productionOrder") 
    @JoinColumn(name = "production_order", referencedColumnName = "productionOrder", insertable = false, updatable = false)
    private ProductionOrder productionOrder;

    
    @ManyToOne
    @MapsId("resourceName") 
    @JoinColumn(name = "resource_name", referencedColumnName = "nameResource", insertable = false, updatable = false)
    private Resource resource;


    @ManyToOne
    @MapsId("batchNumber")
    @JoinColumn(name = "batch_number", referencedColumnName = "batchNumber", insertable = false, updatable = false)
    private Batch batch;
    
  
    private LocalDate startTime;
    private LocalDate endTime;
    @Enumerated(EnumType.STRING)
    private StatusOperation status;


    public Operation(OperationId id,LocalDate startTime,ProductionOrder productionOrder,Resource resource,Batch batch){
      this.id = id;
      this.startTime = LocalDate.now();
      this.status = StatusOperation.READY;
    }


   
 public void finishOperation(){
  this.setEndTime(LocalDate.now());
  this.setStatus(StatusOperation.FINISHED);
  this.setBatch(null);
  this.setResource(null);
  this.setProductionOrder(null);

 }
 public void initiateOperation(){
  this.setStatus(StatusOperation.PROCESSING);
  
 }
 public void addOP(ProductionOrder pos,Batch batch,Resource resource){
  this.setProductionOrder(pos);
 List<Batch> batches =  pos.getBatches();
    for(Batch batchEach: batches){
      if(batchEach.equals(batch)){
        this.setBatch(batch);
      }
    }
    this.setResource(resource);
 }
 



}


