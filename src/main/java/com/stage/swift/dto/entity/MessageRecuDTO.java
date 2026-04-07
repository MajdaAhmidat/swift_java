package com.stage.swift.dto.entity;

import java.io.Serializable;

/**
 * DTO pour MESSAGE_RECU (message reçu lié à un virement reçu).
 * <p><b>Rôle :</b> Expose les clés de liaison et métadonnées (référence). Utilisé pour le CRUD messages reçus.</p>
 */
public class MessageRecuDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idMsgRecu;
    private Long idVrtRecuVirementRecu;
    private Long idStatutStatutVirementRecu;
    private Long idAdresseAdresseVirementRecu;
    private Long idSopVirementRecu;
    private Long codeBicBicVirementRecu;
    private Long codeMsgTypeMessageVirementRecu;
    private String reference;
    private Long sop;
    private String nom;
    private String path;

    public MessageRecuDTO() {
    }

    public Long getIdMsgRecu() { return idMsgRecu; }
    public void setIdMsgRecu(Long idMsgRecu) { this.idMsgRecu = idMsgRecu; }
    public Long getIdVrtRecuVirementRecu() { return idVrtRecuVirementRecu; }
    public void setIdVrtRecuVirementRecu(Long idVrtRecuVirementRecu) { this.idVrtRecuVirementRecu = idVrtRecuVirementRecu; }
    public Long getIdStatutStatutVirementRecu() { return idStatutStatutVirementRecu; }
    public void setIdStatutStatutVirementRecu(Long idStatutStatutVirementRecu) { this.idStatutStatutVirementRecu = idStatutStatutVirementRecu; }
    public Long getIdAdresseAdresseVirementRecu() { return idAdresseAdresseVirementRecu; }
    public void setIdAdresseAdresseVirementRecu(Long idAdresseAdresseVirementRecu) { this.idAdresseAdresseVirementRecu = idAdresseAdresseVirementRecu; }
    public Long getIdSopVirementRecu() { return idSopVirementRecu; }
    public void setIdSopVirementRecu(Long idSopVirementRecu) { this.idSopVirementRecu = idSopVirementRecu; }
    public Long getCodeBicBicVirementRecu() { return codeBicBicVirementRecu; }
    public void setCodeBicBicVirementRecu(Long codeBicBicVirementRecu) { this.codeBicBicVirementRecu = codeBicBicVirementRecu; }
    public Long getCodeMsgTypeMessageVirementRecu() { return codeMsgTypeMessageVirementRecu; }
    public void setCodeMsgTypeMessageVirementRecu(Long codeMsgTypeMessageVirementRecu) { this.codeMsgTypeMessageVirementRecu = codeMsgTypeMessageVirementRecu; }
    public String getReference() { return reference; }
    public void setReference(String reference) { this.reference = reference; }
    public Long getSop() { return sop; }
    public void setSop(Long sop) { this.sop = sop; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getPath() { return path; }
    public void setPath(String path) { this.path = path; }
}
