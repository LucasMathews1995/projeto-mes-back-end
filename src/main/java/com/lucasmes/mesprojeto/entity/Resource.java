package com.lucasmes.mesprojeto.entity;

import com.lucasmes.mesprojeto.entity.enums.Function;
import com.lucasmes.mesprojeto.entity.enums.StatusBatch;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import com.lucasmes.mesprojeto.entity.enums.CurrentStatus;
import com.lucasmes.mesprojeto.entity.enums.FinalQuality;
import jakarta.persistence.Entity;

import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_equipamento")
@Getter
@Setter
@NoArgsConstructor
public class Resource {

     

     @Id
    private String nameResource;
    private String nameArea;
    private Double currentCapacity;
    private Function function;
    private CurrentStatus currentStatus;
   
    @OneToMany(mappedBy = "resource")
    private List<Batch> batches;
  
    @OneToMany(mappedBy = "resource")
    private List<Operation> operations;

    public Resource(String nameResource, String nameArea, Double currentCapacity) {
        this.nameResource = nameResource;
        this.nameArea = nameArea;
        this.currentCapacity = currentCapacity;
        this.function = Function.DEFAULT;
        this.currentStatus = CurrentStatus.DISPONIVEL;
        this.operations = new ArrayList<>();
        this.batches = new ArrayList<>();
    }

    public void addBatch(Batch batch) {
        this.batches.add(batch);
        batch.setResource(this);

    }

    public void removeBatch(Batch batch) {
        this.batches.remove(batch);
        batch.setResource(null);
        batch.setFinalDate(LocalDate.now());
        batch.setStatus(StatusBatch.CONCLUIDO);

    }

    public void putInRework() {

        for (int i = 0; i < batches.size(); i++) {

            if (reworkBatch(batches.get(i))) {
                Batch batch = batches.get(i);
                StringBuilder st = new StringBuilder();
                String number = batches.get(i).getBatchNumber().split("E")[1];
                String newNumber = st.append("RW").append(number).toString();
                batch.setBatchNumber(newNumber);
            }
        }

    }

    public boolean reworkBatch(Batch batch){
    if(batch.getQuality()==FinalQuality.REWORK){
        return true;
   }else {
         return false;
   }
}
    

   












}
