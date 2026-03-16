package com.stage.swift.service.mx.impl;

import com.prowidesoftware.swift.model.mx.MxPacs00800108;
import com.prowidesoftware.swift.model.mx.MxPacs00900108;
import com.stage.swift.entity.Adresse;
import com.stage.swift.entity.MessageRecu;
import com.stage.swift.entity.VirementRecu;
import com.stage.swift.entity.VirementRecuHisto;
import com.stage.swift.enums.MessageMX;
import com.stage.swift.helpers.SaaEnvelopeHelper;
import com.stage.swift.mappers.mx.PACS008ToVirementRecuMapper;
import com.stage.swift.mappers.mx.PACS009ToVirementRecuMapper;
import com.stage.swift.repository.AdresseRepository;
import com.stage.swift.repository.MessageRecuRepository;
import com.stage.swift.repository.VirementRecuHistoRepository;
import com.stage.swift.repository.VirementRecuRepository;
import com.stage.swift.service.entity.MessageRecuService;
import com.stage.swift.service.entity.VirementRecuService;
import com.stage.swift.service.mx.AckRecuService;
import com.stage.swift.service.ref.ReferenceDataResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static com.stage.swift.helpers.MxMapperHelper.truncate;

/**
 * Flux reçu OUT_SAA : crée VirementRecu + MessageRecu depuis le .ack (tous champs sauf statut final).
 * Le statut est mis à jour plus tard par le SOP (OUT_SOP).
 */
@Service
public class AckRecuServiceImpl implements AckRecuService {

    private static final Logger log = LoggerFactory.getLogger(AckRecuServiceImpl.class);

    private static final long DEFAULT_ID_ADRESSE = 1L;
    private static final long CODE_MSG_PACS008 = 1L;
    private static final long CODE_MSG_PACS009 = 2L;
    private static final long ID_VIREMENT_EMIS_NON_RAPPROCHE = 0L;
    /** type_adresse : 1 = DBTR (débiteur), 2 = CDTR (créditeur) */
    private static final long ID_TYPE_ADRESSE_DBTR = 1L;
    private static final long ID_TYPE_ADRESSE_CDTR = 2L;

    private final VirementRecuRepository virementRecuRepository;
    private final VirementRecuHistoRepository virementRecuHistoRepository;
    private final MessageRecuRepository messageRecuRepository;
    private final VirementRecuService virementRecuService;
    private final MessageRecuService messageRecuService;
    private final PACS008ToVirementRecuMapper pacs008ToVirementRecuMapper;
    private final PACS009ToVirementRecuMapper pacs009ToVirementRecuMapper;
    private final ReferenceDataResolver referenceDataResolver;
    private final AdresseRepository adresseRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public AckRecuServiceImpl(VirementRecuRepository virementRecuRepository,
                              VirementRecuHistoRepository virementRecuHistoRepository,
                              MessageRecuRepository messageRecuRepository,
                              VirementRecuService virementRecuService,
                              MessageRecuService messageRecuService,
                              PACS008ToVirementRecuMapper pacs008ToVirementRecuMapper,
                              PACS009ToVirementRecuMapper pacs009ToVirementRecuMapper,
                              ReferenceDataResolver referenceDataResolver,
                              AdresseRepository adresseRepository) {
        this.virementRecuRepository = virementRecuRepository;
        this.virementRecuHistoRepository = virementRecuHistoRepository;
        this.messageRecuRepository = messageRecuRepository;
        this.virementRecuService = virementRecuService;
        this.messageRecuService = messageRecuService;
        this.pacs008ToVirementRecuMapper = pacs008ToVirementRecuMapper;
        this.pacs009ToVirementRecuMapper = pacs009ToVirementRecuMapper;
        this.referenceDataResolver = referenceDataResolver;
        this.adresseRepository = adresseRepository;
    }

