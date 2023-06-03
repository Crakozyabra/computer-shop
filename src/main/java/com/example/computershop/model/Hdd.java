package com.example.computershop.model;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@NoArgsConstructor
@Getter
@Setter
@Entity
@ToString
public class Hdd extends BaseComputerPart {

    @Min(0)
    @NotNull
    private Integer capacity;

    public Hdd(Integer id, String serialNumber, BigDecimal price, Integer quantityOnStock, Producer producer,
               Integer capacity) {
        super(id, serialNumber, price, quantityOnStock, producer);
        this.capacity = capacity;
    }
}
