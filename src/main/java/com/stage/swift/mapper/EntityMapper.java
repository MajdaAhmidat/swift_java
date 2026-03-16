package com.stage.swift.mapper;

import com.stage.swift.dto.entity.*;
import com.stage.swift.entity.*;
import org.springframework.stereotype.Component;

@Component
public class EntityMapper {

    public RoleDTO toDTO(Role e) {
        if (e == null) return null;
        RoleDTO dto = new RoleDTO();
        dto.setIdRole(e.getIdRole());
        dto.setCode(e.getCode());
        dto.setLabel(e.getLabel());
        return dto;
    }

    public Role toEntity(RoleDTO dto) {
        if (dto == null) return null;
        Role e = new Role();
        e.setIdRole(dto.getIdRole());
        e.setCode(dto.getCode());
        e.setLabel(dto.getLabel());
        return e;
    }

    public UtilisateurDTO toDTO(Utilisateur e) {
        if (e == null) return null;
        UtilisateurDTO dto = new UtilisateurDTO();
        dto.setIdUser(e.getIdUser());
        dto.setIdRoleRole(e.getIdRoleRole());
        dto.setLogin(e.getLogin());
        dto.setMotdepasse(e.getMotdepasse());
        dto.setRoleId(e.getRoleId());
        dto.setActif(e.getActif());
        dto.setCreatedAt(e.getCreatedAt());
        if (e.getRole() != null) dto.setRoleCode(e.getRole().getCode());
        return dto;
    }

    public Utilisateur toEntity(UtilisateurDTO dto) {
        if (dto == null) return null;
        Utilisateur e = new Utilisateur();
        e.setIdUser(dto.getIdUser());
        e.setIdRoleRole(dto.getIdRoleRole());
        e.setLogin(dto.getLogin());
        e.setMotdepasse(dto.getMotdepasse());
        e.setRoleId(dto.getRoleId());
        e.setActif(dto.getActif() != null ? dto.getActif() : true);
        e.setCreatedAt(dto.getCreatedAt());
        return e;
    }

    public AdresseDTO toDTO(Adresse e) {
        if (e == null) return null;
        AdresseDTO dto = new AdresseDTO();
        dto.setIdAdresse(e.getIdAdresse());
        dto.setLigne1(e.getLigne1());
        dto.setLigne2(e.getLigne2());
        dto.setVille(e.getVille());
        dto.setCodePostal(e.getCodePostal());
        dto.setPays(e.getPays());
        return dto;
    }

    public Adresse toEntity(AdresseDTO dto) {
        if (dto == null) return null;
        Adresse e = new Adresse();
        e.setIdAdresse(dto.getIdAdresse());
        e.setLigne1(dto.getLigne1());
        e.setLigne2(dto.getLigne2());
        e.setVille(dto.getVille());
        e.setCodePostal(dto.getCodePostal());
        e.setPays(dto.getPays());
        return e;
    }

    public BicDTO toDTO(Bic e) {
        if (e == null) return null;
        BicDTO dto = new BicDTO();
        dto.setCodeBic(e.getCodeBic());
        dto.setBicOrdonnateur(e.getBicOrdonnateur());
        dto.setBicBeneficiare(e.getBicBeneficiare());
        dto.setLibelleBic(e.getLibelleBic());
        dto.setLibelleBq(e.getLibelleBq());
        dto.setCodeBq(e.getCodeBq());
        return dto;
    }

    public Bic toEntity(BicDTO dto) {
        if (dto == null) return null;
        Bic e = new Bic();
        e.setCodeBic(dto.getCodeBic());
        e.setBicOrdonnateur(dto.getBicOrdonnateur());
        e.setBicBeneficiare(dto.getBicBeneficiare());
        e.setLibelleBic(dto.getLibelleBic());
        e.setLibelleBq(dto.getLibelleBq());
        e.setCodeBq(dto.getCodeBq());
        return e;
    }

    public TypeMessageDTO toDTO(TypeMessage e) {
        if (e == null) return null;
        TypeMessageDTO dto = new TypeMessageDTO();
        dto.setCodeMsg(e.getCodeMsg());
        dto.setLibelleMsg(e.getLibelleMsg());
        dto.setTypeMsg(e.getTypeMsg());
        return dto;
    }

    public TypeMessage toEntity(TypeMessageDTO dto) {
        if (dto == null) return null;
        TypeMessage e = new TypeMessage();
        e.setCodeMsg(dto.getCodeMsg());
        e.setLibelleMsg(dto.getLibelleMsg());
        e.setTypeMsg(dto.getTypeMsg());
        return e;
    }