    @Override
    @Transactional
    public void processAckFileRecu(String ackContent) {
        if (ackContent == null || ackContent.trim().isEmpty()) {
            throw new IllegalArgumentException("Contenu .ack recu vide");
        }
        if (hasMultipleXmlDeclarations(ackContent)) {
            List<String> singleDocs = splitIntoSingleXmlDocuments(ackContent);
            for (String doc : singleDocs) {
                if (doc != null && !doc.trim().isEmpty()) {
                    processAckFileRecu(doc);
                }
            }
            return;
        }

        List<String> allDocs = SaaEnvelopeHelper.extractAllPacsDocuments(ackContent);
        if (!allDocs.isEmpty()) {
            for (String docXml : allDocs) {
                parseAndSaveRecuOne(docXml);
            }
            return;
        }

        MessageMX messageType = SaaEnvelopeHelper.getMessageTypeFromXml(ackContent);
        if (messageType == null) {
            throw new IllegalArgumentException("Type de message MX non supporté dans .ack recu (pacs.008, pacs.009 ou camt.054 attendu)");
        }
        String xmlToParse = SaaEnvelopeHelper.xmlToParse(ackContent, messageType);
        if (messageType == MessageMX.CAMT054) {
            parseAndSaveCamt054Recu(xmlToParse, ackContent);
            return;
        }
        parseAndSaveRecuOne(xmlToParse);
    }

    private static boolean hasMultipleXmlDeclarations(String xml) {
        if (xml == null) return false;
        int idx = 0;
        int count = 0;
        while (count < 2 && (idx = xml.toLowerCase().indexOf("<?xml", idx)) != -1) {
            count++;
            idx += 5;
        }
        return count >= 2;
    }

    private static List<String> splitIntoSingleXmlDocuments(String xml) {
        List<String> out = new ArrayList<>();
        if (xml == null) return out;
        Pattern splitPattern = Pattern.compile("(?=<\\?[xX][mM][lL][\\s>])");
        String[] parts = splitPattern.split(xml);
        for (String part : parts) {
            String trimmed = part != null ? part.trim() : "";
            if (!trimmed.isEmpty() && trimmed.toLowerCase().startsWith("<?xml")) {
                out.add(trimmed);
            }
        }
        return out;
    }

    private static String extractBicFromPacs008(MxPacs00800108 mx) {
        if (mx == null || mx.getFIToFICstmrCdtTrf() == null || mx.getFIToFICstmrCdtTrf().getCdtTrfTxInf() == null
                || mx.getFIToFICstmrCdtTrf().getCdtTrfTxInf().isEmpty()) return null;
        com.prowidesoftware.swift.model.mx.dic.CreditTransferTransaction39 tx = mx.getFIToFICstmrCdtTrf().getCdtTrfTxInf().get(0);
        if (tx.getInstgAgt() != null && tx.getInstgAgt().getFinInstnId() != null && tx.getInstgAgt().getFinInstnId().getBICFI() != null)
            return tx.getInstgAgt().getFinInstnId().getBICFI();
        if (tx.getInstdAgt() != null && tx.getInstdAgt().getFinInstnId() != null && tx.getInstdAgt().getFinInstnId().getBICFI() != null)
            return tx.getInstdAgt().getFinInstnId().getBICFI();
        return null;
    }

    private static String extractBicFromPacs009(MxPacs00900108 mx) {
        if (mx == null || mx.getFICdtTrf() == null || mx.getFICdtTrf().getCdtTrfTxInf() == null
                || mx.getFICdtTrf().getCdtTrfTxInf().isEmpty()) return null;
        com.prowidesoftware.swift.model.mx.dic.CreditTransferTransaction36 tx = mx.getFICdtTrf().getCdtTrfTxInf().get(0);
        if (tx.getInstgAgt() != null && tx.getInstgAgt().getFinInstnId() != null && tx.getInstgAgt().getFinInstnId().getBICFI() != null)
            return tx.getInstgAgt().getFinInstnId().getBICFI();
        if (tx.getInstdAgt() != null && tx.getInstdAgt().getFinInstnId() != null && tx.getInstdAgt().getFinInstnId().getBICFI() != null)
            return tx.getInstdAgt().getFinInstnId().getBICFI();
        return null;
    }

