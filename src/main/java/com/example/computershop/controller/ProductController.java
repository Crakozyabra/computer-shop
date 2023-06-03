package com.example.computershop.controller;

import com.example.computershop.dto.ComputerPartDto;
import com.example.computershop.dto.ProductType;
import com.example.computershop.error.NotFoundException;
import com.example.computershop.model.*;
import com.example.computershop.repository.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static com.example.computershop.dto.DtoUtil.*;
import static com.example.computershop.dto.ProductType.DESKTOP_COMPUTER;

@RestController
@RequestMapping(value = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController {

    @Autowired
    private HddRepository hddRepository;

    @Autowired
    private DesktopComputerRepository desktopComputerRepository;

    @Autowired
    private MonitorRepository monitorRepository;

    @Autowired
    private LaptopRepository laptopRepository;

    @Autowired
    private ProducerRepository producerRepository;

    @Autowired
    private BaseComputerPartRepository baseComputerPartRepository;

    @PostMapping
    public ResponseEntity<ComputerPartDto> create(@Valid @RequestBody ComputerPartDto computerPartDto) {
        Assert.isNull(computerPartDto.getId(), "id must be null");
        Producer producer = producerRepository.getReferenceById(computerPartDto.getProducerId().longValue());
        BaseComputerPart baseComputerPart = save(computerPartDto, producer);
        computerPartDto.setId(baseComputerPart.getId());
        URI uriOfCreatedResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/{id}").buildAndExpand(computerPartDto.getId()).toUri();
        return ResponseEntity.created(uriOfCreatedResource).body(computerPartDto);
    }

    @GetMapping("/{id}")
    public ComputerPartDto get(@PathVariable Long id) {
        BaseComputerPart baseComputerPart = baseComputerPartRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Product not found"));
        Class<?> clazz = baseComputerPart.getClass();
        ProductType productType = (clazz == DesktopComputer.class) ? DESKTOP_COMPUTER :
                ProductType.valueOf(clazz.getSimpleName().toUpperCase());
        return switch (productType) {
            case DESKTOP_COMPUTER -> desktopComputerToComputerPartDto((DesktopComputer) baseComputerPart);
            case MONITOR -> monitorToComputerPartDto((Monitor) baseComputerPart);
            case LAPTOP -> laptopToComputerPartDto((Laptop) baseComputerPart);
            case HDD -> hddToComputerPartDto((Hdd) baseComputerPart);
            default -> throw new IllegalArgumentException("Such type does not exist");
        };
    }

    @GetMapping("/by-type")
    public List<ComputerPartDto> getAllByType(@RequestParam("type") ProductType productType) {
        return switch (productType) {
            case DESKTOP_COMPUTER -> desktopComputersToComputerPartDtos(desktopComputerRepository.findAll());
            case MONITOR -> monitorsToComputerPartDtos(monitorRepository.findAll());
            case LAPTOP -> laptopsToComputerPartDtos(laptopRepository.findAll());
            case HDD -> hddsToComputerPartDtos(hddRepository.findAll());
            default -> throw new IllegalArgumentException("Such type does not exist");
        };
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping
    public void update(@Valid  @RequestBody ComputerPartDto computerPartDto) {
        Assert.notNull(computerPartDto.getId(), "id must not be null");
        Producer producer = producerRepository.getReferenceById(computerPartDto.getProducerId().longValue());
        save(computerPartDto, producer);
    }

    private BaseComputerPart save(ComputerPartDto computerPartDto, Producer producer) {
        ProductType productType = computerPartDto.getProductType();
        return switch (productType) {
            case DESKTOP_COMPUTER -> desktopComputerRepository.save(computerPartDtoToDesktopComputer(computerPartDto,
                    producer));
            case MONITOR -> monitorRepository.save(computerPartDtoToMonitor(computerPartDto, producer));
            case LAPTOP -> laptopRepository.save(computerPartDtoToLaptop(computerPartDto, producer));
            case HDD -> hddRepository.save(computerPartDtoToHdd(computerPartDto, producer));
            default -> throw new IllegalArgumentException("Such type does not exist");
        };
    }
}