    public StatutDTO toDTO(Statut e) {
        if (e == null) return null;
        StatutDTO dto = new StatutDTO();
        dto.setIdStatut(e.getIdStatut());
        dto.setCodeStatut(e.getCodeStatut());
        dto.setLibelleStatut(e.getLibelleStatut());
        return dto;
    }

    public Statut toEntity(StatutDTO dto) {
        if (dto == null) return null;
        Statut e = new Statut();
        e.setIdStatut(dto.getIdStatut());
        e.setCodeStatut(dto.getCodeStatut());
        e.setLibelleStatut(dto.getLibelleStatut());
        return e;
    }

    public SopDTO toDTO(Sop e) {
        if (e == null) return null;
        SopDTO dto = new SopDTO();
        dto.setId(e.getId());
        dto.setLibelleSop(e.getLibelleSop());
        return dto;
    }

    public Sop toEntity(SopDTO dto) {
        if (dto == null) return null;
        Sop e = new Sop();
        e.setId(dto.getId());
        e.setLibelleSop(dto.getLibelleSop());
        return e;
    }

    public VirementEmisDTO toDTO(VirementEmis e) {
        if (e == null) return null;
        VirementEmisDTO dto = new VirementEmisDTO();
        dto.setIdVrtEmis(e.getIdVrtEmis());
        dto.setIdSop(e.getIdSop());
        dto.setIdStatutStatut(e.getIdStatutStatut());
        dto.setIdAdresseAdresse(e.getIdAdresseAdresse());
        dto.setCodeBicBic(e.getCodeBicBic());
        dto.setCodeMsgTypeMessage(e.getCodeMsgTypeMessage());
        dto.setReference(e.getReference());
        dto.setDenominationBnf(e.getDenominationBnf());
        dto.setNumCompteBnf(e.getNumCompteBnf());
        dto.setNumCompteOrd(e.getNumCompteOrd());
        dto.setDenominationOrd(e.getDenominationOrd());
        dto.setMontant(e.getMontant());
        dto.setDateValeur(e.getDateValeur());
        dto.setRenseignement(e.getRenseignement());
        dto.setCodeDevise(e.getCodeDevise());
        dto.setDateIntegration(e.getDateIntegration());
        dto.setBicOrdonnateur(e.getBicOrdonnateur());
        dto.setBicBeneficiaire(e.getBicBeneficiaire());
        dto.setUetr(e.getUetr());
        dto.setEndToEnd(e.getEndToEnd());
        dto.setStatutSwift(e.getStatut());
        return dto;
    }

    public VirementEmis toEntity(VirementEmisDTO dto) {
        if (dto == null) return null;
        VirementEmis e = new VirementEmis();
        e.setIdVrtEmis(dto.getIdVrtEmis());
        e.setIdSop(dto.getIdSop());
        e.setIdStatutStatut(dto.getIdStatutStatut());
        e.setIdAdresseAdresse(dto.getIdAdresseAdresse());
        e.setCodeBicBic(dto.getCodeBicBic());
        e.setCodeMsgTypeMessage(dto.getCodeMsgTypeMessage());
        e.setReference(dto.getReference());
        e.setDenominationBnf(dto.getDenominationBnf());
        e.setNumCompteBnf(dto.getNumCompteBnf());
        e.setNumCompteOrd(dto.getNumCompteOrd());
        e.setDenominationOrd(dto.getDenominationOrd());
        e.setMontant(dto.getMontant());
        e.setDateValeur(dto.getDateValeur());
        e.setRenseignement(dto.getRenseignement());
        e.setCodeDevise(dto.getCodeDevise());
        e.setDateIntegration(dto.getDateIntegration());
        e.setBicOrdonnateur(dto.getBicOrdonnateur());
        e.setBicBeneficiaire(dto.getBicBeneficiaire());
        e.setUetr(dto.getUetr());
        e.setEndToEnd(dto.getEndToEnd());
        e.setStatut(dto.getStatutSwift());
        if (dto.getIdStatutStatut() != null) e.setIdStatut(dto.getIdStatutStatut());
        if (dto.getIdAdresseAdresse() != null) e.setIdAdresse(dto.getIdAdresseAdresse());
        if (dto.getCodeMsgTypeMessage() != null) e.setIdTypeMessage(dto.getCodeMsgTypeMessage());
        return e;
    }

