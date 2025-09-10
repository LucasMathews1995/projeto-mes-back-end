package com.lucasmes.entity;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_item-venda")
@Getter
@Setter
@NoArgsConstructor
public class ItemVenda {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

@ManyToOne
@JoinColumn(name = "ordem-venda_id")
    private OrdemVenda ordemVenda;




@NonNull
@Column(nullable = false)
private Integer quantidade;


@Column(nullable = false)
private Double precoUnitario;




    public double getValorTotal() {
        return this.quantidade * this.precoUnitario;
    }

}
