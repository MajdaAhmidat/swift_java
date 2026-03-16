package com.stage.swift.dto.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO pour VIREMENT_EMIS (virement émis).
 * <p><b>Rôle :</b> Expose toutes les données du virement (montant, comptes, BIC, référence, UETR, etc.)
 * et les clés étrangères (SOP, statut, adresse, BIC, type message). Utilisé pour le CRUD virements émis.</p>
 */
public class VirementEmisDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idVrtEmis;
    private Long idSop;
    private Long idStatutStatut;
    private Long idAdresseAdresse;
    private Long codeBicBic;
    private Long codeMsgTypeMessage;
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
    private String statutRappro;
    private String statutSwift;

    public VirementEmisDTO() {}

    public Long getIdVrtEmis() { return idVrtEmis; }
    public void setIdVrtEmis(Long idVrtEmis) { this.idVrtEmis = idVrtEmis; }
    public Long getIdSop() { return idSop; }
    public void setIdSop(Long idSop) { this.idSop = idSop; }
    public Long getIdStatutStatut() { return idStatutStatut; }
    public void setIdStatutStatut(Long idStatutStatut) { this.idStatutStatut = idStatutStatut; }
    public Long getIdAdresseAdresse() { return idAdresseAdresse; }
    public void setIdAdresseAdresse(Long idAdresseAdresse) { this.idAdresseAdresse = idAdresseAdresse; }
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
    public String getBicOrdonnateur() { return bicOrdonnateur; }
    public void setBicOrdonnateur(String bicOrdonnateur) { this.bicOrdonnateur = bicOrdonnateur; }
    public String getBicBeneficiaire() { return bicBeneficiaire; }
    public void setBicBeneficiaire(String bicBeneficiaire) { this.bicBeneficiaire = bicBeneficiaire; }
    public String getUetr() { return uetr; }
    public void setUetr(String uetr) { this.uetr = uetr; }
    public String getEndToEnd() { return endToEnd; }
    public void setEndToEnd(String endToEnd) { this.endToEnd = endToEnd; }
    public String getStatutRappro() { return statutRappro; }
    public void setStatutRappro(String statutRappro) { this.statutRappro = statutRappro; }
    public String getStatutSwift() { return statutSwift; }
    public void setStatutSwift(String statutSwift) { this.statutSwift = statutSwift; }
}
