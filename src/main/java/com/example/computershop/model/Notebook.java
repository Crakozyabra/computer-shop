package com.example.computershop.model;

import com.example.computershop.model.enums.NotebookDiagonalSize;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity

public class Notebook extends BaseComputerPart{

    @Enumerated(EnumType.ORDINAL)
    private NotebookDiagonalSize notebookDiagonalSize;

    public Notebook(Long id, String serialNumber, Long price, Long quantityOnStock, Producer producer,
                    NotebookDiagonalSize notebookDiagonalSize) {
        super(id, serialNumber, price, quantityOnStock, producer);
        this.notebookDiagonalSize = notebookDiagonalSize;
    }
}
