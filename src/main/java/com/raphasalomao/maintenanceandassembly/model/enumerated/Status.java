package com.raphasalomao.maintenanceandassembly.model.enumerated;

import lombok.Data;

public enum Status {

    PENDING("Orçamento Pendente"),
    WAITING_FOR_APPROVAL("Aguardando Aprovação do Orçamento"),
    APPROVED("Orçamento Aprovado"),
    REJECTED("Orçamento Rejeitado");

    private String description;

    Status(String s) {
        this.description = s;
    }

    public static Status getStatus(String status) {
        switch (status) {
            case "PENDING":
                return PENDING;
            case "WAITING_FOR_APPROVAL":
                return WAITING_FOR_APPROVAL;
            case "APPROVED":
                return APPROVED;
            case "REJECTED":
                return REJECTED;
            default:
                return null;
        }
    }

    public String getDescription() {
        return this.description;
    }

}
