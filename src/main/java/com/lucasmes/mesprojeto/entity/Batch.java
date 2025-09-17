package com.lucasmes.mesprojeto.entity;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import com.lucasmes.mesprojeto.entity.enums.FinalQuality;
import com.lucasmes.mesprojeto.entity.enums.StatusBatch;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
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

     private Double weight;
    private Double width;
    private Double thickness;
    private LocalDate startTime;
    private LocalDate finalDate;
    @Enumerated(EnumType.STRING)
    private StatusBatch status;
    private FinalQuality quality;
    private Double progress;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("resourceId") // Mapeia o campo 'resourceId' da chave composta para a coluna da FK
    @JoinColumn(name = "resource_id")
    private Resource resource;


  @OneToMany(mappedBy = "batch")
    private List<Operation> operations;


    public Batch( String batchNumber,Double weight, Double width, Double thickness) {
       this.batchNumber = batchNumber;
        this.weight = weight;
        this.width = width;
        this.thickness = thickness;
        this.status = StatusBatch.PROCESSANDO;
        this.startTime = LocalDate.now();
        this.progress = 0.0;
       this.quality= FinalQuality.GOOD;
       this.operations = new ArrayList<>();
    }


  

  
   }


   



   