    public VirementRecuDTO toDTO(VirementRecu e) {
        if (e == null) return null;
        VirementRecuDTO dto = new VirementRecuDTO();
        dto.setIdVrtRecu(e.getIdVrtRecu());
        dto.setIdStatutStatut(e.getIdStatutStatut());
        dto.setIdAdresseAdresse(e.getIdAdresseAdresse());
        dto.setIdSop(e.getIdSop());
        dto.setCodeBicBic(e.getCodeBicBic());
        dto.setCodeMsgTypeMessage(e.getCodeMsgTypeMessage());
        dto.setReference(e.getReference());
        dto.setDenominationBnf(e.getDenominationBnf());
        dto.setNumCompteBnf(e.getNumCompteBnf());
        dto.setNumCompteOrd(e.getNumCompteOrd());
        dto.setDenominationOrd(e.getDenominationOrd());
        dto.setMontant(e.getMontant());
        dto.setDateValeur(e.getDateValeur());
        dto.setRenseignement(e.getRenseignement());
        dto.setCodeDevise(e.getCodeDevise());
        dto.setDateIntegration(e.getDateIntegration());
        dto.setBicOrdonnateur(e.getBicOrdonnateur());
        dto.setBicBeneficiaire(e.getBicBeneficiaire());
        dto.setUetr(e.getUetr());
        dto.setEndToEnd(e.getEndToEnd());
        return dto;
    }

    public VirementRecu toEntity(VirementRecuDTO dto) {
        if (dto == null) return null;
        VirementRecu e = new VirementRecu();
        e.setIdVrtRecu(dto.getIdVrtRecu());
        e.setIdStatutStatut(dto.getIdStatutStatut());
        e.setIdAdresseAdresse(dto.getIdAdresseAdresse());
        e.setIdSop(dto.getIdSop());
        e.setCodeBicBic(dto.getCodeBicBic());
        e.setCodeMsgTypeMessage(dto.getCodeMsgTypeMessage());
        e.setReference(dto.getReference());
        e.setDenominationBnf(dto.getDenominationBnf());
        e.setNumCompteBnf(dto.getNumCompteBnf());
        e.setNumCompteOrd(dto.getNumCompteOrd());
        e.setDenominationOrd(dto.getDenominationOrd());
        e.setMontant(dto.getMontant());
        e.setDateValeur(dto.getDateValeur());
        e.setRenseignement(dto.getRenseignement());
        e.setCodeDevise(dto.getCodeDevise());
        e.setDateIntegration(dto.getDateIntegration());
        e.setBicOrdonnateur(dto.getBicOrdonnateur());
        e.setBicBeneficiaire(dto.getBicBeneficiaire());
        e.setUetr(dto.getUetr());
        e.setEndToEnd(dto.getEndToEnd());
        if (dto.getIdStatutStatut() != null) e.setIdStatut(dto.getIdStatutStatut());
        if (dto.getIdAdresseAdresse() != null) e.setIdAdresse(dto.getIdAdresseAdresse());
        if (dto.getCodeMsgTypeMessage() != null) e.setIdTypeMessage(dto.getCodeMsgTypeMessage());
        return e;
    }

    public MessageEmisDTO toDTO(MessageEmis e) {
        if (e == null) return null;
        MessageEmisDTO dto = new MessageEmisDTO();
        dto.setIdMsgEmis(e.getIdMsgEmis());
        dto.setIdVrtEmisVirementEmis(e.getIdVrtEmisVirementEmis());
        dto.setIdSopVirementEmis(e.getIdSopVirementEmis());
        dto.setIdStatutStatutVirementEmis(e.getIdStatutStatutVirementEmis());
        dto.setIdAdresseAdresseVirementEmis(e.getIdAdresseAdresseVirementEmis());
        dto.setCodeBicBicVirementEmis(e.getCodeBicBicVirementEmis());
        dto.setCodeMsgTypeMessageVirementEmis(e.getCodeMsgTypeMessageVirementEmis());
        dto.setIdVrtEmis(e.getIdVrtEmis());
        dto.setReference(e.getReference());
        dto.setSop(e.getSop());
        return dto;
    }

