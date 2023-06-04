package com.example.computershop.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class OpenApiUtil {

    public final static String PRODUCT_REQUEST_BODY_CREATE_DESCRIPTION = """
            Варианты свойств:
            - "productType": "HDD" c "hddCapacity": ?
            - "productType": "LAPTOP" c "laptopSize": ?
            - "productType": "DESKTOP_COMPUTER" с "desktopComputerFormFactor": "DESKTOP" или "NETTOP" или "MONOBLOCK"
            - "productType": "MONITOR" c "monitorDiagonal": ?,
            где ? - число 
            """;

    public static final String PRODUCT_REQUEST_BODY_CREATE_EXAMPLE = """
            {"serialNumber": "SERIAL_HDD_1000",
            "producerId": 1,
            "price": 1000,
            "quantityOnStock": 10,
            "productType": "HDD",
            "hddCapacity": 256}""";
}
