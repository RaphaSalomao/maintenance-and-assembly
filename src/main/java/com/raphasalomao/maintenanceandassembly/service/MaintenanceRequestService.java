package com.raphasalomao.maintenanceandassembly.service;

import com.raphasalomao.maintenanceandassembly.model.MaintenanceInfo;
import com.raphasalomao.maintenanceandassembly.model.Parts;
import com.raphasalomao.maintenanceandassembly.model.entity.MaintenanceRequest;
import com.raphasalomao.maintenanceandassembly.model.enumerated.Status;
import com.raphasalomao.maintenanceandassembly.repository.MaintenanceRequestRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class MaintenanceRequestService {

    private final UserService userService;
    private final MaintenanceRequestRepository maintenanceRequestRepository;

    public MaintenanceRequest create(final MaintenanceInfo maintenanceInfo) throws Exception {
        return maintenanceRequestRepository.save(MaintenanceRequest.builder()
                        .maintenanceInfo(maintenanceInfo.toBuilder()
                                .isMachineTurningOn(maintenanceInfo.getIsMachineTurningOn().replaceFirst(",",""))
                                .howManyTimeSincePreventiveMaintenance(maintenanceInfo.getHowManyTimeSincePreventiveMaintenance().replaceFirst(",",""))
                                .build())
                        .user(userService.retrievePrincipal())
                        .status(Status.PENDING)
                        .code((int) maintenanceRequestRepository.findAll().stream().count() + 1000)
                        .parts(Parts.builder()
                                .total(BigDecimal.ZERO)
                                .part(Collections.emptyList())
                                .build())
                        .build());
    }

    public List<MaintenanceRequest> findPenndingRequests() {
        return maintenanceRequestRepository.findByStatus(Status.PENDING);
    }

    public List<MaintenanceRequest> findPrincipalRequests() throws Exception {
        return maintenanceRequestRepository.findByUserOrderByCode(userService.retrievePrincipal());
    }

    public void approve(UUID id) throws Exception {
        var entity = maintenanceRequestRepository.findById(id)
                .orElseThrow(() -> new Exception("Maintenance request not found"));
        entity.setStatus(Status.APPROVED);
        maintenanceRequestRepository.save(entity);
    }

    public void cancel(UUID id) throws Exception {
        var entity = maintenanceRequestRepository.findById(id)
                .orElseThrow(() -> new Exception("Maintenance request not found"));
        entity.setStatus(Status.REJECTED);
        maintenanceRequestRepository.save(entity);
    }
}
