package com.stage.swift.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(schema = "swift", name = "virement_recu")
@IdClass(VirementRecu.VirementRecuPK.class)
public class VirementRecu {

    @Id
    @Column(name = "id_vrt_recu")
    private Long idVrtRecu;
    @Id
    @Column(name = "id_statut")
    private Long idStatutStatut;
    @Id
    @Column(name = "id_adresse")
    private Long idAdresseAdresse;
    @Id
    @Column(name = "id_sop")
    private Long idSop;
    @Id
    @Column(name = "code_bic")
    private Long codeBicBic;
    @Id
    @Column(name = "id_type_message")
    private Long codeMsgTypeMessage;

    @Column(name = "reference", nullable = false, length = 35)
    private String reference;

    @Column(name = "denomination_bnf", nullable = false, length = 255)
    private String denominationBnf;

    @Column(name = "num_compte_bnf", nullable = false, length = 255)
    private String numCompteBnf;

    @Column(name = "num_compte_ord", nullable = false, length = 255)
    private String numCompteOrd;

    @Column(name = "denomination_ord", nullable = false, length = 255)
    private String denominationOrd;

    @Column(name = "montant", nullable = false, precision = 18, scale = 2)
    private BigDecimal montant;

    @Column(name = "date_valeur", nullable = false)
    private LocalDate dateValeur;

    @Column(name = "renseignement", nullable = false, length = 255)
    private String renseignement;

    @Column(name = "code_devise", nullable = false, length = 3)
    private String codeDevise;

    @Column(name = "date_integration", nullable = false)
    private LocalDate dateIntegration;

    @Column(name = "bic_ordonnateur", nullable = false, length = 255)
    private String bicOrdonnateur;

    @Column(name = "bic_beneficiaire", nullable = false, length = 255)
    private String bicBeneficiaire;

    @Column(name = "uetr", nullable = false, length = 254)
    private String uetr;

    @Column(name = "end_to_end", nullable = false, length = 35)
    private String endToEnd;

    /** Libellé statut rapprochement reçu : INTEGRE, RAPPROCHE, NON_RAPPROCHE (colonne statut_rapprochement) */
    @Column(name = "statut_rapprochement", length = 50)
    private String statutRecu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_sop", referencedColumnName = "id", insertable = false, updatable = false)
    private Sop sop;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_type_message", referencedColumnName = "code_msg", insertable = false, updatable = false)
    private TypeMessage typeMessage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_statut", referencedColumnName = "id_statut", insertable = false, updatable = false)
    private Statut statut;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_adresse", referencedColumnName = "id_adresse", insertable = false, updatable = false)
    private Adresse adresse;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code_bic", referencedColumnName = "code_bic", insertable = false, updatable = false)
    private Bic bic;

