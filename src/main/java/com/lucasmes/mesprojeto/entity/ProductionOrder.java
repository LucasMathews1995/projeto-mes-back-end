package com.lucasmes.mesprojeto.entity;

import java.time.LocalDate;
import java.util.List;

import com.lucasmes.mesprojeto.entity.enums.StatusOP;


import jakarta.persistence.Entity;

import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name= "tb_ordem-producao")
@NoArgsConstructor
@Getter
@Setter
public class ProductionOrder {




@Id
    private String numberOp;
    private LocalDate startTime;
    private Double weight;
    private StatusOP statusOP;
    private Double quantityProduced;
    private Double quantityRejected;

    @OneToMany(mappedBy = "productionOrder")
    private List<Batch> batchs;

    @OneToMany(mappedBy = "productionOrder")
    private List<Resource> resources;


}
