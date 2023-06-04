package com.example.computershop.util;

import com.example.computershop.dto.ComputerPartDto;
import com.example.computershop.dto.ProductType;
import com.example.computershop.model.*;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class DtoUtil {

    public Hdd computerPartDtoToHdd(ComputerPartDto computerPartDto, Producer producer) {
        return new Hdd(computerPartDto.getId(), computerPartDto.getSerialNumber(), computerPartDto.getPrice(),
                computerPartDto.getQuantityOnStock(), producer, computerPartDto.getHddCapacity());
    }

    public DesktopComputer computerPartDtoToDesktopComputer(ComputerPartDto computerPartDto, Producer producer) {
        return new DesktopComputer(computerPartDto.getId(), computerPartDto.getSerialNumber(), computerPartDto.getPrice(),
                computerPartDto.getQuantityOnStock(), producer, computerPartDto.getDesktopComputerFormFactor());
    }

    public Monitor computerPartDtoToMonitor(ComputerPartDto computerPartDto, Producer producer) {
        return new Monitor(computerPartDto.getId(), computerPartDto.getSerialNumber(), computerPartDto.getPrice(),
                computerPartDto.getQuantityOnStock(), producer, computerPartDto.getMonitorDiagonal());
    }

    public Laptop computerPartDtoToLaptop(ComputerPartDto computerPartDto, Producer producer) {
        return new Laptop(computerPartDto.getId(), computerPartDto.getSerialNumber(), computerPartDto.getPrice(),
                computerPartDto.getQuantityOnStock(), producer, computerPartDto.getLaptopSize());
    }

    public ComputerPartDto hddToComputerPartDto(Hdd hdd) {
        return getComputerPartDtoBuilder(hdd)
                .productType(ProductType.HDD)
                .hddCapacity(hdd.getCapacity())
                .build();
    }

    public ComputerPartDto desktopComputerToComputerPartDto(DesktopComputer desktopComputer) {
        return getComputerPartDtoBuilder(desktopComputer)
                .productType(ProductType.DESKTOP_COMPUTER)
                .desktopComputerFormFactor(desktopComputer.getFormFactor())
                .build();
    }

    public ComputerPartDto monitorToComputerPartDto(Monitor monitor) {
        return getComputerPartDtoBuilder(monitor)
                .productType(ProductType.MONITOR)
                .monitorDiagonal(monitor.getDiagonal())
                .build();
    }

    public ComputerPartDto laptopToComputerPartDto(Laptop laptop) {
        return getComputerPartDtoBuilder(laptop)
                .productType(ProductType.LAPTOP)
                .laptopSize(laptop.getLaptopSize())
                .build();
    }

    private ComputerPartDto.ComputerPartDtoBuilder getComputerPartDtoBuilder(BaseComputerPart baseComputerPart) {
        return ComputerPartDto.builder()
                .id(baseComputerPart.getId())
                .serialNumber(baseComputerPart.getSerialNumber())
                .producerId(baseComputerPart.getProducer().getId())
                .producerName(baseComputerPart.getProducer().getName())
                .price(baseComputerPart.getPrice())
                .quantityOnStock(baseComputerPart.getQuantityOnStock());
    }

    public List<ComputerPartDto> hddsToComputerPartDtos(List<Hdd> hdds) {
        return hdds.stream().map(DtoUtil::hddToComputerPartDto).collect(Collectors.toList());
    }

    public List<ComputerPartDto> desktopComputersToComputerPartDtos(List<DesktopComputer> desktopComputers) {
        return desktopComputers.stream().map(DtoUtil::desktopComputerToComputerPartDto).collect(Collectors.toList());
    }

    public List<ComputerPartDto> laptopsToComputerPartDtos(List<Laptop> laptops) {
        return laptops.stream().map(DtoUtil::laptopToComputerPartDto).collect(Collectors.toList());
    }

    public List<ComputerPartDto> monitorsToComputerPartDtos(List<Monitor> monitors) {
        return monitors.stream().map(DtoUtil::monitorToComputerPartDto).collect(Collectors.toList());
    }
}
