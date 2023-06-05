package com.example.computershop.controller;

import com.example.computershop.dto.ComputerPartDto;
import com.example.computershop.error.NotFoundException;
import com.example.computershop.util.JsonUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.List;

import static com.example.computershop.controller.ProductTestData.computerPartDto1;
import static com.example.computershop.controller.ProductTestData.computerPartDtoList;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class ProductControllerTest extends AbstractControllerTest {

    @Test
    public void createWithLocation() throws Exception {
        String content = perform(post(ProductController.REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(ProductTestData.getNew())))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();
        ComputerPartDto body = JsonUtil.readValue(content, ComputerPartDto.class);
        ComputerPartDto saved = productService.get(body.getId());
        Assertions.assertThat(body).usingRecursiveComparison().ignoringFields("producerName")
                .isEqualTo(saved);
    }

    @Test
    public void getById() throws Exception {
        String content = perform(get(ProductController.REST_URL + "/" + computerPartDto1.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();
        ComputerPartDto body = JsonUtil.readValue(content, ComputerPartDto.class);
        Assertions.assertThat(body).usingRecursiveComparison().isEqualTo(computerPartDto1);
    }

    @Test
    public void getAllByType() throws Exception {
        String content = perform(get(ProductController.REST_URL + "/by-type?type=" +
                computerPartDto1.getProductType().name()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();
        List<ComputerPartDto> computerPartDtos = JsonUtil.readValues(content, ComputerPartDto.class);
        Assertions.assertThat(computerPartDtos).usingRecursiveComparison().isEqualTo(computerPartDtoList);
    }

    @Test
    public void update() throws Exception {
        ComputerPartDto update = ProductTestData.getNew();
        update.setId(computerPartDto1.getId());
        perform(put(ProductController.REST_URL + "/" + update.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(update)))
                .andDo(print())
                .andExpect(status().isNoContent());
        ComputerPartDto updated = productService.get(update.getId());
        Assertions.assertThat(updated).usingRecursiveComparison().ignoringFields("producerName")
                .isEqualTo(update);
    }

    @Test
    public void deleteById() throws Exception {
        perform(delete(ProductController.REST_URL + "/" + computerPartDto1.getId()))
                .andDo(print())
                .andExpect(status().isNoContent());
        Assertions.assertThatThrownBy(() -> productService.get(computerPartDto1.getId()))
                .isExactlyInstanceOf(NotFoundException.class);
    }
}