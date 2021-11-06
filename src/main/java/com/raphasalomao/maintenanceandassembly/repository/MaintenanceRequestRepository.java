package com.raphasalomao.maintenanceandassembly.repository;

import com.raphasalomao.maintenanceandassembly.model.entity.MaintenanceRequest;
import com.raphasalomao.maintenanceandassembly.model.entity.User;
import com.raphasalomao.maintenanceandassembly.model.enumerated.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MaintenanceRequestRepository extends JpaRepository<MaintenanceRequest, UUID> {
    List<MaintenanceRequest> findByStatus(Status status);

    List<MaintenanceRequest> findByUserOrderByCode(User user);
}