    /** VIREMENT_RECU peut être référencé par plusieurs MESSAGE_RECU. */
    @OneToMany(mappedBy = "virementRecu", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MessageRecu> messagesRecus = new ArrayList<>();

    /** VIREMENT_RECU has_many VIREMENT_RECU_HISTO */
    @OneToMany(mappedBy = "virementRecu", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VirementRecuHisto> virementsRecuHisto = new ArrayList<>();

    public Long getIdVrtRecu() { return idVrtRecu; }
    public void setIdVrtRecu(Long idVrtRecu) { this.idVrtRecu = idVrtRecu; }
    public Long getIdStatutStatut() { return idStatutStatut; }
    public void setIdStatutStatut(Long idStatutStatut) { this.idStatutStatut = idStatutStatut; }
    public Long getIdAdresseAdresse() { return idAdresseAdresse; }
    public void setIdAdresseAdresse(Long idAdresseAdresse) { this.idAdresseAdresse = idAdresseAdresse; }
    public Long getIdSop() { return idSop; }
    public void setIdSop(Long idSop) { this.idSop = idSop; }
    public Long getCodeBicBic() { return codeBicBic; }
    public void setCodeBicBic(Long codeBicBic) { this.codeBicBic = codeBicBic; }
    public Long getCodeMsgTypeMessage() { return codeMsgTypeMessage; }
    public void setCodeMsgTypeMessage(Long codeMsgTypeMessage) { this.codeMsgTypeMessage = codeMsgTypeMessage; }
    public String getReference() { return reference; }
    public void setReference(String reference) { this.reference = reference; }
    public String getDenominationBnf() { return denominationBnf; }
    public void setDenominationBnf(String denominationBnf) { this.denominationBnf = denominationBnf; }
    public String getNumCompteBnf() { return numCompteBnf; }
    public void setNumCompteBnf(String numCompteBnf) { this.numCompteBnf = numCompteBnf; }
    public String getNumCompteOrd() { return numCompteOrd; }
    public void setNumCompteOrd(String numCompteOrd) { this.numCompteOrd = numCompteOrd; }
    public String getDenominationOrd() { return denominationOrd; }
    public void setDenominationOrd(String denominationOrd) { this.denominationOrd = denominationOrd; }
    public BigDecimal getMontant() { return montant; }
    public void setMontant(BigDecimal montant) { this.montant = montant; }
    public LocalDate getDateValeur() { return dateValeur; }
    public void setDateValeur(LocalDate dateValeur) { this.dateValeur = dateValeur; }
    public String getRenseignement() { return renseignement; }
    public void setRenseignement(String renseignement) { this.renseignement = renseignement; }
    public String getCodeDevise() { return codeDevise; }
    public void setCodeDevise(String codeDevise) { this.codeDevise = codeDevise; }
    public LocalDate getDateIntegration() { return dateIntegration; }
    public void setDateIntegration(LocalDate dateIntegration) { this.dateIntegration = dateIntegration; }
    public Long getIdTypeMessage() { return codeMsgTypeMessage; }
    public void setIdTypeMessage(Long idTypeMessage) { this.codeMsgTypeMessage = idTypeMessage; }
    public String getBicOrdonnateur() { return bicOrdonnateur; }
    public void setBicOrdonnateur(String bicOrdonnateur) { this.bicOrdonnateur = bicOrdonnateur; }
    public String getBicBeneficiaire() { return bicBeneficiaire; }
    public void setBicBeneficiaire(String bicBeneficiaire) { this.bicBeneficiaire = bicBeneficiaire; }
    public Long getIdStatut() { return idStatutStatut; }
    public void setIdStatut(Long idStatut) { this.idStatutStatut = idStatut; }
    public Long getIdAdresse() { return idAdresseAdresse; }
    public void setIdAdresse(Long idAdresse) { this.idAdresseAdresse = idAdresse; }
    public String getUetr() { return uetr; }
    public void setUetr(String uetr) { this.uetr = uetr; }
    public String getEndToEnd() { return endToEnd; }
    public void setEndToEnd(String endToEnd) { this.endToEnd = endToEnd; }
    public String getStatutRecu() { return statutRecu; }
    public void setStatutRecu(String statutRecu) { this.statutRecu = statutRecu; }
    public Sop getSop() { return sop; }
    public void setSop(Sop sop) { this.sop = sop; }
    public TypeMessage getTypeMessage() { return typeMessage; }
    public void setTypeMessage(TypeMessage typeMessage) { this.typeMessage = typeMessage; }
    public Statut getStatut() { return statut; }
    public void setStatut(Statut statut) { this.statut = statut; }
    public Adresse getAdresse() { return adresse; }
    public void setAdresse(Adresse adresse) { this.adresse = adresse; }
    public Bic getBic() { return bic; }
    public void setBic(Bic bic) { this.bic = bic; }
    public List<MessageRecu> getMessagesRecus() { return messagesRecus; }
    public void setMessagesRecus(List<MessageRecu> messagesRecus) { this.messagesRecus = messagesRecus; }
    public List<VirementRecuHisto> getVirementsRecuHisto() { return virementsRecuHisto; }
    public void setVirementsRecuHisto(List<VirementRecuHisto> virementsRecuHisto) { this.virementsRecuHisto = virementsRecuHisto; }

    /** PK composite  */
    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode
    public static class VirementRecuPK implements Serializable {
        private static final long serialVersionUID = 1L;
        private Long idVrtRecu;
        private Long idStatutStatut;
        private Long idAdresseAdresse;
        private Long idSop;
        private Long codeBicBic;
        private Long codeMsgTypeMessage;
    }
}
