package com.raphasalomao.maintenanceandassembly.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Part {

    private String name;
    private Integer quantity;
    private BigDecimal price;

}
