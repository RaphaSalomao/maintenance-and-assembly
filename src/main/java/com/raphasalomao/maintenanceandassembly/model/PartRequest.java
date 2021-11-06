package com.raphasalomao.maintenanceandassembly.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class PartRequest {

    private String name;
    private String quantity;
    private String price;
    private String type;
    private UUID id;

}
