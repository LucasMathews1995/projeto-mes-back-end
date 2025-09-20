package com.lucasmes.mesprojeto.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.lucasmes.mesprojeto.entity.enums.productiononder.StatusOP;
import com.lucasmes.mesprojeto.exceptions.NotAvailableProductionOrderException;
import com.lucasmes.mesprojeto.exceptions.NotFoundProductionOrderException;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;

import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_ordem_producao")
@NoArgsConstructor
@Getter
@Setter
public class ProductionOrder {

    @Id
    private String productionOrder;
    private LocalDate startTime;
    private Double weight;
    private StatusOP statusOP;
    private Integer quantityProduced;
    private Integer quantityRejected;
    private Integer limiteMaximoLotes;

    @OneToMany(mappedBy = "productionOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Batch> batches;
    @OneToMany(mappedBy = "productionOrder")
    List<Operation> operations;

    public ProductionOrder(String productionOrder, Double weight, Integer limiteMaximoLotes) {
        this.productionOrder = getProductionOrderName();
        this.startTime = LocalDate.now();
        this.weight = weight;
        this.statusOP = StatusOP.ABERTA;
        this.quantityProduced = 0;
        this.quantityRejected = 0;
        this.limiteMaximoLotes = limiteMaximoLotes;
        batches = new ArrayList<>();

    }

    public boolean verifyOP() {
        if (this.getStatusOP() == StatusOP.CANCELADA || this.getStatusOP() == StatusOP.PAUSADA) {
            throw new NotAvailableProductionOrderException("This order seems to be not available");
        } else if (this.getStatusOP() == StatusOP.EM_EXECUCAO || this.getStatusOP() == StatusOP.CONCLUIDA) {
            throw new NotFoundProductionOrderException("This order seems to be already done");
        } else {
            return true;
        }
    }

    public void addQuantityProduced() {
        this.setQuantityProduced(quantityProduced + 1);
    }

    public void addQuantityRejected() {

        this.setQuantityRejected(quantityRejected + 1);
    }

    public void addBatch(Batch batch) {
        if (batches.size() < getLimiteMaximoLotes()) {
            this.batches.add(batch);
            batch.setProductionOrder(this);
            setLimiteMaximoLotes(getLimiteMaximoLotes()-1);
        } else {
            throw new IllegalStateException("Limited number reached");
        }

    }

    public void removeBatch(Batch batch) {
            
            this.batches.remove(batch);
            batch.setProductionOrder(null);
        
    }
    public String getProductionOrderName(){
        StringBuilder st= new StringBuilder();
        Random random = new Random();
       return st.append("PO").append(random.nextInt(10000,90000)).toString();
    }

}
