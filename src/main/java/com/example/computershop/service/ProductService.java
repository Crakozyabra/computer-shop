package com.example.computershop.service;

import com.example.computershop.dto.ComputerPartDto;
import com.example.computershop.dto.ProductType;
import com.example.computershop.error.DataConflictException;
import com.example.computershop.error.NotFoundException;
import com.example.computershop.model.*;
import com.example.computershop.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.computershop.dto.ProductType.DESKTOP_COMPUTER;
import static com.example.computershop.util.DtoUtil.*;

@AllArgsConstructor
@Transactional(readOnly = true)
@Service
public class ProductService {

    private HddRepository hddRepository;

    private DesktopComputerRepository desktopComputerRepository;

    private MonitorRepository monitorRepository;

    private LaptopRepository laptopRepository;

    private ProducerRepository producerRepository;

    private BaseComputerPartRepository baseComputerPartRepository;

    @Transactional
    public ComputerPartDto create(ComputerPartDto computerPartDto) {
        Producer producer = producerRepository.getReferenceById(computerPartDto.getProducerId().longValue());
        ProductType productType = computerPartDto.getProductType();
        BaseComputerPart baseComputerPart = switch (productType) {
            case DESKTOP_COMPUTER -> desktopComputerRepository.save(computerPartDtoToDesktopComputer(computerPartDto,
                    producer));
            case MONITOR -> monitorRepository.save(computerPartDtoToMonitor(computerPartDto, producer));
            case LAPTOP -> laptopRepository.save(computerPartDtoToLaptop(computerPartDto, producer));
            case HDD -> hddRepository.save(computerPartDtoToHdd(computerPartDto, producer));
            default -> throw new IllegalArgumentException("Such type does not supports");
        };
        computerPartDto.setId(baseComputerPart.getId());
        computerPartDto.setProducerName(baseComputerPart.getProducer().getName());
        return computerPartDto;
    }

    public ComputerPartDto get(Integer id) {
        BaseComputerPart baseComputerPart = baseComputerPartRepository.findById(id.longValue()).orElseThrow(
                () -> new NotFoundException("Product not found"));
        ProductType productType = getProductType(baseComputerPart);
        return switch (productType) {
            case DESKTOP_COMPUTER -> desktopComputerToComputerPartDto((DesktopComputer) baseComputerPart);
            case MONITOR -> monitorToComputerPartDto((Monitor) baseComputerPart);
            case LAPTOP -> laptopToComputerPartDto((Laptop) baseComputerPart);
            case HDD -> hddToComputerPartDto((Hdd) baseComputerPart);
            default -> throw new IllegalArgumentException("Such type does not supports");
        };
    }

    public List<ComputerPartDto> getAllByType(ProductType productType) {
        return switch (productType) {
            case DESKTOP_COMPUTER -> desktopComputersToComputerPartDtos(desktopComputerRepository.findAll());
            case MONITOR -> monitorsToComputerPartDtos(monitorRepository.findAll());
            case LAPTOP -> laptopsToComputerPartDtos(laptopRepository.findAll());
            case HDD -> hddsToComputerPartDtos(hddRepository.findAll());
            default -> throw new IllegalArgumentException("Such type does not supports");
        };
    }

    @Transactional
    public void update(ComputerPartDto computerPartDto) {
        BaseComputerPart baseComputerPart = baseComputerPartRepository.findById(computerPartDto.getId().longValue())
                .orElseThrow(() -> new NotFoundException("Such id not found"));
        ProductType productType = getProductType(baseComputerPart);
        if (computerPartDto.getProductType() != productType)
            throw new DataConflictException(
                    String.format("Requests %s and databases %s productType not matches for id=%d",
                            computerPartDto.getProductType().name(), productType.name(), computerPartDto.getId()));
        Producer producer = producerRepository.getReferenceById(computerPartDto.getProducerId().longValue());
        baseComputerPart.setProducer(producer);
        baseComputerPart.setPrice(computerPartDto.getPrice());
        baseComputerPart.setSerialNumber(computerPartDto.getSerialNumber());
        baseComputerPart.setQuantityOnStock(computerPartDto.getQuantityOnStock());
        switch (productType) {
            case DESKTOP_COMPUTER -> {
                DesktopComputer desktopComputer = (DesktopComputer) baseComputerPart;
                desktopComputer.setFormFactor(computerPartDto.getDesktopComputerFormFactor());
                desktopComputerRepository.save(desktopComputer);
            }
            case MONITOR -> {
                Monitor monitor = (Monitor) baseComputerPart;
                monitor.setDiagonal(computerPartDto.getMonitorDiagonal());
                monitorRepository.save(monitor);
            }
            case LAPTOP -> {
                Laptop laptop = (Laptop) baseComputerPart;
                laptop.setLaptopSize(computerPartDto.getLaptopSize());
                laptopRepository.save(laptop);
            }
            case HDD -> {
                Hdd hdd = (Hdd) baseComputerPart;
                hdd.setCapacity(computerPartDto.getHddCapacity());
                hddRepository.save(hdd);
            }
            default -> throw new IllegalArgumentException("Such type does not supports");
        }
    }

    @Transactional
    public void delete(Integer id) {
        baseComputerPartRepository.deleteById(id.longValue());
    }

    private ProductType getProductType(BaseComputerPart baseComputerPart) {
        Class<?> clazz = baseComputerPart.getClass();
        return (clazz == DesktopComputer.class) ? DESKTOP_COMPUTER :
                ProductType.valueOf(clazz.getSimpleName().toUpperCase());
    }
}