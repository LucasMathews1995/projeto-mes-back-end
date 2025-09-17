package com.lucasmes.mesprojeto.entity;

import java.time.LocalDate;
import com.lucasmes.mesprojeto.entity.enums.StatusOperation;
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
    @MapsId("opName") 
    @JoinColumn(name = "production_order_id", referencedColumnName = "id", insertable = false, updatable = false)
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


    public void finishOperationBatches(){
     
      this.getBatch().setFinalDate(LocalDate.now());
    
    }



}