    private long resolveAdresseIdFromPostalAdr(com.prowidesoftware.swift.model.mx.dic.PostalAddress24 pstlAdr, long idTypeAdresse) {
        if (pstlAdr == null) {
            return DEFAULT_ID_ADRESSE;
        }

        String ligne1 = truncate(
                ((pstlAdr.getStrtNm() != null) ? pstlAdr.getStrtNm() : "") +
                        ((pstlAdr.getBldgNb() != null) ? " " + pstlAdr.getBldgNb() : ""),
                255);
        String ligne2 = null;
        String ville = truncate(pstlAdr.getTwnNm() != null ? pstlAdr.getTwnNm() : "", 150);
        String codePostal = truncate(pstlAdr.getPstCd() != null ? pstlAdr.getPstCd() : "", 20);
        String pays = truncate(pstlAdr.getCtry() != null ? pstlAdr.getCtry() : "", 100);

        java.util.List<String> adrLines = pstlAdr.getAdrLine();
        if (adrLines != null && !adrLines.isEmpty()) {
            if ((ligne1 == null || ligne1.trim().isEmpty())) {
                ligne1 = truncate(adrLines.get(0), 255);
            }
            if (adrLines.size() > 1 && (ligne2 == null || ligne2.trim().isEmpty())) {
                ligne2 = truncate(adrLines.get(1), 255);
            }
        }

        boolean hasContent =
                (ligne1 != null && !ligne1.trim().isEmpty()) ||
                (ligne2 != null && !ligne2.trim().isEmpty()) ||
                (ville != null && !ville.trim().isEmpty()) ||
                (codePostal != null && !codePostal.trim().isEmpty()) ||
                (pays != null && !pays.trim().isEmpty());

        if (!hasContent) {
            return DEFAULT_ID_ADRESSE;
        }

        final String fLigne1 = ligne1;
        final String fLigne2 = ligne2;
        final String fVille = ville;
        final String fCodePostal = codePostal;
        final String fPays = pays;
        final long fIdTypeAdresse = idTypeAdresse;

        return adresseRepository
                .findByLigne1AndLigne2AndVilleAndCodePostalAndPaysAndIdTypeAdresse(fLigne1, fLigne2, fVille, fCodePostal, fPays, fIdTypeAdresse)
                .map(Adresse::getIdAdresse)
                .orElseGet(() -> {
                    Long nextId = adresseRepository.nextIdAdresse();
                    Adresse a = new Adresse();
                    a.setIdAdresse(nextId);
                    a.setLigne1(fLigne1);
                    a.setLigne2(fLigne2);
                    a.setVille(fVille);
                    a.setCodePostal(fCodePostal);
                    a.setPays(fPays);
                    a.setIdTypeAdresse(fIdTypeAdresse);
                    adresseRepository.save(a);
                    return nextId;
                });
    }

    private long resolveAdresseIdFromPacs008(MxPacs00800108 mx) {
        if (mx == null || mx.getFIToFICstmrCdtTrf() == null
                || mx.getFIToFICstmrCdtTrf().getCdtTrfTxInf() == null
                || mx.getFIToFICstmrCdtTrf().getCdtTrfTxInf().isEmpty()) {
            return DEFAULT_ID_ADRESSE;
        }
        com.prowidesoftware.swift.model.mx.dic.CreditTransferTransaction39 tx =
                mx.getFIToFICstmrCdtTrf().getCdtTrfTxInf().get(0);
        com.prowidesoftware.swift.model.mx.dic.PostalAddress24 pstlAdr = null;
        long idTypeAdresse = ID_TYPE_ADRESSE_DBTR;
        if (tx.getCdtr() != null && tx.getCdtr().getPstlAdr() != null) {
            pstlAdr = tx.getCdtr().getPstlAdr();
            idTypeAdresse = ID_TYPE_ADRESSE_CDTR;
        } else if (tx.getDbtr() != null && tx.getDbtr().getPstlAdr() != null) {
            pstlAdr = tx.getDbtr().getPstlAdr();
            idTypeAdresse = ID_TYPE_ADRESSE_DBTR;
        }
        return resolveAdresseIdFromPostalAdr(pstlAdr, idTypeAdresse);
    }

    private long resolveAdresseIdFromPacs009(MxPacs00900108 mx) {
        // Dans ce schéma Prowide, PACS.009 ne porte pas d'adresse client exploitable
        // → on garde l'adresse par défaut.
        return DEFAULT_ID_ADRESSE;
    }

