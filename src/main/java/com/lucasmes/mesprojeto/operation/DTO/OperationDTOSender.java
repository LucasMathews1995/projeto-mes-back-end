package com.lucasmes.mesprojeto.operation.DTO;

import java.time.LocalDate;


public record OperationDTOSender(LocalDate startTime,String productionOrder,String resource,String batch) {

}
