package com.example.computershop.controller;

import com.example.computershop.dto.ComputerPartDto;
import com.example.computershop.dto.ProductType;
import com.example.computershop.service.ProductService;
import com.example.computershop.util.ValidationUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static com.example.computershop.util.OpenApiUtil.*;

@RestController
@RequestMapping(value = ProductController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController {

    public static final String REST_URL = "/products";

    @Autowired
    private ProductService productService;

    @Operation(requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = PRODUCT_REQUEST_BODY_CREATE_DESCRIPTION,
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = @ExampleObject(value = PRODUCT_REQUEST_BODY_CREATE_EXAMPLE)
            )
    ))
    @PostMapping
    public ResponseEntity<ComputerPartDto> create(@Valid @RequestBody ComputerPartDto computerPartDto) {
        ValidationUtil.checkNew(computerPartDto);
        ComputerPartDto created = productService.create(computerPartDto);
        URI uriOfCreatedResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/{id}").buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfCreatedResource).body(created);
    }

    @GetMapping("/{id}")
    public ComputerPartDto get(@PathVariable Integer id) {
        return productService.get(id);
    }

    @GetMapping("/by-type")
    public List<ComputerPartDto> getAllByType(@RequestParam("type") ProductType productType) {
        return productService.getAllByType(productType);
    }

    @Operation(requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = PRODUCT_REQUEST_BODY_CREATE_DESCRIPTION,
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = @ExampleObject(value = PRODUCT_REQUEST_BODY_CREATE_EXAMPLE)
            )
    ))
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    public void update(@Valid @RequestBody ComputerPartDto computerPartDto, @PathVariable Integer id) {
        ValidationUtil.assureIdConsistent(computerPartDto, id);
        productService.update(computerPartDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        productService.delete(id);
    }
}
