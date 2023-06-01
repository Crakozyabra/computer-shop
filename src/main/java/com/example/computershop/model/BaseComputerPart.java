package com.example.computershop.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(uniqueConstraints = @UniqueConstraint(name = "serial_producer_unique_constraint",
        columnNames = {"serial_number", "producer_id"}))
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@ToString
public class BaseComputerPart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "serial_number", nullable = false)
    private String serialNumber;

    @Column(nullable = false)
    private Long price;

    @Column(name = "quantity_on_stock")
    private Long quantityOnStock;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne
    @JoinColumn(name = "producer_id")
    private Producer producer;

    public BaseComputerPart(Long id, String serialNumber, Long price, Long quantityOnStock, Producer producer) {
        this.id = id;
        this.serialNumber = serialNumber;
        this.price = price;
        this.quantityOnStock = quantityOnStock;
        this.producer = producer;
    }
}
