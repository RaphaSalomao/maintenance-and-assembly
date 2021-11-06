package com.raphasalomao.maintenanceandassembly.model.entity;

import com.raphasalomao.maintenanceandassembly.model.enumerated.Role;
import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "t_user")
@Data
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class User  {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column
    private String email;

    @Column
    private String password;

    @Column
    private String name;

    @Column
    private String phone;

    @Column
    @Enumerated(EnumType.STRING)
    private Role role;


}
