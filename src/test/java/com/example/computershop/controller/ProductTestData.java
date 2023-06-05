package com.example.computershop.controller;

import com.example.computershop.dto.ComputerPartDto;
import com.example.computershop.dto.ProductType;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.util.List;

@UtilityClass
public class ProductTestData {

    public static ComputerPartDto getNew() {
        return ComputerPartDto.builder()
                .serialNumber("SERIAL_HDD_1000")
                .producerId(1)
                .price(new BigDecimal(1000))
                .quantityOnStock(10)
                .productType(ProductType.HDD)
                .hddCapacity(256)
                .build();
    }

    public static final ComputerPartDto computerPartDto1 = ComputerPartDto.builder()
            .id(1).serialNumber("SERIAL_HDD_1")
            .producerId(1)
            .price(new BigDecimal("1000.00"))
            .quantityOnStock(10)
            .productType(ProductType.HDD)
            .hddCapacity(128)
            .build();

    public static final ComputerPartDto computerPartDto2 = ComputerPartDto.builder()
            .id(2).serialNumber("SERIAL_HDD_2")
            .producerId(2)
            .price(new BigDecimal("1000.00"))
            .quantityOnStock(20)
            .productType(ProductType.HDD)
            .hddCapacity(256)
            .build();

    public static final ComputerPartDto computerPartDto3 = ComputerPartDto.builder()
            .id(3).serialNumber("SERIAL_HDD_3")
            .producerId(3)
            .price(new BigDecimal("1000.00"))
            .quantityOnStock(30)
            .productType(ProductType.HDD)
            .hddCapacity(512)
            .build();

    public static final List<ComputerPartDto> computerPartDtoList = List.of(computerPartDto1, computerPartDto2,
            computerPartDto3);
}