    private static String extractBicFromCamt054Tx(com.prowidesoftware.swift.model.mx.dic.EntryTransaction10 tx) {
        if (tx == null || tx.getRltdAgts() == null) return null;
        if (tx.getRltdAgts().getInstgAgt() != null && tx.getRltdAgts().getInstgAgt().getFinInstnId() != null && tx.getRltdAgts().getInstgAgt().getFinInstnId().getBICFI() != null)
            return tx.getRltdAgts().getInstgAgt().getFinInstnId().getBICFI();
        if (tx.getRltdAgts().getInstdAgt() != null && tx.getRltdAgts().getInstdAgt().getFinInstnId() != null && tx.getRltdAgts().getInstdAgt().getFinInstnId().getBICFI() != null)
            return tx.getRltdAgts().getInstdAgt().getFinInstnId().getBICFI();
        return null;
    }

    private void parseAndSaveCamt054Recu(String xmlToParse, String fullXml) {
        com.prowidesoftware.swift.model.mx.MxCamt05400108 camt = null;
        try {
            camt = com.prowidesoftware.swift.model.mx.MxCamt05400108.parse(xmlToParse);
        } catch (Exception e) {
            log.debug("CAMT054 recu: parse fragment échoué, tentative XML complet: {}", e.getMessage());
        }
        if (camt == null && fullXml != null && !fullXml.equals(xmlToParse) && !hasMultipleXmlDeclarations(fullXml)) {
            try {
                camt = com.prowidesoftware.swift.model.mx.MxCamt05400108.parse(fullXml);
            } catch (Exception ignored) { }
        }
        if (camt == null) {
            throw new IllegalArgumentException("CAMT054 recu: parsing impossible");
        }
        com.prowidesoftware.swift.model.mx.dic.BankToCustomerDebitCreditNotificationV08 notif = camt.getBkToCstmrDbtCdtNtfctn();
        if (notif == null || notif.getNtfctn() == null || notif.getNtfctn().isEmpty()) {
            return;
        }
        String grpMsgId = notif.getGrpHdr() != null ? notif.getGrpHdr().getMsgId() : null;
        for (com.prowidesoftware.swift.model.mx.dic.AccountNotification17 ntfctn : notif.getNtfctn()) {
            if (ntfctn.getNtry() == null) continue;
            for (com.prowidesoftware.swift.model.mx.dic.ReportEntry10 ntry : ntfctn.getNtry()) {
                if (ntry.getNtryDtls() == null) continue;
                for (com.prowidesoftware.swift.model.mx.dic.EntryDetails9 ed : ntry.getNtryDtls()) {
                    if (ed.getTxDtls() == null) continue;
                    for (com.prowidesoftware.swift.model.mx.dic.EntryTransaction10 tx : ed.getTxDtls()) {
                        String bicCode = extractBicFromCamt054Tx(tx);
                        long idSop = referenceDataResolver.resolveSopIdFromBic(bicCode != null ? bicCode : "");
                        long idStatutStatut = referenceDataResolver.resolveStatutIdByCode("INTEGRE");
                        long codeBic = referenceDataResolver.resolveCodeBicFromBic(bicCode != null ? bicCode : "");
                        Long nextIdRecu = virementRecuRepository.nextIdVrtRecu();
                        VirementRecu v = mapCamt054TxToVirementRecu(tx, ntry, grpMsgId, nextIdRecu, idSop, idStatutStatut, codeBic);
                        virementRecuService.save(v);
                        entityManager.flush();
                        MessageRecu m = mapToMessageRecu(v, tx.getRefs() != null ? tx.getRefs().getMsgId() : grpMsgId);
                        m.setIdMsgRecu(messageRecuRepository.nextIdMsgRecu());
                        messageRecuService.save(m);
                        createHistoFromVirementRecu(v, ID_VIREMENT_EMIS_NON_RAPPROCHE);
                        entityManager.flush();
                        log.info("OUT_SAA CAMT054: VirementRecu + MessageRecu + VirementRecuHisto créés, idVrtRecu={}", v.getIdVrtRecu());
                    }
                }
            }
        }
    }

