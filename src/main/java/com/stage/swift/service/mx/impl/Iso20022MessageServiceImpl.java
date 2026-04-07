package com.stage.swift.service.mx.impl;

import com.prowidesoftware.swift.model.mx.MxPacs00800108;
import com.prowidesoftware.swift.model.mx.MxPacs00900108;
import com.stage.swift.entity.Adresse;
import com.stage.swift.entity.MessageEmis;
import com.stage.swift.entity.Sop;
import com.stage.swift.entity.VirementEmis;
import com.stage.swift.entity.VirementEmisHisto;
import com.stage.swift.entity.VirementRecu;
import com.stage.swift.enums.MessageMX;
import com.stage.swift.exception.MissingReferenceDataException;
import com.stage.swift.helpers.AckHelper;
import com.stage.swift.helpers.SaaEnvelopeHelper;
import com.stage.swift.mappers.mx.PACS008ToVirementEmisMapper;
import com.stage.swift.mappers.mx.PACS009ToVirementEmisMapper;
import com.stage.swift.repository.AdresseRepository;
import com.stage.swift.repository.BicRepository;
import com.stage.swift.repository.MessageEmisRepository;
import com.stage.swift.repository.SopRepository;
import com.stage.swift.repository.StatutRepository;
import com.stage.swift.repository.TypeMessageRepository;
import com.stage.swift.repository.VirementEmisRepository;
import com.stage.swift.repository.VirementEmisHistoRepository;
import com.stage.swift.service.entity.MessageEmisService;
import com.stage.swift.service.entity.VirementEmisService;
import com.stage.swift.service.mx.Iso20022MessageService;
import com.stage.swift.service.ref.ReferenceDataResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import static com.stage.swift.helpers.MxMapperHelper.truncate;

/**
 * Parsing ISO 20022 (Pacs 008 et Pacs 009) avec Prowide.
 * Tout est enregistré dans virement_emis et message_emis uniquement.
 */
@Service
public class Iso20022MessageServiceImpl implements Iso20022MessageService {

    private static final Logger log = LoggerFactory.getLogger(Iso20022MessageServiceImpl.class);

