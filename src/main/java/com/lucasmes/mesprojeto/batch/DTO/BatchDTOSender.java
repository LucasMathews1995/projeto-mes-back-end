package com.lucasmes.mesprojeto.batch.DTO;

import com.lucasmes.mesprojeto.entity.enums.batch.FinalQuality;
import com.lucasmes.mesprojeto.entity.enums.batch.StatusBatch;

public record BatchDTOSender(String batchNumber,Double weight,Double width,Double thickness,FinalQuality quality,Double progress,StatusBatch status) {

}
