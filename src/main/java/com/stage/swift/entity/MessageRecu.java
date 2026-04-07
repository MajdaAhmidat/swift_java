package com.stage.swift.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(schema = "swift", name = "message_recu")
@IdClass(MessageRecu.MessageRecuPK.class)
public class MessageRecu {

    @Id
    @Column(name = "id_msg_recu")
    private Long idMsgRecu;
    @Id
    @Column(name = "id_vrt_recu_virement_recu")
    private Long idVrtRecuVirementRecu;
    @Id
    @Column(name = "id_statut_statut_virement_recu")
    private Long idStatutStatutVirementRecu;
    @Id
    @Column(name = "id_adresse_adresse_virement_recu")
    private Long idAdresseAdresseVirementRecu;
    @Id
    @Column(name = "id_sop_virement_recu")
    private Long idSopVirementRecu;
    @Id
    @Column(name = "code_bic_bic_virement_recu")
    private Long codeBicBicVirementRecu;
    @Id
    @Column(name = "code_msg_type_message_virement_recu")
    private Long codeMsgTypeMessageVirementRecu;

    @Column(name = "reference", nullable = false, length = 35)
    private String reference;

    @Column(name = "sop", nullable = false)
    private Long sop;

    @Column(name = "nom", length = 255)
    private String nom;

    @Column(name = "path", length = 1024)
    private String path;

    /** Plusieurs MESSAGE_RECU peuvent pointer vers le même VIREMENT_RECU. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "id_vrt_recu_virement_recu", referencedColumnName = "id_vrt_recu", insertable = false, updatable = false),
            @JoinColumn(name = "id_statut_statut_virement_recu", referencedColumnName = "id_statut", insertable = false, updatable = false),
            @JoinColumn(name = "id_adresse_adresse_virement_recu", referencedColumnName = "id_adresse", insertable = false, updatable = false),
            @JoinColumn(name = "id_sop_virement_recu", referencedColumnName = "id_sop", insertable = false, updatable = false),
            @JoinColumn(name = "code_bic_bic_virement_recu", referencedColumnName = "code_bic", insertable = false, updatable = false),
            @JoinColumn(name = "code_msg_type_message_virement_recu", referencedColumnName = "id_type_message", insertable = false, updatable = false)
    })
    private VirementRecu virementRecu;

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
    public VirementRecu getVirementRecu() { return virementRecu; }
    public void setVirementRecu(VirementRecu virementRecu) { this.virementRecu = virementRecu; }

    /** PK composite  */
    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode
    public static class MessageRecuPK implements Serializable {
        private static final long serialVersionUID = 1L;
        private Long idMsgRecu;
        private Long idVrtRecuVirementRecu;
        private Long idStatutStatutVirementRecu;
        private Long idAdresseAdresseVirementRecu;
        private Long idSopVirementRecu;
        private Long codeBicBicVirementRecu;
        private Long codeMsgTypeMessageVirementRecu;
    }
}
