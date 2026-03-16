package com.stage.swift.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;

@Entity
@Table(schema = "swift", name = "virement_emis_histo")
public class VirementEmisHisto {

    @Id
    @Column(name = "id_vrt_emis_histo")
    private Long idVrtEmisHisto;

    @Column(name = "id_vrt_emis", nullable = false)
    private Long idVrtEmis;

    @Column(name = "id_sop", nullable = false)
    private Long idSop;

    @Column(name = "code_bic", nullable = false)
    private Long codeBic;

    // Colonne technique de clé étrangère vers virement_emis (NOT NULL en base)
    // On la renseigne avec la même valeur que id_vrt_emis pour respecter la contrainte,
    // sans ajouter de notion de statut dans l'historique.
    @Column(name = "id_vrt_emis_virement_emis", nullable = false)
    private Long idVrtEmisVirementEmis;

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

    public Long getIdVrtEmisHisto() { return idVrtEmisHisto; }
    public void setIdVrtEmisHisto(Long idVrtEmisHisto) { this.idVrtEmisHisto = idVrtEmisHisto; }
    public Long getIdVrtEmis() { return idVrtEmis; }
    public void setIdVrtEmis(Long idVrtEmis) { this.idVrtEmis = idVrtEmis; }
    public Long getIdSop() { return idSop; }
    public void setIdSop(Long idSop) { this.idSop = idSop; }
    public Long getCodeBic() { return codeBic; }
    public void setCodeBic(Long codeBic) { this.codeBic = codeBic; }
    public Long getIdVrtEmisVirementEmis() { return idVrtEmisVirementEmis; }
    public void setIdVrtEmisVirementEmis(Long idVrtEmisVirementEmis) { this.idVrtEmisVirementEmis = idVrtEmisVirementEmis; }
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
}