    private static final long DEFAULT_ID_STATUT = 1L;
    private static final long DEFAULT_ID_ADRESSE = 0L;
    /** type_adresse : 1 = DBTR (débiteur), 2 = CDTR (créditeur) */
    private static final long ID_TYPE_ADRESSE_DBTR = 1L;
    private static final long ID_TYPE_ADRESSE_CDTR = 2L;
    private static final long DEFAULT_ID_SOP = 0L;
    private static final long DEFAULT_CODE_BIC = 0L;
    private static final long CODE_MSG_PACS008 = 1L;
    private static final long CODE_MSG_PACS009 = 2L;
    private static final Pattern UETR_XML_PATTERN = Pattern.compile(
            "<(?:[A-Za-z0-9_\\-]+:)?UETR(?:\\s+[^>]*)?>\\s*([^<]+?)\\s*</(?:[A-Za-z0-9_\\-]+:)?UETR\\s*>",
            Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
    private static final Pattern SOP_XML_PATTERN = Pattern.compile(
            "<(?:[A-Za-z0-9_\\-]+:)?(?:SOP|SystemOperant|SystemeOperant|BizSvc|BusinessService)(?:\\s+[^>]*)?>\\s*([^<]+?)\\s*</(?:[A-Za-z0-9_\\-]+:)?(?:SOP|SystemOperant|SystemeOperant|BizSvc|BusinessService)\\s*>",
            Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

    private final VirementEmisRepository virementEmisRepository;
    private final MessageEmisRepository messageEmisRepository;
    private final VirementEmisHistoRepository virementEmisHistoRepository;
    private final VirementEmisService virementEmisService;
    private final MessageEmisService messageEmisService;
    private final StatutRepository statutRepository;
    private final AdresseRepository adresseRepository;
    private final SopRepository sopRepository;
    private final BicRepository bicRepository;
    private final TypeMessageRepository typeMessageRepository;
    private final PACS008ToVirementEmisMapper pacs008ToVirementEmisMapper;
    private final PACS009ToVirementEmisMapper pacs009ToVirementEmisMapper;
    private final ReferenceDataResolver referenceDataResolver;

    public Iso20022MessageServiceImpl(VirementEmisRepository virementEmisRepository,
                                      MessageEmisRepository messageEmisRepository,
                                      VirementEmisHistoRepository virementEmisHistoRepository,
                                      VirementEmisService virementEmisService,
                                      MessageEmisService messageEmisService,
                                      StatutRepository statutRepository,
                                      AdresseRepository adresseRepository,
                                      SopRepository sopRepository,
                                      BicRepository bicRepository,
                                      TypeMessageRepository typeMessageRepository,
                                      PACS008ToVirementEmisMapper pacs008ToVirementEmisMapper,
                                      PACS009ToVirementEmisMapper pacs009ToVirementEmisMapper,
                                      ReferenceDataResolver referenceDataResolver) {
        this.virementEmisRepository = virementEmisRepository;
        this.messageEmisRepository = messageEmisRepository;
        this.virementEmisHistoRepository = virementEmisHistoRepository;
        this.virementEmisService = virementEmisService;
        this.messageEmisService = messageEmisService;
        this.statutRepository = statutRepository;
        this.adresseRepository = adresseRepository;
        this.sopRepository = sopRepository;
        this.bicRepository = bicRepository;
        this.typeMessageRepository = typeMessageRepository;
        this.pacs008ToVirementEmisMapper = pacs008ToVirementEmisMapper;
        this.pacs009ToVirementEmisMapper = pacs009ToVirementEmisMapper;
        this.referenceDataResolver = referenceDataResolver;
    }

    @Override
    @Transactional
    public VirementRecu parseAndSave(String xml) {
        return parseAndSave(xml, null);
    }

    @Override
    @Transactional
    public VirementRecu parseAndSave(String xml, String sourceFilePath) {
        if (xml == null || xml.trim().isEmpty()) {
            throw new IllegalArgumentException("XML vide");
        }

        // Fichier avec plusieurs <?xml → découper et traiter chaque document séparément (évite Fatal Error du parseur)
        if (hasMultipleXmlDeclarations(xml)) {
            List<String> singleDocs = splitIntoSingleXmlDocuments(xml);
            for (String doc : singleDocs) {
                if (doc != null && !doc.trim().isEmpty()) {
                    parseAndSave(doc, sourceFilePath);
                }
            }
            return null;
        }

        ensureReferenceDataExists();

        List<String> allDocs = SaaEnvelopeHelper.extractAllPacsDocuments(xml);
        if (!allDocs.isEmpty()) {
            for (String docXml : allDocs) {
                parseAndSaveOne(docXml, sourceFilePath);
            }
            return null;
        }

        MessageMX messageType = SaaEnvelopeHelper.getMessageTypeFromXml(xml);
        if (messageType == null) {
            String detected = SaaEnvelopeHelper.getFirstMessageIdentifier(xml);
            if (detected != null && detected.toLowerCase().startsWith("camt.053")) {
                log.info("CAMT053 ignoré (non pris en charge dans ce flux IN_SOP), aucun virement créé");
                return null;
            }
            String msg = detected != null
                    ? ("Type de message MX non supporté: '" + detected + "'. Attendu: pacs.008, pacs.009 ou camt.054.")
                    : "Type de message MX non supporté. Aucun MessageIdentifier pacs.008/pacs.009/camt.054 trouvé.";
            throw new IllegalArgumentException(msg);
        }
        String xmlToParse = SaaEnvelopeHelper.xmlToParse(xml, messageType);
        if (messageType == MessageMX.CAMT054) {
            parseAndSaveCamt054(xmlToParse, xml, sourceFilePath);
            return null;
        }
        parseAndSaveOne(xmlToParse, sourceFilePath);
        return null;
    }

    /**
     * Traite un message camt.054 (Bank to Customer Debit Credit Notification)..
     */
    private void parseAndSaveCamt054(String xmlToParse, String fullXml, String sourceFilePath) {
        com.prowidesoftware.swift.model.mx.MxCamt05400108 camt = null;
        try {
            camt = com.prowidesoftware.swift.model.mx.MxCamt05400108.parse(xmlToParse);
        } catch (Exception e) {
            log.debug("CAMT054: parse du fragment échoué, tentative avec le XML complet: {}", e.getMessage());
        }
        if (camt == null && fullXml != null && !fullXml.equals(xmlToParse) && !hasMultipleXmlDeclarations(fullXml)) {
            try {
                camt = com.prowidesoftware.swift.model.mx.MxCamt05400108.parse(fullXml);
            } catch (Exception ignored) { }
        }
        if (camt == null) {
            log.info("CAMT054: message reçu et accepté (parsing Prowide indisponible ou format non reconnu, aucun virement créé)");
            return;
        }
        long idSop = resolveSopIdFromBusinessSource(xmlToParse, "CAMT054");
        try {
            parseAndSaveCamt054Entries(camt, idSop, xmlToParse, sourceFilePath);
        } catch (Exception e) {
            log.warn("CAMT054: erreur lors de la création des virements: {}", e.getMessage());
        }
    }

    /** Retourne true si le contenu contient plusieurs déclarations XML (<?xml), ce qui ferait échouer le parseur. */
    private static boolean hasMultipleXmlDeclarations(String xml) {
        if (xml == null) return false;
        int idx = 0;
        int count = 0;
        String pi = "<?xml";
        while (count < 2 && (idx = xml.toLowerCase().indexOf(pi, idx)) != -1) {
            count++;
            idx += pi.length();
        }
        return count >= 2;
    }

    /** Découpe un contenu avec plusieurs <?xml en une liste de documents XML (un seul <?xml par élément). */
    private static List<String> splitIntoSingleXmlDocuments(String xml) {
        List<String> out = new ArrayList<>();
        if (xml == null) return out;
        // Découper avant chaque déclaration <?xml (insensible à la casse)
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

    private void parseAndSaveCamt054Entries(com.prowidesoftware.swift.model.mx.MxCamt05400108 camt, long idSop, String rawXml, String sourceFilePath) {
        com.prowidesoftware.swift.model.mx.dic.BankToCustomerDebitCreditNotificationV08 notif = camt.getBkToCstmrDbtCdtNtfctn();
        if (notif == null || notif.getNtfctn() == null || notif.getNtfctn().isEmpty()) {
            log.info("CAMT054: aucune notification, message accepté sans création de virement");
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
                        Long nextIdEmis = virementEmisRepository.nextIdVrtEmis();
                        VirementEmis incoming = mapCamt054TxToVirementEmis(tx, ntry, grpMsgId, nextIdEmis, idSop, referenceDataResolver);
                        validateUetrForEmis(incoming, "CAMT054");
                        Optional<VirementEmis> existingOpt = findExistingEmisForIdempotence(incoming);
                        VirementEmis entityToPersist = existingOpt
                                .map(existing -> mergeEmis(existing, incoming))
                                .orElse(incoming);

                        virementEmisService.save(entityToPersist);
                        syncAdresseForExistingEmis(existingOpt, incoming, entityToPersist);
                        // Historiser chaque création et chaque modification.
                        createHistoFromVirement(entityToPersist);
                        if (!existingOpt.isPresent()) {
                            MessageEmis m = mapToMessageEmis(entityToPersist, tx.getRefs() != null ? tx.getRefs().getMsgId() : grpMsgId, sourceFilePath);
                            m.setIdMsgEmis(messageEmisRepository.nextIdMsgEmis());
                            messageEmisService.save(m);
                            log.info("CAMT054: VirementEmis créé + historisé + MessageEmis, idVrtEmis={}", entityToPersist.getIdVrtEmis());
                        } else {
                            updateLatestMessageEmisFileMeta(entityToPersist.getIdVrtEmis(), sourceFilePath);
                            log.info("CAMT054: VirementEmis actualisé + historisé: uetr={}, reference={}, idVrtEmis={}",
                                    entityToPersist.getUetr(), entityToPersist.getReference(), entityToPersist.getIdVrtEmis());
                        }
                    }
                }
            }
        }
    }

