package com.stage.swift.dto.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;

/**
 * DTO pour VIREMENT_RECU_HISTO (historique des virements reçus).
 * <p><b>Rôle :</b> Expose une snapshot du virement reçu à une date d'historisation. Utilisé pour le CRUD et l'audit.</p>
 */
public class VirementRecuHistoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idVrtRecuHisto;
    private Long idVrtRecuVirementRecu;
    private Long idStatutStatutVirementRecu;
    private Long idAdresseAdresseVirementRecu;
    private Long idSopVirementRecu;
    private Long codeBicBicVirementRecu;
    private Long codeMsgTypeMessageVirementRecu;
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
    private Long idTypeMessage;
    private String bicOrdonnateur;
    private String bicBeneficiaire;
    private Long idStatut;
    private Long idAdresse;
    private String uetr;
    private String endToEnd;
    private OffsetDateTime dateHistorisation;

    public VirementRecuHistoDTO() {}

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
    public Long getIdTypeMessage() { return idTypeMessage; }
    public void setIdTypeMessage(Long idTypeMessage) { this.idTypeMessage = idTypeMessage; }
    public String getBicOrdonnateur() { return bicOrdonnateur; }
    public void setBicOrdonnateur(String bicOrdonnateur) { this.bicOrdonnateur = bicOrdonnateur; }
    public String getBicBeneficiaire() { return bicBeneficiaire; }
    public void setBicBeneficiaire(String bicBeneficiaire) { this.bicBeneficiaire = bicBeneficiaire; }
    public Long getIdStatut() { return idStatut; }
    public void setIdStatut(Long idStatut) { this.idStatut = idStatut; }
    public Long getIdAdresse() { return idAdresse; }
    public void setIdAdresse(Long idAdresse) { this.idAdresse = idAdresse; }
    public String getUetr() { return uetr; }
    public void setUetr(String uetr) { this.uetr = uetr; }
    public String getEndToEnd() { return endToEnd; }
    public void setEndToEnd(String endToEnd) { this.endToEnd = endToEnd; }
    public OffsetDateTime getDateHistorisation() { return dateHistorisation; }
    public void setDateHistorisation(OffsetDateTime dateHistorisation) { this.dateHistorisation = dateHistorisation; }
}