    private VirementRecu mapCamt054TxToVirementRecu(com.prowidesoftware.swift.model.mx.dic.EntryTransaction10 tx,
                                                    com.prowidesoftware.swift.model.mx.dic.ReportEntry10 ntry,
                                                    String grpMsgId, Long nextIdRecu, long idSop, long idStatutStatut, long codeBic) {
        com.prowidesoftware.swift.model.mx.dic.PostalAddress24 pstlAdr = null;
        // 1) Adresse de l'agent instigateur (FinInstnId/PstlAdr) si présente
        if (tx.getRltdAgts() != null
                && tx.getRltdAgts().getInstgAgt() != null
                && tx.getRltdAgts().getInstgAgt().getFinInstnId() != null) {
            pstlAdr = tx.getRltdAgts().getInstgAgt().getFinInstnId().getPstlAdr();
        }
        // 2) Sinon, adresse du débiteur si disponible
        if (pstlAdr == null
                && tx.getRltdPties() != null
                && tx.getRltdPties().getDbtr() != null
                && tx.getRltdPties().getDbtr().getPty() != null) {
            pstlAdr = tx.getRltdPties().getDbtr().getPty().getPstlAdr();
        }
        long idAdresse = resolveAdresseIdFromPostalAdr(pstlAdr, ID_TYPE_ADRESSE_DBTR);

        VirementRecu v = new VirementRecu();
        v.setIdVrtRecu(nextIdRecu);
        v.setIdSop(idSop);
        v.setIdStatutStatut(idStatutStatut);
        v.setIdAdresseAdresse(idAdresse);
        v.setCodeBicBic(codeBic);
        v.setCodeMsgTypeMessage(CODE_MSG_PACS008);
        v.setIdStatut(idStatutStatut);
        v.setIdAdresse(idAdresse);
        v.setIdTypeMessage(CODE_MSG_PACS008);
        v.setDateIntegration(java.time.LocalDate.now());
        String ref = grpMsgId;
        if (tx.getRefs() != null) {
            if (tx.getRefs().getMsgId() != null) ref = tx.getRefs().getMsgId();
            v.setUetr(tx.getRefs().getUETR() != null ? truncate(tx.getRefs().getUETR(), 254) : "");
            v.setEndToEnd(truncate(tx.getRefs().getEndToEndId() != null ? tx.getRefs().getEndToEndId() : "NOTPROVIDED", 35));
        } else {
            v.setUetr("");
            v.setEndToEnd("NOTPROVIDED");
        }
        v.setReference(truncate(ref != null ? ref : " ", 35));
        if (ntry.getAmt() != null) {
            v.setMontant(ntry.getAmt().getValue() != null ? ntry.getAmt().getValue() : java.math.BigDecimal.ZERO);
            v.setCodeDevise(ntry.getAmt().getCcy() != null && !ntry.getAmt().getCcy().isEmpty() ? truncate(ntry.getAmt().getCcy(), 3) : "MAD");
        } else {
            v.setMontant(java.math.BigDecimal.ZERO);
            v.setCodeDevise("MAD");
        }
        v.setDateValeur(com.stage.swift.helpers.MxMapperHelper.parseLocalDate(ntry.getValDt() != null ? ntry.getValDt().getDt() : null));
        if (tx.getRltdPties() != null) {
            v.setDenominationOrd(truncate(tx.getRltdPties().getDbtr() != null && tx.getRltdPties().getDbtr().getPty() != null ? tx.getRltdPties().getDbtr().getPty().getNm() : " ", 255));
            v.setDenominationBnf(truncate(tx.getRltdPties().getCdtr() != null && tx.getRltdPties().getCdtr().getPty() != null ? tx.getRltdPties().getCdtr().getPty().getNm() : " ", 255));
            v.setNumCompteOrd(truncate(tx.getRltdPties().getDbtrAcct() != null && tx.getRltdPties().getDbtrAcct().getId() != null && tx.getRltdPties().getDbtrAcct().getId().getOthr() != null ? tx.getRltdPties().getDbtrAcct().getId().getOthr().getId() : " ", 255));
            v.setNumCompteBnf(truncate(tx.getRltdPties().getCdtrAcct() != null && tx.getRltdPties().getCdtrAcct().getId() != null && tx.getRltdPties().getCdtrAcct().getId().getOthr() != null ? tx.getRltdPties().getCdtrAcct().getId().getOthr().getId() : " ", 255));
        } else {
            v.setDenominationOrd(" ");
            v.setDenominationBnf(" ");
            v.setNumCompteOrd(" ");
            v.setNumCompteBnf(" ");
        }
        if (tx.getRltdAgts() != null) {
            v.setBicOrdonnateur(truncate(tx.getRltdAgts().getInstgAgt() != null && tx.getRltdAgts().getInstgAgt().getFinInstnId() != null ? tx.getRltdAgts().getInstgAgt().getFinInstnId().getBICFI() : " ", 255));
            v.setBicBeneficiaire(truncate(tx.getRltdAgts().getInstdAgt() != null && tx.getRltdAgts().getInstdAgt().getFinInstnId() != null ? tx.getRltdAgts().getInstdAgt().getFinInstnId().getBICFI() : " ", 255));
        } else {
            v.setBicOrdonnateur(" ");
            v.setBicBeneficiaire(" ");
        }
        v.setRenseignement(truncate(tx.getRmtInf() != null && tx.getRmtInf().getUstrd() != null ? String.join(" ", tx.getRmtInf().getUstrd()) : " ", 255));
        // Statut rapprochement initial côté reçu : INTEGRE
        v.setStatutRecu("INTEGRE");
        return v;
    }

