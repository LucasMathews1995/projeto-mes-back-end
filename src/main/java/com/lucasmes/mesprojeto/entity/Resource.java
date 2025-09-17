package com.lucasmes.mesprojeto.entity;

import com.lucasmes.mesprojeto.entity.enums.Function;
import com.lucasmes.mesprojeto.entity.enums.StatusBatch;
import com.lucasmes.mesprojeto.exceptions.NotAvailableResourceException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import com.lucasmes.mesprojeto.entity.enums.CurrentStatus;
import com.lucasmes.mesprojeto.entity.enums.FinalQuality;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

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
    private List<Operation> operations;



    public Resource(String nameResource, String nameArea, Double currentCapacity) {
        this.nameResource = nameResource;
        this.nameArea = nameArea;
        this.currentCapacity = currentCapacity;
        this.function = Function.DEFAULT;
        this.currentStatus = CurrentStatus.DISPONIVEL;
        this.operations = new ArrayList<>();
       
    }


 
    

    public boolean verifyStatus(){
        if(this.getCurrentStatus()==CurrentStatus.QUEBRADO || this.getCurrentStatus()==CurrentStatus.MANUTENCAO || this.getCurrentStatus()==CurrentStatus.EM_ESPERA){
            throw new NotAvailableResourceException("Resource not available right now");

        }
        else{
return true;
        }
    }
}
    

   













