package com.lucasmes.mesprojeto.DTO;

import com.lucasmes.mesprojeto.entity.enums.CurrentStatus;
import com.lucasmes.mesprojeto.entity.enums.Function;

public record ResourceSenderDTO(String nameResource,String nameArea,Double currentCapacity,Function function,CurrentStatus currentStatus) {

}
