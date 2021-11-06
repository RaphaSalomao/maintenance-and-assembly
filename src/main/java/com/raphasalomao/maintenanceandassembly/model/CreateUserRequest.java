package com.raphasalomao.maintenanceandassembly.model;

import com.raphasalomao.maintenanceandassembly.model.enumerated.Role;
import lombok.*;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class CreateUserRequest {

    private String email;
    private String password;
    private String name;
    private String phone;
    private Role role;

}
