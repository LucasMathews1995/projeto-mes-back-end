package com.lucasmes.mesprojeto.DTO;

import com.lucasmes.mesprojeto.entity.OrdemProducao;

public record LoteDTO(String numeroLote,double peso, double largura,double espessura, String ordemProducao) {

}
