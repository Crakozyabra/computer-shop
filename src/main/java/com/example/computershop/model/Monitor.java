package com.example.computershop.model;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Monitor extends BaseComputerPart {

    @Min(0)
    @NotNull
    private Double diagonal;

    public Monitor(Integer id, String serialNumber, BigDecimal price, Integer quantityOnStock, Producer producer,
                   Double diagonal) {
        super(id, serialNumber, price, quantityOnStock, producer);
        this.diagonal = diagonal;
    }
}
