package com.raphasalomao.maintenanceandassembly.service;

import com.raphasalomao.maintenanceandassembly.model.Part;
import com.raphasalomao.maintenanceandassembly.model.PartRequest;
import com.raphasalomao.maintenanceandassembly.model.Parts;
import com.raphasalomao.maintenanceandassembly.model.enumerated.Status;
import com.raphasalomao.maintenanceandassembly.repository.MaintenanceRequestRepository;
import com.raphasalomao.maintenanceandassembly.repository.UpgradeRequestRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;

@Service
@AllArgsConstructor
public class PartService {

    private final MaintenanceRequestRepository maintenanceRequestRepository;
    private final UpgradeRequestRepository upgradeRequestRepository;
    public void createPart(PartRequest part) {
        String[] partNames = part.getName().split(",");
        String[] partPrices = part.getPrice().split(",");
        String[] partQuantities = part.getQuantity().split(",");
        ArrayList<Part> partList = new ArrayList<>();
        for (int i = 0; i < partNames.length; i++) {
            partList.add(
                    Part.builder()
                            .name(partNames[i])
                            .price(new BigDecimal(partPrices[i]))
                            .quantity(Integer.valueOf(partQuantities[i]))
                            .build());
        }
        var total = new BigDecimal(0);
        for (Part p : partList) {
            total = total.add(p.getPrice().multiply(new BigDecimal(p.getQuantity())));
        }
        var parts = Parts.builder()
                .part(partList)
                .total(total)
                .build();
        if (part.getType().equals("MAINTENANCE")) {
            var maintenanceRequest = maintenanceRequestRepository.findById(part.getId()).orElseThrow(
                    () -> new EntityNotFoundException("Maintenance request not found"));
            maintenanceRequestRepository.save(maintenanceRequest.toBuilder()
                    .parts(parts)
                    .status(Status.WAITING_FOR_APPROVAL)
                    .build());
        } else {
            var upgradeRequest = upgradeRequestRepository.findById(part.getId()).orElseThrow(
                    () -> new EntityNotFoundException("Upgrade request not found"));
            upgradeRequestRepository.save(upgradeRequest.toBuilder()
                    .parts(parts)
                    .status(Status.WAITING_FOR_APPROVAL)
                    .build());
        }
    }
}
