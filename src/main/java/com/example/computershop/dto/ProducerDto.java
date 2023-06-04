package com.example.computershop.dto;


import com.example.computershop.HasId;
import com.example.computershop.util.validation.NoHtml;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProducerDto implements HasId {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Setter
    private Integer id;

    @Size(min = 3, max = 128)
    @NoHtml
    @NotBlank
    private String name;
}
