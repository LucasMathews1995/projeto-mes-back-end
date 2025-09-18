package com.lucasmes.mesprojeto.DTO;

import java.time.LocalDate;

import com.lucasmes.mesprojeto.entity.OperationId;
import com.lucasmes.mesprojeto.entity.ProductionOrder;

public record OperationDTOSender(LocalDate startTime,String productionOrder,String resource,String batch) {

}
