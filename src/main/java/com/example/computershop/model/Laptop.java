package com.example.computershop.model;

import com.example.computershop.model.enums.LaptopFormFactor;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Laptop extends BaseComputerPart{

    @Enumerated(EnumType.STRING)
    private LaptopFormFactor laptopFormFactor;

    public Laptop(Long id, String serialNumber, Long price, Long quantityOnStock, Producer producer,
                  LaptopFormFactor laptopFormFactor) {
        super(id, serialNumber, price, quantityOnStock, producer);
        this.laptopFormFactor = laptopFormFactor;
    }
}
