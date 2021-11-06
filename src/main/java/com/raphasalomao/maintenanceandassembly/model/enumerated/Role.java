package com.raphasalomao.maintenanceandassembly.model.enumerated;

public enum Role {
    CUSTOMER,
    USER,
    ADMIN;

    public static Role fromString(String role) {
        switch (role) {
            case "CUSTOMER":
                return CUSTOMER;
            case "USER":
                return USER;
            case "ADMIN":
                return ADMIN;
            default:
                throw new IllegalArgumentException("Invalid role: " + role);
        }
    }
}
