package com.lucasmes.mesprojeto.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Setter
@Getter
public class OperationId implements Serializable {

    private String batchNumber;
    private String resourceName;
    private String productionOrder;

    public OperationId() {
    }

    public OperationId(String batchNumber, String resourceName, String productionOrder) {
        this.batchNumber = batchNumber;
        this.resourceName = resourceName;
        this.productionOrder = productionOrder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OperationId that = (OperationId) o;
        return Objects.equals(batchNumber, that.batchNumber) &&
               Objects.equals(resourceName, that.resourceName) &&
               Objects.equals(productionOrder, that.productionOrder);
    }

    @Override
    public int hashCode() {
        return Objects.hash(batchNumber, resourceName, productionOrder);
    }
}