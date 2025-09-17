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
    private String opName;

    public OperationId() {
    }

    public OperationId(String batchNumber, String resourceName, String opName) {
        this.batchNumber = batchNumber;
        this.resourceName = resourceName;
        this.opName = opName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OperationId that = (OperationId) o;
        return Objects.equals(batchNumber, that.batchNumber) &&
               Objects.equals(resourceName, that.resourceName) &&
               Objects.equals(opName, that.opName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(batchNumber, resourceName, opName);
    }
}