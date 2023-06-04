package com.example.computershop.controller;

import com.example.computershop.dto.ProducerDto;
import com.example.computershop.error.NotFoundException;
import com.example.computershop.model.Producer;
import com.example.computershop.repository.ProducerRepository;
import com.example.computershop.util.ValidationUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = ProducerController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class ProducerController {

    public static final String REST_URL = "/producers";

    @Autowired
    private ProducerRepository producerRepository;

    @PostMapping
    public ResponseEntity<ProducerDto> create(@Valid @RequestBody ProducerDto producerDto) {
        ValidationUtil.checkNew(producerDto);
        Producer producer = producerRepository.save(new Producer(null, producerDto.getName()));
        producerDto.setId(producer.getId());
        URI uriOfCreatedResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}").buildAndExpand(producerDto.getId()).toUri();
        return ResponseEntity.created(uriOfCreatedResource).body(producerDto);
    }

    @GetMapping("/{id}")
    public ProducerDto get(@PathVariable Integer id) {
        Producer producer = producerRepository
                .findById(id.longValue())
                .orElseThrow(() -> new NotFoundException("Producer not found"));
        return new ProducerDto(producer.getId(), producer.getName());
    }

    @GetMapping
    public List<ProducerDto> getAll() {
        return producerRepository
                .findAll()
                .stream()
                .map(producer -> new ProducerDto(producer.getId(), producer.getName()))
                .collect(Collectors.toList());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    public void update(@Valid @RequestBody ProducerDto producerDto, @PathVariable Integer id) {
        ValidationUtil.assureIdConsistent(producerDto, id);
        Producer producer = producerRepository
                .findById(id.longValue()).orElseThrow(() -> new NotFoundException("Producer not found"));
        producer.setName(producerDto.getName());
        producerRepository.save(producer);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        producerRepository.deleteById(id.longValue());
    }
}
