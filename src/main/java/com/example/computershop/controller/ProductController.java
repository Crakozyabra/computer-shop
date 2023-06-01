package com.example.computershop.controller;

import com.example.computershop.dto.ComputerPartDto;
import com.example.computershop.dto.DtoUtil;
import com.example.computershop.dto.ProductType;
import com.example.computershop.error.NotFoundException;
import com.example.computershop.model.BaseComputerPart;
import com.example.computershop.model.HardDisk;
import com.example.computershop.model.Producer;
import com.example.computershop.repository.*;
import org.springframework.aop.scope.ScopedProxyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/")
public class ProductController {

    @Autowired
    private HardDiskRepository hardDiskRepository;

    @Autowired
    private LaptopRepository laptopRepository;

    @Autowired
    private MonitorRepository monitorRepository;

    @Autowired
    private NotebookRepository notebookRepository;

    @Autowired
    private ProducerRepository producerRepository;

    @Autowired
    private BaseComputerPartRepository baseComputerPartRepository;

    @PostMapping
    public ResponseEntity<ComputerPartDto> create(ComputerPartDto computerPartDto) {
        Assert.isNull(computerPartDto.getProducerId(), "id must be null");
        Producer producer = producerRepository.getReferenceById(computerPartDto.getProducerId());
        BaseComputerPart baseComputerPart = save(computerPartDto, producer);
        computerPartDto.setId(baseComputerPart.getId());
        URI uriOfCreatedResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/{id}").buildAndExpand(computerPartDto.getId()).toUri();
        return ResponseEntity.created(uriOfCreatedResource).body(computerPartDto);
    }

    @GetMapping("/{id}")
    public void get(@PathVariable Long id) {
        BaseComputerPart baseComputerPart = baseComputerPartRepository.findById(id).orElseThrow(
                () -> new NotFoundException("product not found"));
        String type = baseComputerPart.getClass().getSimpleName();
        //HardDisk hardDisk = (HardDisk) baseComputerPart;
        System.out.println(baseComputerPart.getSerialNumber());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping
    public void update(ComputerPartDto computerPartDto) {
        Assert.isNull(computerPartDto.getProducerId(), "id must not be null");
        Producer producer = producerRepository.getReferenceById(computerPartDto.getProducerId());
        save(computerPartDto, producer);
    }


    private BaseComputerPart save(ComputerPartDto computerPartDto, Producer producer) {
        ProductType productType = computerPartDto.getProductType();
        return switch (productType) {
            case LAPTOP -> laptopRepository.save(DtoUtil.computerPartDtoToLaptop(computerPartDto, producer));
            case MONITOR -> monitorRepository.save(DtoUtil.computerPartDtoToMonitor(computerPartDto, producer));
            case NOTEBOOK -> notebookRepository.save(DtoUtil.computerPartDtoToNotebook(computerPartDto, producer));
            default -> hardDiskRepository.save(DtoUtil.computerPartDtoToHardDisk(computerPartDto, producer));
        };
    }

     /* URI uriOfCreatedResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}").buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfCreatedResource).body(ToUtil.menuToAdminMenuDtoWithRestaurantId(created,
                adminMenuDtoWithRestaurantId.getRestaurantId()));*/
   /* @GetMapping
    public ComputerPartDto test() {
        return new ComputerPartDto("some text", LocalDate.now());
    }*/
}