    public MessageEmis toEntity(MessageEmisDTO dto) {
        if (dto == null) return null;
        MessageEmis e = new MessageEmis();
        e.setIdMsgEmis(dto.getIdMsgEmis());
        e.setIdVrtEmisVirementEmis(dto.getIdVrtEmisVirementEmis());
        e.setIdSopVirementEmis(dto.getIdSopVirementEmis());
        e.setIdStatutStatutVirementEmis(dto.getIdStatutStatutVirementEmis());
        e.setIdAdresseAdresseVirementEmis(dto.getIdAdresseAdresseVirementEmis());
        e.setCodeBicBicVirementEmis(dto.getCodeBicBicVirementEmis());
        e.setCodeMsgTypeMessageVirementEmis(dto.getCodeMsgTypeMessageVirementEmis());
        e.setIdVrtEmis(dto.getIdVrtEmis());
        e.setReference(dto.getReference());
        e.setSop(dto.getSop());
        return e;
    }

    public MessageRecuDTO toDTO(MessageRecu e) {
        if (e == null) return null;
        MessageRecuDTO dto = new MessageRecuDTO();
        dto.setIdMsgRecu(e.getIdMsgRecu());
        dto.setIdVrtRecuVirementRecu(e.getIdVrtRecuVirementRecu());
        dto.setIdStatutStatutVirementRecu(e.getIdStatutStatutVirementRecu());
        dto.setIdAdresseAdresseVirementRecu(e.getIdAdresseAdresseVirementRecu());
        dto.setIdSopVirementRecu(e.getIdSopVirementRecu());
        dto.setCodeBicBicVirementRecu(e.getCodeBicBicVirementRecu());
        dto.setCodeMsgTypeMessageVirementRecu(e.getCodeMsgTypeMessageVirementRecu());
        dto.setReference(e.getReference());
        dto.setSop(e.getSop());
        return dto;
    }

    public MessageRecu toEntity(MessageRecuDTO dto) {
        if (dto == null) return null;
        MessageRecu e = new MessageRecu();
        e.setIdMsgRecu(dto.getIdMsgRecu());
        e.setIdVrtRecuVirementRecu(dto.getIdVrtRecuVirementRecu());
        e.setIdStatutStatutVirementRecu(dto.getIdStatutStatutVirementRecu());
        e.setIdAdresseAdresseVirementRecu(dto.getIdAdresseAdresseVirementRecu());
        e.setIdSopVirementRecu(dto.getIdSopVirementRecu());
        e.setCodeBicBicVirementRecu(dto.getCodeBicBicVirementRecu());
        e.setCodeMsgTypeMessageVirementRecu(dto.getCodeMsgTypeMessageVirementRecu());
        e.setReference(dto.getReference());
        e.setSop(dto.getSop());
        return e;
    }

    public VirementEmisHistoDTO toDTO(VirementEmisHisto e) {
        if (e == null) return null;
        VirementEmisHistoDTO dto = new VirementEmisHistoDTO();
        dto.setIdVrtEmisHisto(e.getIdVrtEmisHisto());
        dto.setIdVrtEmis(e.getIdVrtEmis());
        dto.setReference(e.getReference());
        dto.setDenominationBnf(e.getDenominationBnf());
        dto.setNumCompteBnf(e.getNumCompteBnf());
        dto.setNumCompteOrd(e.getNumCompteOrd());
        dto.setDenominationOrd(e.getDenominationOrd());
        dto.setMontant(e.getMontant());
        dto.setDateValeur(e.getDateValeur());
        dto.setRenseignement(e.getRenseignement());
        dto.setCodeDevise(e.getCodeDevise());
        dto.setDateIntegration(e.getDateIntegration());
        dto.setBicOrdonnateur(e.getBicOrdonnateur());
        dto.setBicBeneficiaire(e.getBicBeneficiaire());
        dto.setUetr(e.getUetr());
        dto.setEndToEnd(e.getEndToEnd());
        dto.setDateHistorisation(e.getDateHistorisation());
        return dto;
    }

    public VirementEmisHisto toEntity(VirementEmisHistoDTO dto) {
        if (dto == null) return null;
        VirementEmisHisto e = new VirementEmisHisto();
        e.setIdVrtEmisHisto(dto.getIdVrtEmisHisto());
        e.setIdVrtEmis(dto.getIdVrtEmis());
        e.setReference(dto.getReference());
        e.setDenominationBnf(dto.getDenominationBnf());
        e.setNumCompteBnf(dto.getNumCompteBnf());
        e.setNumCompteOrd(dto.getNumCompteOrd());
        e.setDenominationOrd(dto.getDenominationOrd());
        e.setMontant(dto.getMontant());
        e.setDateValeur(dto.getDateValeur());
        e.setRenseignement(dto.getRenseignement());
        e.setCodeDevise(dto.getCodeDevise());
        e.setDateIntegration(dto.getDateIntegration());
        e.setBicOrdonnateur(dto.getBicOrdonnateur());
        e.setBicBeneficiaire(dto.getBicBeneficiaire());
        e.setUetr(dto.getUetr());
        e.setEndToEnd(dto.getEndToEnd());
        e.setDateHistorisation(dto.getDateHistorisation());
        return e;
    }

