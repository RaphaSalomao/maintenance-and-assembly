package com.raphasalomao.maintenanceandassembly.repository;

import com.raphasalomao.maintenanceandassembly.model.entity.UpgradeRequest;
import com.raphasalomao.maintenanceandassembly.model.entity.User;
import com.raphasalomao.maintenanceandassembly.model.enumerated.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UpgradeRequestRepository extends JpaRepository<UpgradeRequest, UUID> {
    List<UpgradeRequest> findByStatusOrderByCode(Status pending);
    List<UpgradeRequest> findByUserOrderByCode(User user);
}