    private void parseAndSaveRecuOne(String xmlToParse) {
        MessageMX messageType = MessageMX.fromXmlContent(xmlToParse);
        if (messageType == null || messageType == MessageMX.CAMT054) {
            throw new IllegalArgumentException("Document recu non reconnu (pacs.008 ou pacs.009 attendu pour fragment)");
        }
        try {
            if (messageType == MessageMX.PACS008) {
                MxPacs00800108 mx = MxPacs00800108.parse(xmlToParse);
                String bicCode = extractBicFromPacs008(mx);
                long idSop = referenceDataResolver.resolveSopIdFromBic(bicCode != null ? bicCode : "");
                long idStatutStatut = referenceDataResolver.resolveStatutIdByCode("INTEGRE");
                long codeBic = referenceDataResolver.resolveCodeBicFromBic(bicCode != null ? bicCode : "");
                long idAdresse = resolveAdresseIdFromPacs008(mx);
                String msgId = mx.getFIToFICstmrCdtTrf() != null && mx.getFIToFICstmrCdtTrf().getGrpHdr() != null
                        ? mx.getFIToFICstmrCdtTrf().getGrpHdr().getMsgId() : null;
                Long nextIdRecu = virementRecuRepository.nextIdVrtRecu();
                VirementRecu v = pacs008ToVirementRecuMapper.toVirementRecu(mx, nextIdRecu,
                        idStatutStatut, idAdresse, idSop, codeBic, CODE_MSG_PACS008);
                // Statut rapprochement initial côté reçu : INTEGRE
                v.setStatutRecu("INTEGRE");
                virementRecuService.save(v);
                entityManager.flush();
                MessageRecu m = mapToMessageRecu(v, msgId);
                m.setIdMsgRecu(messageRecuRepository.nextIdMsgRecu());
                messageRecuService.save(m);
                createHistoFromVirementRecu(v, ID_VIREMENT_EMIS_NON_RAPPROCHE);
                entityManager.flush();
                log.info("OUT_SAA PACS008: VirementRecu + MessageRecu + VirementRecuHisto créés, idVrtRecu={}", v.getIdVrtRecu());
                return;
            }
            MxPacs00900108 mx = MxPacs00900108.parse(xmlToParse);
            String bicCode = extractBicFromPacs009(mx);
            long idSop = referenceDataResolver.resolveSopIdFromBic(bicCode != null ? bicCode : "");
            long idStatutStatut = referenceDataResolver.resolveStatutIdByCode("INTEGRE");
            long codeBic = referenceDataResolver.resolveCodeBicFromBic(bicCode != null ? bicCode : "");
            long idAdresse = resolveAdresseIdFromPacs009(mx);
            String msgId = mx.getFICdtTrf() != null && mx.getFICdtTrf().getGrpHdr() != null
                    ? mx.getFICdtTrf().getGrpHdr().getMsgId() : null;
            Long nextIdRecu = virementRecuRepository.nextIdVrtRecu();
            VirementRecu v = pacs009ToVirementRecuMapper.toVirementRecu(mx, nextIdRecu,
                    idStatutStatut, idAdresse, idSop, codeBic, CODE_MSG_PACS009);
            // Statut rapprochement initial côté reçu : INTEGRE
            v.setStatutRecu("INTEGRE");
            virementRecuService.save(v);
            entityManager.flush();
            MessageRecu m = mapToMessageRecu(v, msgId);
            m.setIdMsgRecu(messageRecuRepository.nextIdMsgRecu());
            messageRecuService.save(m);
            createHistoFromVirementRecu(v, ID_VIREMENT_EMIS_NON_RAPPROCHE);
            entityManager.flush();
            log.info("OUT_SAA PACS009: VirementRecu + MessageRecu + VirementRecuHisto créés, idVrtRecu={}", v.getIdVrtRecu());
        } catch (Exception e) {
            log.error("Erreur parsing recu {} via Prowide", messageType.getLabel(), e);
            throw new IllegalArgumentException("Erreur parsing MX recu: " + e.getMessage(), e);
        }
    }

