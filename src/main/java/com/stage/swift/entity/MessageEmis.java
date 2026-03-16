package com.stage.swift.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(schema = "swift", name = "message_emis")
@IdClass(MessageEmis.MessageEmisPK.class)
public class MessageEmis {

    @Id
    @Column(name = "id_msg_emis")
    private Long idMsgEmis;
    @Id
    @Column(name = "id_vrt_emis_virement_emis")
    private Long idVrtEmisVirementEmis;
    @Id
    @Column(name = "id_sop_virement_emis")
    private Long idSopVirementEmis;
    @Id
    @Column(name = "id_statut_statut_virement_emis")
    private Long idStatutStatutVirementEmis;
    @Id
    @Column(name = "id_adresse_adresse_virement_emis")
    private Long idAdresseAdresseVirementEmis;
    @Id
    @Column(name = "code_bic_bic_virement_emis")
    private Long codeBicBicVirementEmis;
    @Id
    @Column(name = "code_msg_type_message_virement_emis")
    private Long codeMsgTypeMessageVirementEmis;

    @Column(name = "id_vrt_emis", nullable = false)
    private Long idVrtEmis;

    @Column(name = "reference", nullable = false, length = 35)
    private String reference;

    @Column(name = "sop", nullable = false)
    private Long sop;

    /** MESSAGE_EMIS has_one VIREMENT_EMIS (owning side with FK) */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "id_vrt_emis_virement_emis", referencedColumnName = "id_vrt_emis", insertable = false, updatable = false),
            @JoinColumn(name = "id_sop_virement_emis", referencedColumnName = "id_sop", insertable = false, updatable = false),
            @JoinColumn(name = "id_statut_statut_virement_emis", referencedColumnName = "id_statut", insertable = false, updatable = false),
            @JoinColumn(name = "id_adresse_adresse_virement_emis", referencedColumnName = "id_adresse", insertable = false, updatable = false),
            @JoinColumn(name = "code_bic_bic_virement_emis", referencedColumnName = "code_bic", insertable = false, updatable = false),
            @JoinColumn(name = "code_msg_type_message_virement_emis", referencedColumnName = "id_type_message", insertable = false, updatable = false)
    })
    private VirementEmis virementEmis;

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
    public VirementEmis getVirementEmis() { return virementEmis; }
    public void setVirementEmis(VirementEmis virementEmis) { this.virementEmis = virementEmis; }

    /** PK composite  */
    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode
    public static class MessageEmisPK implements Serializable {
        private static final long serialVersionUID = 1L;
        private Long idMsgEmis;
        private Long idVrtEmisVirementEmis;
        private Long idSopVirementEmis;
        private Long idStatutStatutVirementEmis;
        private Long idAdresseAdresseVirementEmis;
        private Long codeBicBicVirementEmis;
        private Long codeMsgTypeMessageVirementEmis;
    }
}
