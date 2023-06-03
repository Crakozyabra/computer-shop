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
public class Laptop extends BaseComputerPart {

    //@Convert(converter = LaptopSizeAttributeConverter.class)
    @Min(0)
    @NotNull
    private Integer laptopSize;

    public Laptop(Integer id, String serialNumber, BigDecimal price, Integer quantityOnStock, Producer producer,
                  Integer laptopSize) {
        super(id, serialNumber, price, quantityOnStock, producer);
        this.laptopSize = laptopSize;
    }
}
