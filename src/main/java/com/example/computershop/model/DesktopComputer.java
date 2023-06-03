package com.example.computershop.model;

import com.example.computershop.model.enums.DesktopComputerFormFactor;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@Getter
@Setter
@Entity
@DiscriminatorValue("Desktop")
public class DesktopComputer extends BaseComputerPart{

    @Enumerated(EnumType.STRING)
    @Column(name = "form_factor")
    private DesktopComputerFormFactor formFactor;

    public DesktopComputer(Integer id, String serialNumber, BigDecimal price, Integer quantityOnStock, Producer producer,
                           DesktopComputerFormFactor formFactor) {
        super(id, serialNumber, price, quantityOnStock, producer);
        this.formFactor = formFactor;
    }
}
