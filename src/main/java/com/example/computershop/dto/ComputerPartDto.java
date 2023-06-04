package com.example.computershop.dto;

import com.example.computershop.HasId;
import com.example.computershop.model.enums.DesktopComputerFormFactor;
import com.example.computershop.util.validation.LaptopSizeConstraint;
import com.example.computershop.util.validation.NoHtml;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
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
public class ComputerPartDto implements HasId {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @Setter
    private Integer id;

    @Size(min = 3, max = 128)
    @NoHtml
    @NotBlank
    private String serialNumber;

    @Min(1)
    @NotNull
    private Integer producerId;

    @Setter
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String producerName;

    @Min(0)
    @NotNull
    private BigDecimal price;

    @Min(0)
    @NotNull
    private Integer quantityOnStock;

    @NotNull
    private ProductType productType;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private DesktopComputerFormFactor desktopComputerFormFactor;

    @Min(0)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer hddCapacity;

    @Min(0)
    @LaptopSizeConstraint(sizes = {13, 14, 15, 17})
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer laptopSize;

    @Min(0)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double monitorDiagonal;
}
