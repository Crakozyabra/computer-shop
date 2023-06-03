package com.example.computershop.dto;

import com.example.computershop.model.enums.DesktopComputerFormFactor;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ComputerPartDto {

    @Setter
    private Integer id;

    @Size(min = 3, max = 128)
    @NotBlank
    private String serialNumber;

    @Min(1)
    @NotNull
    private Integer producerId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String producerName;

    @Min(0)
    @NotNull
    private BigDecimal price;

    @Min(0)
    @NotNull
    private Integer quantityOnStock;

    private ProductType productType;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private DesktopComputerFormFactor desktopComputerFormFactor;

    @Min(0)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer hddCapacity;

    @Min(0)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer laptopSize;

    @Min(0)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double monitorDiagonal;
}
