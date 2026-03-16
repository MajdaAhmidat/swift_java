package com.stage.swift.dto.entity;

import java.io.Serializable;

/**
 * DTO pour MESSAGE_EMIS (message émis lié à un virement émis).
 * <p><b>Rôle :</b> Expose les clés de liaison et métadonnées (référence). Utilisé pour le CRUD messages émis.</p>
 */
public class MessageEmisDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idMsgEmis;
    private Long idVrtEmisVirementEmis;
    private Long idSopVirementEmis;
    private Long idStatutStatutVirementEmis;
    private Long idAdresseAdresseVirementEmis;
    private Long codeBicBicVirementEmis;
    private Long codeMsgTypeMessageVirementEmis;
    private Long idVrtEmis;
    private String reference;
    private Long sop;

    public MessageEmisDTO() {}

    public Long getIdMsgEmis() { return idMsgEmis; }
    public void setIdMsgEmis(Long idMsgEmis) { this.idMsgEmis = idMsgEmis; }
    public Long getIdVrtEmisVirementEmis() { return idVrtEmisVirementEmis; }
    public void setIdVrtEmisVirementEmis(Long idVrtEmisVirementEmis) { this.idVrtEmisVirementEmis = idVrtEmisVirementEmis; }
    public Long getIdSopVirementEmis() { return idSopVirementEmis; }
    public void setIdSopVirementEmis(Long idSopVirementEmis) { this.idSopVirementEmis = idSopVirementEmis; }
    public Long getIdStatutStatutVirementEmis() { return idStatutStatutVirementEmis; }
    public void setIdStatutStatutVirementEmis(Long idStatutStatutVirementEmis) { this.idStatutStatutVirementEmis = idStatutStatutVirementEmis; }
    public Long getIdAdresseAdresseVirementEmis() { return idAdresseAdresseVirementEmis; }
    public void setIdAdresseAdresseVirementEmis(Long idAdresseAdresseVirementEmis) { this.idAdresseAdresseVirementEmis = idAdresseAdresseVirementEmis; }
    public Long getCodeBicBicVirementEmis() { return codeBicBicVirementEmis; }
    public void setCodeBicBicVirementEmis(Long codeBicBicVirementEmis) { this.codeBicBicVirementEmis = codeBicBicVirementEmis; }
    public Long getCodeMsgTypeMessageVirementEmis() { return codeMsgTypeMessageVirementEmis; }
    public void setCodeMsgTypeMessageVirementEmis(Long codeMsgTypeMessageVirementEmis) { this.codeMsgTypeMessageVirementEmis = codeMsgTypeMessageVirementEmis; }
    public Long getIdVrtEmis() { return idVrtEmis; }
    public void setIdVrtEmis(Long idVrtEmis) { this.idVrtEmis = idVrtEmis; }
    public String getReference() { return reference; }
    public void setReference(String reference) { this.reference = reference; }
    public Long getSop() { return sop; }
    public void setSop(Long sop) { this.sop = sop; }
}
