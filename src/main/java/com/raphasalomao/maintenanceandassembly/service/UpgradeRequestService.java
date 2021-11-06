package com.raphasalomao.maintenanceandassembly.service;

import com.raphasalomao.maintenanceandassembly.model.Parts;
import com.raphasalomao.maintenanceandassembly.model.UpgradeInfo;
import com.raphasalomao.maintenanceandassembly.model.entity.UpgradeRequest;
import com.raphasalomao.maintenanceandassembly.model.enumerated.Status;
import com.raphasalomao.maintenanceandassembly.repository.UpgradeRequestRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class UpgradeRequestService {

    private final UpgradeRequestRepository upgradeRequestRepository;
    private final UserService userService;

    public UpgradeRequest create(UpgradeInfo upgradeInfo) throws Exception {
        return upgradeRequestRepository.save(UpgradeRequest.builder()
                        .upgradeInfo(upgradeInfo.toBuilder()
                                .softwareHardToRun(upgradeInfo.getSoftwareHardToRun().replaceFirst(",",""))
                                .storageAmount(this.validateStorageAmount(upgradeInfo.getStorageAmount()))
                                .build())
                        .user(userService.retrievePrincipal())
                        .status(Status.PENDING)
                        .code((int) upgradeRequestRepository.findAll().stream().count() + 1000)
                        .parts(Parts.builder()
                                .total(BigDecimal.ZERO)
                                .part(Collections.emptyList())
                                .build())
                        .build());
    }

    private String validateStorageAmount(final String storageAmount) {
        log.warn("Storage amount: {}", storageAmount);
        try {
            Integer amount = Integer.parseInt(storageAmount.replace(",",""));
            return amount.toString();
        } catch (NumberFormatException e) {
            return "0";
        }
    }

    public List<UpgradeRequest> findPenndingRequests() {
        return upgradeRequestRepository.findByStatusOrderByCode(Status.PENDING);
    }

    public List<UpgradeRequest> findPrincipalRequests() throws Exception {
        return upgradeRequestRepository.findByUserOrderByCode(userService.retrievePrincipal());
    }

    public void approve(UUID id) throws Exception {
        var entity = upgradeRequestRepository.findById(id)
                .orElseThrow(() -> new Exception("Upgrade request not found"));
        entity.setStatus(Status.APPROVED);
        upgradeRequestRepository.save(entity);
    }

    public void cancel(UUID id) throws Exception {
        var entity = upgradeRequestRepository.findById(id)
                .orElseThrow(() -> new Exception("Upgrade request not found"));
        entity.setStatus(Status.REJECTED);
        upgradeRequestRepository.save(entity);
    }
}
