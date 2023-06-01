package com.example.computershop.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@Entity
@ToString
public class HardDisk extends BaseComputerPart {

    private Double capacity;

    public HardDisk(Long id, String serialNumber, Long price, Long quantityOnStock, Producer producer, Double capacity) {
        super(id, serialNumber, price, quantityOnStock, producer);
        this.capacity = capacity;
    }
}
