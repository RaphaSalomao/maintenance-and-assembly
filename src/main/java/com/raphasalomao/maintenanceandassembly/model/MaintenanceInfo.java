package com.raphasalomao.maintenanceandassembly.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceInfo {

    private String isMachineTurningOn;
    private String howManyTimeSincePreventiveMaintenance;
    private String willItBeCompleteCleaning;
    private String powerButtonBeep;
    private String additionalInfo;

}
