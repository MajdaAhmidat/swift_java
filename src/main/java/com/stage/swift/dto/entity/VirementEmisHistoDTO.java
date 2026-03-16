package com.stage.swift.dto.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;

/**
 * DTO pour VIREMENT_EMIS_HISTO (historique des virements émis).
 * <p><b>Rôle :</b> Expose une snapshot des données du virement émis à une date d'historisation. Utilisé pour le CRUD et l'audit.</p>
 */
public class VirementEmisHistoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idVrtEmisHisto;
    private Long idVrtEmisVirementEmis;
    private Long idSopVirementEmis;
    private Long idStatutStatutVirementEmis;
    private Long idAdresseAdresseVirementEmis;
    private Long codeBicBicVirementEmis;
    private Long codeMsgTypeMessageVirementEmis;
    private Long idVrtEmis;
    private String reference;
    private String denominationBnf;
    private String numCompteBnf;
    private String numCompteOrd;
    private String denominationOrd;
    private BigDecimal montant;
    private LocalDate dateValeur;
    private String renseignement;
    private String codeDevise;
    private LocalDate dateIntegration;
    private String bicOrdonnateur;
    private String bicBeneficiaire;
    private String uetr;
    private String endToEnd;
    private OffsetDateTime dateHistorisation;

    public VirementEmisHistoDTO() {}

    public Long getIdVrtEmisHisto() { return idVrtEmisHisto; }
    public void setIdVrtEmisHisto(Long idVrtEmisHisto) { this.idVrtEmisHisto = idVrtEmisHisto; }
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
