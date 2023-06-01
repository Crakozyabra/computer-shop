package com.example.computershop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
public class ComputerPartDto {

    @Setter
    private Long id;

    //@JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String serialNumber;

    //@JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long producerId;

    private Long price;

    private Long quantityOnStock;

    private ProductType productType;

    private String additionalProperty;
}
