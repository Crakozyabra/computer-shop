package com.example.computershop.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Monitor extends BaseComputerPart{

    private Double diagonal;

    public Monitor(Long id, String serialNumber, Long price, Long quantityOnStock, Producer producer, Double diagonal) {
        super(id, serialNumber, price, quantityOnStock, producer);
        this.diagonal = diagonal;
    }
}
