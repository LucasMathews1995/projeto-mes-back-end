package com.lucasmes.mesprojeto.entity;


import com.lucasmes.mesprojeto.entity.enums.resource.CurrentStatus;
import com.lucasmes.mesprojeto.entity.enums.resource.Function;
import com.lucasmes.mesprojeto.exceptions.NotAvailableResourceException;
import com.lucasmes.mesprojeto.exceptions.OutOfCapacityException;


import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

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
    @Enumerated(EnumType.STRING)
    private Function function;
     @Enumerated(EnumType.STRING)
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
    public boolean verifyCapacity(Batch batch )throws OutOfCapacityException{
      
      if(batch.getWeight()> this.getCurrentCapacity()){
      throw new OutOfCapacityException("Capacity exceeded");
      }  else {
        this.setCurrentCapacity(this.getCurrentCapacity() -batch.getWeight());
        return true;

        
      }

      

    }

    public void releasedResource(){
      this.setNameArea("None");
this.setNameResource("None");
    }
    
    



}
    

   