    private void createHistoFromVirementRecu(VirementRecu v, long idVrtEmis) {
        VirementRecuHisto h = new VirementRecuHisto();
        h.setIdVrtRecuHisto(virementRecuHistoRepository.nextIdVrtRecuHisto());
        h.setIdVrtRecuVirementRecu(v.getIdVrtRecu());
        h.setIdStatutStatutVirementRecu(v.getIdStatutStatut());
        h.setIdAdresseAdresseVirementRecu(v.getIdAdresseAdresse());
        h.setIdSopVirementRecu(v.getIdSop());
        h.setCodeBicBicVirementRecu(v.getCodeBicBic());
        h.setCodeMsgTypeMessageVirementRecu(v.getCodeMsgTypeMessage());
        h.setIdVrtEmis(idVrtEmis);
        h.setReference(v.getReference());
        h.setDenominationBnf(v.getDenominationBnf());
        h.setNumCompteBnf(v.getNumCompteBnf());
        h.setNumCompteOrd(v.getNumCompteOrd());
        h.setDenominationOrd(v.getDenominationOrd());
        h.setMontant(v.getMontant());
        h.setDateValeur(v.getDateValeur());
        h.setRenseignement(v.getRenseignement());
        h.setCodeDevise(v.getCodeDevise());
        h.setDateIntegration(v.getDateIntegration());
        h.setCodeMsgTypeMessageVirementRecu(v.getCodeMsgTypeMessage());
        h.setBicOrdonnateur(v.getBicOrdonnateur());
        h.setBicBeneficiaire(v.getBicBeneficiaire());
        h.setIdStatutStatutVirementRecu(v.getIdStatutStatut());
        h.setIdAdresseAdresseVirementRecu(v.getIdAdresseAdresse());
        h.setUetr(v.getUetr());
        h.setEndToEnd(v.getEndToEnd());
        h.setDateHistorisation(OffsetDateTime.now());
        virementRecuHistoRepository.save(h);
    }

    private MessageRecu mapToMessageRecu(VirementRecu v, String msgId) {
        MessageRecu m = new MessageRecu();
        m.setIdVrtRecuVirementRecu(v.getIdVrtRecu());
        m.setIdStatutStatutVirementRecu(v.getIdStatutStatut());
        m.setIdAdresseAdresseVirementRecu(v.getIdAdresseAdresse());
        m.setIdSopVirementRecu(v.getIdSop());
        m.setCodeBicBicVirementRecu(v.getCodeBicBic());
        m.setCodeMsgTypeMessageVirementRecu(v.getCodeMsgTypeMessage());
        m.setReference(msgId != null ? truncate(msgId, 35) : v.getReference());
        m.setSop(v.getIdSop());
        m.setVirementRecu(v);
        return m;
    }
}