    private VirementEmis mapCamt054TxToVirementEmis(com.prowidesoftware.swift.model.mx.dic.EntryTransaction10 tx,
                                                    com.prowidesoftware.swift.model.mx.dic.ReportEntry10 ntry,
                                                    String grpMsgId, Long nextIdEmis,
                                                    long idSop,
                                                    ReferenceDataResolver resolver) {
        String bicCode = null;
        if (tx.getRltdAgts() != null) {
            if (tx.getRltdAgts().getInstgAgt() != null && tx.getRltdAgts().getInstgAgt().getFinInstnId() != null)
                bicCode = tx.getRltdAgts().getInstgAgt().getFinInstnId().getBICFI();
            if ((bicCode == null || bicCode.isEmpty()) && tx.getRltdAgts().getInstdAgt() != null && tx.getRltdAgts().getInstdAgt().getFinInstnId() != null)
                bicCode = tx.getRltdAgts().getInstdAgt().getFinInstnId().getBICFI();
        }
        long idStatutStatut = resolver != null ? resolver.resolveStatutIdByCode("INTEGRE") : DEFAULT_ID_STATUT;
        long codeBic = resolver != null ? resolver.resolveCodeBicFromBic(bicCode) : DEFAULT_CODE_BIC;

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
        long idAdresse = safeResolveAdresseIdFromPostalAdr(pstlAdr, ID_TYPE_ADRESSE_DBTR, "CAMT054");

        VirementEmis v = new VirementEmis();
        v.setIdVrtEmis(nextIdEmis);
        v.setIdSop(idSop);
        v.setIdStatutStatut(idStatutStatut);
        v.setIdAdresseAdresse(idAdresse);
        v.setCodeBicBic(codeBic);
        v.setCodeMsgTypeMessage(CODE_MSG_PACS008);
        v.setIdStatut(idStatutStatut);
        v.setIdAdresse(idAdresse);
        v.setIdTypeMessage(CODE_MSG_PACS008);
        // Statut SWIFT initial côté émission : code_statut (INTEGRE, ACK, NACK, ...)
        v.setStatut(resolveCodeStatut(idStatutStatut));
        v.setDateIntegration(java.time.LocalDate.now());
        String ref = grpMsgId;
        if (tx.getRefs() != null) {
            if (tx.getRefs().getMsgId() != null) ref = tx.getRefs().getMsgId();
            v.setUetr(truncate(tx.getRefs().getUETR(), 254));
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
        return v;
    }

    /**
     * Parse un seul document PACS (008 ou 009) et enregistre un VirementEmis + MessageEmis.
     */
    private void parseAndSaveOne(String xmlToParse, String sourceFilePath) {
        MessageMX messageType = MessageMX.fromXmlContent(xmlToParse);
        if (messageType == null) {
            throw new IllegalArgumentException("Document non reconnu (pacs.008, pacs.009 ou camt.054 attendu)");
        }
        if (messageType == MessageMX.CAMT054) {
            parseAndSaveCamt054(xmlToParse, xmlToParse, sourceFilePath);
            return;
        }
        try {
            if (messageType == MessageMX.PACS008) {
                MxPacs00800108 mx = MxPacs00800108.parse(xmlToParse);
                String msgId = mx.getFIToFICstmrCdtTrf() != null && mx.getFIToFICstmrCdtTrf().getGrpHdr() != null
                        ? mx.getFIToFICstmrCdtTrf().getGrpHdr().getMsgId()
                        : null;
                String bicCode = extractBicCodeFromPacs008(mx);
                long idSop = resolveSopIdFromBusinessSource(xmlToParse, "PACS008");
                long idStatutStatut = referenceDataResolver.resolveStatutIdByCode("INTEGRE");
                long codeBic = referenceDataResolver.resolveCodeBicFromBic(bicCode);
                long idAdresse = safeResolveAdresseIdFromPacs008(mx, "PACS008");
                Long nextIdEmis = virementEmisRepository.nextIdVrtEmis();
                VirementEmis incoming = pacs008ToVirementEmisMapper.toVirementEmis(mx, nextIdEmis,
                        idStatutStatut, idAdresse, idSop, codeBic, CODE_MSG_PACS008);
                // Statut initial côté émission : code_statut (INTEGRE)
                incoming.setStatut(resolveCodeStatut(idStatutStatut));
                applyUetrFallbackFromXmlIfMissing(incoming, xmlToParse, "PACS008");
                validateUetrForEmis(incoming, "PACS008");
                Optional<VirementEmis> existingOpt = findExistingEmisForIdempotence(incoming);
                VirementEmis entityToPersist = existingOpt
                        .map(existing -> mergeEmis(existing, incoming))
                        .orElse(incoming);
                virementEmisService.save(entityToPersist);
                syncAdresseForExistingEmis(existingOpt, incoming, entityToPersist);
                // Historiser chaque création et chaque modification.
                createHistoFromVirement(entityToPersist);
                if (!existingOpt.isPresent()) {
                    MessageEmis messageEmis = mapToMessageEmis(entityToPersist, msgId, sourceFilePath);
                    messageEmis.setIdMsgEmis(messageEmisRepository.nextIdMsgEmis());
                    messageEmisService.save(messageEmis);
                    log.info("PACS 008: VirementEmis créé + historisé + MessageEmis, idVrtEmis={}", entityToPersist.getIdVrtEmis());
                } else {
                    updateLatestMessageEmisFileMeta(entityToPersist.getIdVrtEmis(), sourceFilePath);
                    log.info("PACS 008: VirementEmis actualisé + historisé: uetr={}, reference={}, idVrtEmis={}",
                            entityToPersist.getUetr(), entityToPersist.getReference(), entityToPersist.getIdVrtEmis());
                }
                return;
            }
            MxPacs00900108 mx = MxPacs00900108.parse(xmlToParse);
            String msgId = mx.getFICdtTrf() != null && mx.getFICdtTrf().getGrpHdr() != null
                    ? mx.getFICdtTrf().getGrpHdr().getMsgId()
                    : null;
            String bicCode = extractBicCodeFromPacs009(mx);
            long idSop = resolveSopIdFromBusinessSource(xmlToParse, "PACS009");
            long idStatutStatut = referenceDataResolver.resolveStatutIdByCode("INTEGRE");
            long codeBic = referenceDataResolver.resolveCodeBicFromBic(bicCode);
            long idAdresse = safeResolveAdresseIdFromPacs009(mx, "PACS009");
            Long nextIdEmis = virementEmisRepository.nextIdVrtEmis();
            VirementEmis incoming = pacs009ToVirementEmisMapper.toVirementEmis(mx, nextIdEmis,
                    idStatutStatut, idAdresse, idSop, codeBic, CODE_MSG_PACS009);
            // Statut initial côté émission : code_statut (INTEGRE)
            incoming.setStatut(resolveCodeStatut(idStatutStatut));
            applyUetrFallbackFromXmlIfMissing(incoming, xmlToParse, "PACS009");
            validateUetrForEmis(incoming, "PACS009");
            Optional<VirementEmis> existingOpt = findExistingEmisForIdempotence(incoming);
            VirementEmis entityToPersist = existingOpt
                    .map(existing -> mergeEmis(existing, incoming))
                    .orElse(incoming);
            virementEmisService.save(entityToPersist);
            syncAdresseForExistingEmis(existingOpt, incoming, entityToPersist);
            // Historiser chaque création et chaque modification.
            createHistoFromVirement(entityToPersist);
            if (!existingOpt.isPresent()) {
                MessageEmis messageEmis = mapToMessageEmis(entityToPersist, msgId, sourceFilePath);
                messageEmis.setIdMsgEmis(messageEmisRepository.nextIdMsgEmis());
                messageEmisService.save(messageEmis);
                log.info("PACS 009: VirementEmis créé + historisé + MessageEmis, idVrtEmis={}", entityToPersist.getIdVrtEmis());
            } else {
                updateLatestMessageEmisFileMeta(entityToPersist.getIdVrtEmis(), sourceFilePath);
                log.info("PACS 009: VirementEmis actualisé + historisé: uetr={}, reference={}, idVrtEmis={}",
                        entityToPersist.getUetr(), entityToPersist.getReference(), entityToPersist.getIdVrtEmis());
            }
        } catch (Exception e) {
            log.error("Erreur lors du parsing {} via Prowide", messageType.getLabel(), e);
            throw new IllegalArgumentException("Erreur lors du parsing MX : " + e.getMessage(), e);
        }
    }

    private Optional<VirementEmis> findExistingEmisForIdempotence(VirementEmis incoming) {
        if (incoming == null) {
            return Optional.empty();
        }
        String uetr = incoming.getUetr() != null ? incoming.getUetr().trim() : "";
        if (!uetr.isEmpty()) {
            return virementEmisRepository
                    .findByUetrForDedup(uetr, PageRequest.of(0, 1))
                    .stream()
                    .findFirst();
        }

        String reference = incoming.getReference() != null ? incoming.getReference().trim() : "";
        Long idSop = incoming.getIdSop();
        if (reference.isEmpty() || idSop == null) {
            return Optional.empty();
        }
        Optional<VirementEmis> fallback = virementEmisRepository.findByReferenceAndIdSop(reference, idSop)
                .stream()
                .filter(v -> java.util.Objects.equals(v.getCodeMsgTypeMessage(), incoming.getCodeMsgTypeMessage()))
                .max(java.util.Comparator.comparing(VirementEmis::getIdVrtEmis));
        fallback.ifPresent(v -> log.info(
                "EMIS idempotence fallback (sans UETR): reference={}, idSop={}, idVrtEmis={}",
                reference, idSop, v.getIdVrtEmis()));
        return fallback;
    }

    private void applyUetrFallbackFromXmlIfMissing(VirementEmis incoming, String xml, String flow) {
        if (incoming == null) {
            return;
        }
        String current = incoming.getUetr() != null ? incoming.getUetr().trim() : "";
        if (!current.isEmpty()) {
            return;
        }
        String fallback = extractFirstUetrFromXml(xml);
        if (!fallback.isEmpty()) {
            incoming.setUetr(truncate(fallback, 254));
            log.info("{}: UETR récupéré via fallback XML -> {}", flow, incoming.getUetr());
        } else {
            log.warn("{}: UETR absent après fallback XML", flow);
        }
    }

    private String extractFirstUetrFromXml(String xml) {
        if (xml == null || xml.trim().isEmpty()) {
            return "";
        }
        String fromDom = extractFirstUetrWithDom(xml);
        if (!fromDom.isEmpty()) {
            return fromDom;
        }
        java.util.regex.Matcher matcher = UETR_XML_PATTERN.matcher(xml);
        if (!matcher.find()) {
            return "";
        }
        String value = matcher.group(1);
        return value != null ? value.trim() : "";
    }

    private String extractFirstUetrWithDom(String xml) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            try {
                factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
                factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
                factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            } catch (Exception ignored) { }
            Document doc = factory.newDocumentBuilder().parse(new InputSource(new StringReader(xml)));
            NodeList nodes = doc.getElementsByTagNameNS("*", "UETR");
            for (int i = 0; i < nodes.getLength(); i++) {
                Node n = nodes.item(i);
                if (n == null) {
                    continue;
                }
                String text = n.getTextContent();
                String trimmed = text != null ? text.trim() : "";
                if (!trimmed.isEmpty()) {
                    return trimmed;
                }
            }
        } catch (Exception ignored) {
            // fallback regex juste après
        }
        return "";
    }

    private long resolveSopIdFromBusinessSource(String xml, String flow) {
        String sopLibelle = extractFirstSopBusinessValue(xml);
        if (sopLibelle.isEmpty()) {
            log.warn("{}: SOP métier introuvable dans le message, fallback id_sop={}", flow, DEFAULT_ID_SOP);
            return DEFAULT_ID_SOP;
        }
        return sopRepository.findByLibelleSopNormalized(sopLibelle)
                .map(s -> s.getId())
                .orElseGet(() -> {
                    log.warn("{}: SOP métier '{}' absent de la table sop, fallback id_sop={}",
                            flow, sopLibelle, DEFAULT_ID_SOP);
                    return DEFAULT_ID_SOP;
                });
    }

    private String extractFirstSopBusinessValue(String xml) {
        if (xml == null || xml.trim().isEmpty()) {
            return "";
        }
        String fromDom = extractFirstSopWithDom(xml);
        if (!fromDom.isEmpty()) {
            return fromDom;
        }
        java.util.regex.Matcher matcher = SOP_XML_PATTERN.matcher(xml);
        if (!matcher.find()) {
            return "";
        }
        String value = matcher.group(1);
        return value != null ? value.trim() : "";
    }

    private String extractFirstSopWithDom(String xml) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            try {
                factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
                factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
                factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            } catch (Exception ignored) { }
            Document doc = factory.newDocumentBuilder().parse(new InputSource(new StringReader(xml)));
            String[] names = { "SOP", "SystemOperant", "SystemeOperant", "BizSvc", "BusinessService" };
            for (String name : names) {
                NodeList nodes = doc.getElementsByTagNameNS("*", name);
                for (int i = 0; i < nodes.getLength(); i++) {
                    Node n = nodes.item(i);
                    if (n == null) {
                        continue;
                    }
                    String text = n.getTextContent();
                    String trimmed = text != null ? text.trim() : "";
                    if (!trimmed.isEmpty()) {
                        return trimmed;
                    }
                }
            }
        } catch (Exception ignored) {
            // fallback regex juste après
        }
        return "";
    }

    private void validateUetrForEmis(VirementEmis incoming, String flow) {
        String uetr = incoming != null && incoming.getUetr() != null ? incoming.getUetr().trim() : "";
        if (uetr.isEmpty()) {
            log.warn("{}: UETR manquant -> message EMIS stocké sans idempotence UETR (pas de sync id_adresse par UETR)", flow);
        }
        incoming.setUetr(uetr);
    }

    private VirementEmis mergeEmis(VirementEmis target, VirementEmis source) {
        target.setReference(source.getReference());
        target.setDenominationBnf(source.getDenominationBnf());
        target.setNumCompteBnf(source.getNumCompteBnf());
        target.setNumCompteOrd(source.getNumCompteOrd());
        target.setDenominationOrd(source.getDenominationOrd());
        target.setMontant(source.getMontant());
        target.setDateValeur(source.getDateValeur());
        target.setRenseignement(source.getRenseignement());
        target.setCodeDevise(source.getCodeDevise());
        target.setDateIntegration(source.getDateIntegration());
        target.setBicOrdonnateur(source.getBicOrdonnateur());
        target.setBicBeneficiaire(source.getBicBeneficiaire());
        target.setUetr(source.getUetr());
        target.setEndToEnd(source.getEndToEnd());
        target.setStatut(source.getStatut());
        return target;
    }

    /**
     * Si le même UETR revient avec une nouvelle adresse, on met à jour id_adresse
     * sur la même ligne virement_emis (id_vrt_emis constant).
     */
    private void syncAdresseForExistingEmis(Optional<VirementEmis> existingOpt,
                                            VirementEmis incoming,
                                            VirementEmis persisted) {
        if (!existingOpt.isPresent() || incoming == null || persisted == null) {
            return;
        }
        Long oldAdresse = existingOpt.get().getIdAdresseAdresse();
        Long newAdresse = incoming.getIdAdresseAdresse();
        if (newAdresse == null || java.util.Objects.equals(oldAdresse, newAdresse)) {
            return;
        }
        int updated = virementEmisRepository.updateAdresseByIdVrtEmis(persisted.getIdVrtEmis(), newAdresse);
        if (updated > 0) {
            log.info("EMIS UETR {}: id_adresse mis à jour {} -> {} sur idVrtEmis={}",
                    persisted.getUetr(), oldAdresse, newAdresse, persisted.getIdVrtEmis());
        } else {
            log.warn("EMIS UETR {}: échec mise à jour id_adresse (idVrtEmis={}, old={}, new={})",
                    persisted.getUetr(), persisted.getIdVrtEmis(), oldAdresse, newAdresse);
        }
    }

    private void ensureReferenceDataExists() {
        ensureDefaultAdresseExists();
        ensureDefaultSopExists();
        StringBuilder missing = new StringBuilder();
        if (!statutRepository.findById(DEFAULT_ID_STATUT).isPresent()) missing.append(" statut(id=1)");
        if (!typeMessageRepository.findById(CODE_MSG_PACS008).isPresent()) missing.append(" type_message(code_msg=1)");
        if (!typeMessageRepository.findById(CODE_MSG_PACS009).isPresent()) missing.append(" type_message(code_msg=2)");
        if (missing.length() > 0) {
            throw new MissingReferenceDataException(
                "Données de référence manquantes. Créer en base:" + missing + ".");
        }
    }

    private void ensureDefaultAdresseExists() {
        if (adresseRepository.findById(DEFAULT_ID_ADRESSE).isPresent()) {
            return;
        }
        Adresse adresse = new Adresse();
        adresse.setIdAdresse(DEFAULT_ID_ADRESSE);
        adresse.setIdTypeAdresse(ID_TYPE_ADRESSE_DBTR);
        adresse.setLigne1("ADRESSE PAR DEFAUT");
        adresse.setVille("CASABLANCA");
        adresse.setCodePostal("00000");
        adresse.setPays("MA");
        adresseRepository.save(adresse);
        log.warn("Référence auto-créée: adresse(id={})", DEFAULT_ID_ADRESSE);
    }

    private void ensureDefaultSopExists() {
        if (sopRepository.findById(DEFAULT_ID_SOP).isPresent()) {
            return;
        }
        Sop sop = new Sop();
        sop.setId(DEFAULT_ID_SOP);
        sop.setLibelleSop("SOP PAR DEFAUT");
        sopRepository.save(sop);
        log.warn("Référence auto-créée: sop(id={})", DEFAULT_ID_SOP);
    }

    private static String extractBicCodeFromPacs008(MxPacs00800108 mx) {
        if (mx == null || mx.getFIToFICstmrCdtTrf() == null || mx.getFIToFICstmrCdtTrf().getCdtTrfTxInf() == null
                || mx.getFIToFICstmrCdtTrf().getCdtTrfTxInf().isEmpty()) return null;
        com.prowidesoftware.swift.model.mx.dic.CreditTransferTransaction39 tx = mx.getFIToFICstmrCdtTrf().getCdtTrfTxInf().get(0);
        if (tx.getInstgAgt() != null && tx.getInstgAgt().getFinInstnId() != null && tx.getInstgAgt().getFinInstnId().getBICFI() != null)
            return tx.getInstgAgt().getFinInstnId().getBICFI();
        if (tx.getInstdAgt() != null && tx.getInstdAgt().getFinInstnId() != null && tx.getInstdAgt().getFinInstnId().getBICFI() != null)
            return tx.getInstdAgt().getFinInstnId().getBICFI();
        return null;
    }

    private static String extractBicCodeFromPacs009(MxPacs00900108 mx) {
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
        String ligne2 = truncate(
                ((pstlAdr.getFlr() != null) ? pstlAdr.getFlr() : "") +
                        ((pstlAdr.getPstBx() != null) ? " " + pstlAdr.getPstBx() : "") +
                        ((pstlAdr.getDept() != null) ? " " + pstlAdr.getDept() : "") +
                        ((pstlAdr.getSubDept() != null) ? " " + pstlAdr.getSubDept() : "") +
                        ((pstlAdr.getDstrctNm() != null) ? " " + pstlAdr.getDstrctNm() : ""),
                255);
        String ville = truncate(pstlAdr.getTwnNm() != null ? pstlAdr.getTwnNm() : pstlAdr.getTwnLctnNm(), 150);
        String codePostal = truncate(pstlAdr.getPstCd() != null ? pstlAdr.getPstCd() : "", 20);
        String pays = truncate(pstlAdr.getCtry() != null ? pstlAdr.getCtry() : pstlAdr.getCtrySubDvsn(), 100);

        // Compléter à partir de AdrLine si présent (ex: PACS/CAMT avec AdrLine seulement)
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

        final String fLigne1 = normalizeNullable(ligne1);
        final String fLigne2 = normalizeNullable(ligne2);
        final String fVille = normalizeNullable(ville);
        final String fCodePostal = normalizeNullable(codePostal);
        final String fPays = normalizeNullable(pays);
        final long fIdTypeAdresse = idTypeAdresse;

        // Priorité: réutiliser la même adresse textuelle (même ID) même si le type DBTR/CDTR diffère.
        return adresseRepository
                .findByLigne1AndLigne2AndVilleAndCodePostalAndPays(fLigne1, fLigne2, fVille, fCodePostal, fPays)
                .map(Adresse::getIdAdresse)
                .orElseGet(() -> adresseRepository
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
                        }));
    }

    private long safeResolveAdresseIdFromPostalAdr(com.prowidesoftware.swift.model.mx.dic.PostalAddress24 pstlAdr,
                                                   long idTypeAdresse,
                                                   String flow) {
        try {
            return resolveAdresseIdFromPostalAdr(pstlAdr, idTypeAdresse);
        } catch (Exception e) {
            log.warn("{}: erreur résolution adresse depuis PostalAdr, fallback id_adresse={} ({})",
                    flow, DEFAULT_ID_ADRESSE, e.getMessage());
            return DEFAULT_ID_ADRESSE;
        }
    }

    private static String normalizeNullable(String value) {
        if (value == null) return null;
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }

    private long resolveAdresseIdFromPacs008(MxPacs00800108 mx) {
        if (mx == null || mx.getFIToFICstmrCdtTrf() == null
                || mx.getFIToFICstmrCdtTrf().getCdtTrfTxInf() == null
                || mx.getFIToFICstmrCdtTrf().getCdtTrfTxInf().isEmpty()) {
            return DEFAULT_ID_ADRESSE;
        }
        for (com.prowidesoftware.swift.model.mx.dic.CreditTransferTransaction39 tx : mx.getFIToFICstmrCdtTrf().getCdtTrfTxInf()) {
            com.prowidesoftware.swift.model.mx.dic.PostalAddress24 pstlAdr = null;
            long idTypeAdresse = ID_TYPE_ADRESSE_DBTR;
            // 1) Adresse créditeur (bénéficiaire) si disponible → CDTR (2)
            if (tx.getCdtr() != null && tx.getCdtr().getPstlAdr() != null) {
                pstlAdr = tx.getCdtr().getPstlAdr();
                idTypeAdresse = ID_TYPE_ADRESSE_CDTR;
            }
            // 2) Sinon adresse débiteur → DBTR (1)
            else if (tx.getDbtr() != null && tx.getDbtr().getPstlAdr() != null) {
                pstlAdr = tx.getDbtr().getPstlAdr();
                idTypeAdresse = ID_TYPE_ADRESSE_DBTR;
            }
            // 3) Sinon adresse de la banque ordonnatrice (agent instigateur)
            else if (tx.getInstgAgt() != null
                    && tx.getInstgAgt().getFinInstnId() != null
                    && tx.getInstgAgt().getFinInstnId().getPstlAdr() != null) {
                pstlAdr = tx.getInstgAgt().getFinInstnId().getPstlAdr();
                idTypeAdresse = ID_TYPE_ADRESSE_DBTR;
            }
            // 4) Sinon adresse de la banque bénéficiaire (agent instruit)
            else if (tx.getInstdAgt() != null
                    && tx.getInstdAgt().getFinInstnId() != null
                    && tx.getInstdAgt().getFinInstnId().getPstlAdr() != null) {
                pstlAdr = tx.getInstdAgt().getFinInstnId().getPstlAdr();
                idTypeAdresse = ID_TYPE_ADRESSE_CDTR;
            }
            long resolved = resolveAdresseIdFromPostalAdr(pstlAdr, idTypeAdresse);
            if (resolved != DEFAULT_ID_ADRESSE) {
                return resolved;
            }
        }
        return DEFAULT_ID_ADRESSE;
    }

    private long safeResolveAdresseIdFromPacs008(MxPacs00800108 mx, String flow) {
        try {
            return resolveAdresseIdFromPacs008(mx);
        } catch (Exception e) {
            log.warn("{}: erreur résolution adresse PACS008, fallback id_adresse={} ({})",
                    flow, DEFAULT_ID_ADRESSE, e.getMessage());
            return DEFAULT_ID_ADRESSE;
        }
    }

    private long resolveAdresseIdFromPacs009(MxPacs00900108 mx) {
        if (mx == null || mx.getFICdtTrf() == null
                || mx.getFICdtTrf().getCdtTrfTxInf() == null
                || mx.getFICdtTrf().getCdtTrfTxInf().isEmpty()) {
            return DEFAULT_ID_ADRESSE;
        }
        for (com.prowidesoftware.swift.model.mx.dic.CreditTransferTransaction36 tx : mx.getFICdtTrf().getCdtTrfTxInf()) {
            com.prowidesoftware.swift.model.mx.dic.PostalAddress24 pstlAdr = null;
            long idTypeAdresse = ID_TYPE_ADRESSE_DBTR;

            if (tx.getInstgAgt() != null
                    && tx.getInstgAgt().getFinInstnId() != null
                    && tx.getInstgAgt().getFinInstnId().getPstlAdr() != null) {
                pstlAdr = tx.getInstgAgt().getFinInstnId().getPstlAdr();
                idTypeAdresse = ID_TYPE_ADRESSE_DBTR;
            } else if (tx.getInstdAgt() != null
                    && tx.getInstdAgt().getFinInstnId() != null
                    && tx.getInstdAgt().getFinInstnId().getPstlAdr() != null) {
                pstlAdr = tx.getInstdAgt().getFinInstnId().getPstlAdr();
                idTypeAdresse = ID_TYPE_ADRESSE_CDTR;
            }
            long resolved = resolveAdresseIdFromPostalAdr(pstlAdr, idTypeAdresse);
            if (resolved != DEFAULT_ID_ADRESSE) {
                return resolved;
            }
        }
        return DEFAULT_ID_ADRESSE;
    }

    private long safeResolveAdresseIdFromPacs009(MxPacs00900108 mx, String flow) {
        try {
            return resolveAdresseIdFromPacs009(mx);
        } catch (Exception e) {
            log.warn("{}: erreur résolution adresse PACS009, fallback id_adresse={} ({})",
                    flow, DEFAULT_ID_ADRESSE, e.getMessage());
            return DEFAULT_ID_ADRESSE;
        }
    }

    /**
     * Retourne le code_statut (INTEGRE, ACK, NACK, ...) pour un id_statut donné.
     * Si introuvable, retourne "INTEGRE" par défaut pour éviter un statut null.
     */
    private String resolveCodeStatut(long idStatut) {
        return statutRepository.findById(idStatut)
                .map(s -> truncate(s.getCodeStatut(), 50))
                .orElse("INTEGRE");
    }

    private void createHistoFromVirement(VirementEmis v) {
        Optional<VirementEmisHisto> existing = virementEmisHistoRepository
                .findTopByIdVrtEmisOrderByDateHistorisationDesc(v.getIdVrtEmis());
        if (existing.isPresent()) {
            VirementEmisHisto row = existing.get();
            row.setDateHistorisation(OffsetDateTime.now());
            virementEmisHistoRepository.save(row);
            return;
        }

        VirementEmisHisto h = new VirementEmisHisto();
        h.setIdVrtEmisHisto(virementEmisHistoRepository.nextIdVrtEmisHisto());
        h.setIdVrtEmis(v.getIdVrtEmis());
        // Respecter la contrainte NOT NULL de la colonne id_vrt_emis_virement_emis
        h.setIdVrtEmisVirementEmis(v.getIdVrtEmis());
        // Respecter la contrainte NOT NULL de la colonne id_sop dans virement_emis_histo
        h.setIdSop(v.getIdSop());
        // Respecter la contrainte NOT NULL de la colonne code_bic dans virement_emis_histo
        h.setCodeBic(v.getCodeBicBic());
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
        h.setBicOrdonnateur(v.getBicOrdonnateur());
        h.setBicBeneficiaire(v.getBicBeneficiaire());
        h.setUetr(v.getUetr());
        h.setEndToEnd(v.getEndToEnd());
        h.setDateHistorisation(OffsetDateTime.now());
        virementEmisHistoRepository.save(h);
    }

    private MessageEmis mapToMessageEmis(VirementEmis v, String msgId, String sourceFilePath) {
        MessageEmis m = new MessageEmis();
        m.setIdVrtEmisVirementEmis(v.getIdVrtEmis());
        m.setIdSopVirementEmis(v.getIdSop());
        m.setIdStatutStatutVirementEmis(v.getIdStatutStatut());
        m.setIdAdresseAdresseVirementEmis(v.getIdAdresseAdresse());
        m.setCodeBicBicVirementEmis(v.getCodeBicBic());
        m.setCodeMsgTypeMessageVirementEmis(v.getCodeMsgTypeMessage());
        m.setIdVrtEmis(v.getIdVrtEmis());
        m.setReference(msgId != null ? truncate(msgId, 35) : v.getReference());
        m.setSop(v.getIdSop());
        m.setNom(sourceFilePath == null ? null : new java.io.File(sourceFilePath).getName());
        m.setPath(sourceFilePath);
        m.setVirementEmis(v);
        return m;
    }

    private void updateLatestMessageEmisFileMeta(Long idVrtEmis, String sourceFilePath) {
        if (idVrtEmis == null || sourceFilePath == null || sourceFilePath.trim().isEmpty()) {
            return;
        }
        messageEmisRepository.findTopByIdVrtEmisOrderByIdMsgEmisDesc(idVrtEmis).ifPresent(m -> {
            m.setNom(new java.io.File(sourceFilePath).getName());
            m.setPath(sourceFilePath);
            messageEmisRepository.save(m);
        });
    }
}
