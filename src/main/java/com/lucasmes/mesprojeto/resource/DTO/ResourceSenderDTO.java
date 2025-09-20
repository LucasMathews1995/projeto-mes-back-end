package com.lucasmes.mesprojeto.resource.DTO;

import com.lucasmes.mesprojeto.entity.enums.resource.CurrentStatus;
import com.lucasmes.mesprojeto.entity.enums.resource.Function;

public record ResourceSenderDTO(String nameResource,String nameArea,Double currentCapacity,Function function,CurrentStatus currentStatus) {

}
