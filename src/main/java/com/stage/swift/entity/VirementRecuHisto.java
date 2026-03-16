package com.stage.swift.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;

@Entity
@Table(schema = "swift", name = "virement_recu_histo")
@IdClass(VirementRecuHisto.VirementRecuHistoPK.class)
public class VirementRecuHisto {

    @Id
    @Column(name = "id_vrt_recu_histo")
    private Long idVrtRecuHisto;
    @Id
    @Column(name = "id_vrt_recu_virement_recu")
    private Long idVrtRecuVirementRecu;
    @Id
    @Column(name = "id_statut")
    private Long idStatutStatutVirementRecu;
    @Id
    @Column(name = "id_adresse")
    private Long idAdresseAdresseVirementRecu;
    @Id
    @Column(name = "id_sop_virement_recu")
    private Long idSopVirementRecu;
    @Id
    @Column(name = "code_bic")
    private Long codeBicBicVirementRecu;
    @Id
    @Column(name = "id_type_message")
    private Long codeMsgTypeMessageVirementRecu;

    @Column(name = "id_vrt_emis", nullable = false)
    private Long idVrtEmis;

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

    @Column(name = "date_historisation", nullable = false)
    private OffsetDateTime dateHistorisation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "id_vrt_recu_virement_recu", referencedColumnName = "id_vrt_recu", insertable = false, updatable = false),
            @JoinColumn(name = "id_statut", referencedColumnName = "id_statut", insertable = false, updatable = false),
            @JoinColumn(name = "id_adresse", referencedColumnName = "id_adresse", insertable = false, updatable = false),
            @JoinColumn(name = "id_sop_virement_recu", referencedColumnName = "id_sop", insertable = false, updatable = false),
            @JoinColumn(name = "code_bic", referencedColumnName = "code_bic", insertable = false, updatable = false),
            @JoinColumn(name = "id_type_message", referencedColumnName = "id_type_message", insertable = false, updatable = false)
    })
    private VirementRecu virementRecu;

    public Long getIdVrtRecuHisto() { return idVrtRecuHisto; }
    public void setIdVrtRecuHisto(Long idVrtRecuHisto) { this.idVrtRecuHisto = idVrtRecuHisto; }
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
    public Long getIdVrtEmis() { return idVrtEmis; }
    public void setIdVrtEmis(Long idVrtEmis) { this.idVrtEmis = idVrtEmis; }
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
    public String getBicOrdonnateur() { return bicOrdonnateur; }
    public void setBicOrdonnateur(String bicOrdonnateur) { this.bicOrdonnateur = bicOrdonnateur; }
    public String getBicBeneficiaire() { return bicBeneficiaire; }
    public void setBicBeneficiaire(String bicBeneficiaire) { this.bicBeneficiaire = bicBeneficiaire; }
    public String getUetr() { return uetr; }
    public void setUetr(String uetr) { this.uetr = uetr; }
    public String getEndToEnd() { return endToEnd; }
    public void setEndToEnd(String endToEnd) { this.endToEnd = endToEnd; }
    public OffsetDateTime getDateHistorisation() { return dateHistorisation; }
    public void setDateHistorisation(OffsetDateTime dateHistorisation) { this.dateHistorisation = dateHistorisation; }
    public VirementRecu getVirementRecu() { return virementRecu; }
    public void setVirementRecu(VirementRecu virementRecu) { this.virementRecu = virementRecu; }

    /** PK composite  */
    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode
    public static class VirementRecuHistoPK implements Serializable {
        private static final long serialVersionUID = 1L;
        private Long idVrtRecuHisto;
        private Long idVrtRecuVirementRecu;
        private Long idStatutStatutVirementRecu;
        private Long idAdresseAdresseVirementRecu;
        private Long idSopVirementRecu;
        private Long codeBicBicVirementRecu;
        private Long codeMsgTypeMessageVirementRecu;
    }
}
