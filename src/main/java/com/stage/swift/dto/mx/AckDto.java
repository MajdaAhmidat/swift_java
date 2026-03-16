package com.stage.swift.dto.mx;

/**
 * DTO pour les messages ACK (SAA).
 * Contient la référence du message (BizMsgIdr / MsgId) et le statut réseau (ACK, NACK, ...).
 */
public class AckDto {

    private String reference;
    private String status;

    public AckDto() {
    }

    public AckDto(String reference, String status) {
        this.reference = reference;
        this.status = status;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

