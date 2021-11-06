package com.raphasalomao.maintenanceandassembly.model.entity;

import com.raphasalomao.maintenanceandassembly.model.MaintenanceInfo;
import com.raphasalomao.maintenanceandassembly.model.Parts;
import com.raphasalomao.maintenanceandassembly.model.enumerated.Status;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.util.UUID;

@TypeDefs({
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
@Entity
@Table(name = "maintenance_request")
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @OneToOne(fetch = FetchType.EAGER)
    private User user;

    @Type(type = "jsonb")
    @Column(name = "maintenance_info", columnDefinition = "jsonb")
    private MaintenanceInfo maintenanceInfo;

    @Column
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column
    private Integer code;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private Parts parts;
}
