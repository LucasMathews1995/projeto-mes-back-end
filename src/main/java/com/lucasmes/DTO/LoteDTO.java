package com.lucasmes.DTO;

import com.lucasmes.entity.OrdemProducao;

public record LoteDTO(String numeroLote,double peso, double largura,double espessura, String ordemProducao) {

}
