package com.example.computershop.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(uniqueConstraints = @UniqueConstraint(name = "serial_producer_unique_constraint",
        columnNames = {"serial_number", "producer_id"}))
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("Base")
@ToString
public class BaseComputerPart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(min = 3, max = 128)
    @NotBlank
    @Column(name = "serial_number", nullable = false)
    private String serialNumber;

    @Min(0)
    @NotNull
    @Column(nullable = false)
    private BigDecimal price;

    @Min(0)
    @NotNull
    @Column(name = "quantity_on_stock")
    private Integer quantityOnStock;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne
    @JoinColumn(name = "producer_id")
    private Producer producer;

    public BaseComputerPart(Integer id, String serialNumber, BigDecimal price, Integer quantityOnStock,
                            Producer producer) {
        this.id = id;
        this.serialNumber = serialNumber;
        this.price = price;
        this.quantityOnStock = quantityOnStock;
        this.producer = producer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || !getClass().equals(Hibernate.getClass(o))) {
            return false;
        }
        BaseComputerPart that = (BaseComputerPart) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id == null ? 0 : id;
    }
}
