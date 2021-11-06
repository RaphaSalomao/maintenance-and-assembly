package com.raphasalomao.maintenanceandassembly.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class UpgradeInfo {

    private String isMachineBusinessOrPersonal;
    private String softwareHardToRun;
    private String storageAmount;
    private String additionalInfo;

}