    public VirementRecuHistoDTO toDTO(VirementRecuHisto e) {
        if (e == null) return null;
        VirementRecuHistoDTO dto = new VirementRecuHistoDTO();
        dto.setIdVrtRecuHisto(e.getIdVrtRecuHisto());
        dto.setIdVrtRecuVirementRecu(e.getIdVrtRecuVirementRecu());
        dto.setIdStatutStatutVirementRecu(e.getIdStatutStatutVirementRecu());
        dto.setIdAdresseAdresseVirementRecu(e.getIdAdresseAdresseVirementRecu());
        dto.setIdSopVirementRecu(e.getIdSopVirementRecu());
        dto.setCodeBicBicVirementRecu(e.getCodeBicBicVirementRecu());
        dto.setCodeMsgTypeMessageVirementRecu(e.getCodeMsgTypeMessageVirementRecu());
        dto.setIdVrtEmis(e.getIdVrtEmis());
        dto.setReference(e.getReference());
        dto.setDenominationBnf(e.getDenominationBnf());
        dto.setNumCompteBnf(e.getNumCompteBnf());
        dto.setNumCompteOrd(e.getNumCompteOrd());
        dto.setDenominationOrd(e.getDenominationOrd());
        dto.setMontant(e.getMontant());
        dto.setDateValeur(e.getDateValeur());
        dto.setRenseignement(e.getRenseignement());
        dto.setCodeDevise(e.getCodeDevise());
        dto.setDateIntegration(e.getDateIntegration());
        dto.setIdTypeMessage(e.getCodeMsgTypeMessageVirementRecu());
        dto.setBicOrdonnateur(e.getBicOrdonnateur());
        dto.setBicBeneficiaire(e.getBicBeneficiaire());
        dto.setIdStatut(e.getIdStatutStatutVirementRecu());
        dto.setIdAdresse(e.getIdAdresseAdresseVirementRecu());
        dto.setUetr(e.getUetr());
        dto.setEndToEnd(e.getEndToEnd());
        dto.setDateHistorisation(e.getDateHistorisation());
        return dto;
    }

    public VirementRecuHisto toEntity(VirementRecuHistoDTO dto) {
        if (dto == null) return null;
        VirementRecuHisto e = new VirementRecuHisto();
        e.setIdVrtRecuHisto(dto.getIdVrtRecuHisto());
        e.setIdVrtRecuVirementRecu(dto.getIdVrtRecuVirementRecu());
        e.setIdStatutStatutVirementRecu(dto.getIdStatutStatutVirementRecu());
        e.setIdAdresseAdresseVirementRecu(dto.getIdAdresseAdresseVirementRecu());
        e.setIdSopVirementRecu(dto.getIdSopVirementRecu());
        e.setCodeBicBicVirementRecu(dto.getCodeBicBicVirementRecu());
        e.setCodeMsgTypeMessageVirementRecu(dto.getCodeMsgTypeMessageVirementRecu());
        e.setIdVrtEmis(dto.getIdVrtEmis());
        e.setReference(dto.getReference());
        e.setDenominationBnf(dto.getDenominationBnf());
        e.setNumCompteBnf(dto.getNumCompteBnf());
        e.setNumCompteOrd(dto.getNumCompteOrd());
        e.setDenominationOrd(dto.getDenominationOrd());
        e.setMontant(dto.getMontant());
        e.setDateValeur(dto.getDateValeur());
        e.setRenseignement(dto.getRenseignement());
        e.setCodeDevise(dto.getCodeDevise());
        e.setDateIntegration(dto.getDateIntegration());
        e.setCodeMsgTypeMessageVirementRecu(dto.getIdTypeMessage());
        e.setBicOrdonnateur(dto.getBicOrdonnateur());
        e.setBicBeneficiaire(dto.getBicBeneficiaire());
        e.setIdStatutStatutVirementRecu(dto.getIdStatut());
        e.setIdAdresseAdresseVirementRecu(dto.getIdAdresse());
        e.setUetr(dto.getUetr());
        e.setEndToEnd(dto.getEndToEnd());
        e.setDateHistorisation(dto.getDateHistorisation());
        return e;
    }
}
