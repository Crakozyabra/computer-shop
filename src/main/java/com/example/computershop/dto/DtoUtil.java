package com.example.computershop.dto;

import com.example.computershop.model.*;
import com.example.computershop.model.enums.LaptopFormFactor;
import com.example.computershop.model.enums.NotebookDiagonalSize;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DtoUtil {

    public HardDisk computerPartDtoToHardDisk(ComputerPartDto computerPartDto, Producer producer) {
        Double capacity = Double.parseDouble(computerPartDto.getAdditionalProperty());
        return new HardDisk(computerPartDto.getId(), computerPartDto.getSerialNumber(), computerPartDto.getPrice(),
                computerPartDto.getQuantityOnStock(), producer, capacity);
    }

    public Laptop computerPartDtoToLaptop(ComputerPartDto computerPartDto, Producer producer) {
        LaptopFormFactor formFactor = LaptopFormFactor.valueOf(computerPartDto.getAdditionalProperty());
        return new Laptop(computerPartDto.getId(), computerPartDto.getSerialNumber(), computerPartDto.getPrice(),
                computerPartDto.getQuantityOnStock(), producer, formFactor);
    }

    public Monitor computerPartDtoToMonitor(ComputerPartDto computerPartDto, Producer producer) {
        Double diagonal = Double.parseDouble(computerPartDto.getAdditionalProperty());
        return new Monitor(computerPartDto.getId(), computerPartDto.getSerialNumber(),
                computerPartDto.getPrice(), computerPartDto.getQuantityOnStock(), producer, diagonal);
    }

    public Notebook computerPartDtoToNotebook(ComputerPartDto computerPartDto, Producer producer) {
        NotebookDiagonalSize notebookDiagonalSize = NotebookDiagonalSize.valueOf(computerPartDto.getAdditionalProperty());
        return new Notebook(computerPartDto.getId(), computerPartDto.getSerialNumber(),
                computerPartDto.getPrice(), computerPartDto.getQuantityOnStock(), producer, notebookDiagonalSize);
    }
}
