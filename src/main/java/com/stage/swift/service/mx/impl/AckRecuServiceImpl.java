package com.stage.swift.service.mx.impl;

import com.prowidesoftware.swift.model.mx.MxPacs00800108;
import com.prowidesoftware.swift.model.mx.MxPacs00900108;
import com.stage.swift.entity.Adresse;
import com.stage.swift.entity.MessageRecu;
import com.stage.swift.entity.Sop;
import com.stage.swift.entity.VirementRecu;
import com.stage.swift.entity.VirementRecuHisto;
import com.stage.swift.enums.MessageMX;
import com.stage.swift.helpers.SaaEnvelopeHelper;
import com.stage.swift.mappers.mx.PACS008ToVirementRecuMapper;
import com.stage.swift.mappers.mx.PACS009ToVirementRecuMapper;
import com.stage.swift.repository.AdresseRepository;
import com.stage.swift.repository.MessageRecuRepository;
import com.stage.swift.repository.SopRepository;
import com.stage.swift.repository.VirementRecuHistoRepository;
import com.stage.swift.repository.VirementRecuRepository;
import com.stage.swift.service.entity.MessageRecuService;
import com.stage.swift.service.entity.VirementRecuService;
import com.stage.swift.service.mx.AckRecuService;
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

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static com.stage.swift.helpers.MxMapperHelper.truncate;

/**
 * Flux reçu OUT_SAA
 */
@Service
public class AckRecuServiceImpl implements AckRecuService {

    private static final Logger log = LoggerFactory.getLogger(AckRecuServiceImpl.class);

    private static final long DEFAULT_ID_ADRESSE = 0L;
    private static final long CODE_MSG_PACS008 = 1L;
    private static final long CODE_MSG_PACS009 = 2L;
    private static final long ID_VIREMENT_EMIS_NON_RAPPROCHE = 0L;
    /** type_adresse : 1 = DBTR (débiteur), 2 = CDTR (créditeur) */
    private static final long ID_TYPE_ADRESSE_DBTR = 1L;
    private static final long ID_TYPE_ADRESSE_CDTR = 2L;
    private static final Pattern SOP_XML_PATTERN = Pattern.compile(
            "<(?:[A-Za-z0-9_\\-]+:)?(?:SOP|SystemOperant|SystemeOperant|BizSvc|BusinessService)(?:\\s+[^>]*)?>\\s*([^<]+?)\\s*</(?:[A-Za-z0-9_\\-]+:)?(?:SOP|SystemOperant|SystemeOperant|BizSvc|BusinessService)\\s*>",
            Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
    private static final Pattern SOP_FOLDER_PATTERN = Pattern.compile("(?i)(?:^|[\\\\/])SOP_(\\d+)(?:[_-]([^\\\\/]+))?(?:[\\\\/]|$)");

    private final VirementRecuRepository virementRecuRepository;
    private final VirementRecuHistoRepository virementRecuHistoRepository;
    private final MessageRecuRepository messageRecuRepository;
    private final VirementRecuService virementRecuService;
    private final MessageRecuService messageRecuService;
    private final PACS008ToVirementRecuMapper pacs008ToVirementRecuMapper;
    private final PACS009ToVirementRecuMapper pacs009ToVirementRecuMapper;
    private final ReferenceDataResolver referenceDataResolver;
    private final AdresseRepository adresseRepository;
    private final SopRepository sopRepository;

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
                              AdresseRepository adresseRepository,
                              SopRepository sopRepository) {
        this.virementRecuRepository = virementRecuRepository;
        this.virementRecuHistoRepository = virementRecuHistoRepository;
        this.messageRecuRepository = messageRecuRepository;
        this.virementRecuService = virementRecuService;
        this.messageRecuService = messageRecuService;
        this.pacs008ToVirementRecuMapper = pacs008ToVirementRecuMapper;
        this.pacs009ToVirementRecuMapper = pacs009ToVirementRecuMapper;
        this.referenceDataResolver = referenceDataResolver;
        this.adresseRepository = adresseRepository;
        this.sopRepository = sopRepository;
    }

    @Override
    @Transactional
    public void processAckFileRecu(String ackContent) {
        processAckFileRecu(ackContent, null);
    }

    @Override
    @Transactional
    public void processAckFileRecu(String ackContent, String sourceFilePath) {
        if (ackContent == null || ackContent.trim().isEmpty()) {
            throw new IllegalArgumentException("Contenu .ack recu vide");
        }
        ensureDefaultAdresseExists();
        if (hasMultipleXmlDeclarations(ackContent)) {
            List<String> singleDocs = splitIntoSingleXmlDocuments(ackContent);
            for (String doc : singleDocs) {
                if (doc != null && !doc.trim().isEmpty()) {
                    processAckFileRecu(doc, sourceFilePath);
                }
            }
            return;
        }

        List<String> allDocs = SaaEnvelopeHelper.extractAllPacsDocuments(ackContent);
        if (!allDocs.isEmpty()) {
            for (String docXml : allDocs) {
                parseAndSaveRecuOne(docXml, sourceFilePath);
            }
            return;
        }

        MessageMX messageType = SaaEnvelopeHelper.getMessageTypeFromXml(ackContent);
        if (messageType == null) {
            throw new IllegalArgumentException("Type de message MX non supporté dans .ack recu (pacs.008, pacs.009 ou camt.054 attendu)");
        }
        String xmlToParse = SaaEnvelopeHelper.xmlToParse(ackContent, messageType);
        if (messageType == MessageMX.CAMT054) {
            parseAndSaveCamt054Recu(xmlToParse, ackContent, sourceFilePath);
            return;
        }
        parseAndSaveRecuOne(xmlToParse, sourceFilePath);
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
            if (tx.getCdtr() != null && tx.getCdtr().getPstlAdr() != null) {
                pstlAdr = tx.getCdtr().getPstlAdr();
                idTypeAdresse = ID_TYPE_ADRESSE_CDTR;
            } else if (tx.getDbtr() != null && tx.getDbtr().getPstlAdr() != null) {
                pstlAdr = tx.getDbtr().getPstlAdr();
                idTypeAdresse = ID_TYPE_ADRESSE_DBTR;
            } else if (tx.getInstgAgt() != null
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
            long resolved = safeResolveAdresseIdFromPostalAdr(pstlAdr, idTypeAdresse, "OUT_SAA PACS008");
            if (resolved != DEFAULT_ID_ADRESSE) {
                return resolved;
            }
        }
        return DEFAULT_ID_ADRESSE;
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
            long resolved = safeResolveAdresseIdFromPostalAdr(pstlAdr, idTypeAdresse, "OUT_SAA PACS009");
            if (resolved != DEFAULT_ID_ADRESSE) {
                return resolved;
            }
        }
        return DEFAULT_ID_ADRESSE;
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

    private long safeResolveAdresseIdFromPacs008(MxPacs00800108 mx, String flow) {
        try {
            return resolveAdresseIdFromPacs008(mx);
        } catch (Exception e) {
            log.warn("{}: erreur résolution adresse PACS008, fallback id_adresse={} ({})",
                    flow, DEFAULT_ID_ADRESSE, e.getMessage());
            return DEFAULT_ID_ADRESSE;
        }
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

    private static String extractBicFromCamt054Tx(com.prowidesoftware.swift.model.mx.dic.EntryTransaction10 tx) {
        if (tx == null || tx.getRltdAgts() == null) return null;
        if (tx.getRltdAgts().getInstgAgt() != null && tx.getRltdAgts().getInstgAgt().getFinInstnId() != null && tx.getRltdAgts().getInstgAgt().getFinInstnId().getBICFI() != null)
            return tx.getRltdAgts().getInstgAgt().getFinInstnId().getBICFI();
        if (tx.getRltdAgts().getInstdAgt() != null && tx.getRltdAgts().getInstdAgt().getFinInstnId() != null && tx.getRltdAgts().getInstdAgt().getFinInstnId().getBICFI() != null)
            return tx.getRltdAgts().getInstdAgt().getFinInstnId().getBICFI();
        return null;
    }

    private void parseAndSaveCamt054Recu(String xmlToParse, String fullXml, String sourceFilePath) {
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
                        long idSop = resolveSopIdFromBusinessSource(xmlToParse, "OUT_SAA CAMT054", sourceFilePath);
                        long idStatutStatut = referenceDataResolver.resolveStatutIdByCode("INTEGRE");
                        long codeBic = referenceDataResolver.resolveCodeBicFromBic(bicCode != null ? bicCode : "", idSop);
                        Long nextIdRecu = virementRecuRepository.nextIdVrtRecu();
                        VirementRecu v = mapCamt054TxToVirementRecu(tx, ntry, grpMsgId, nextIdRecu, idSop, idStatutStatut, codeBic);
                        validateUetrForRecu(v, "OUT_SAA CAMT054");
                        // Idempotence CAMT054 basée uniquement sur UETR.
                        Optional<VirementRecu> existingOpt = virementRecuRepository
                                .findByUetrForDedup(v.getUetr(), PageRequest.of(0, 1))
                                .stream()
                                .findFirst();
                        VirementRecu entityToPersist = existingOpt
                                .map(existing -> {
                                    existing.setMontant(v.getMontant());
                                    existing.setCodeDevise(v.getCodeDevise());
                                    existing.setDateValeur(v.getDateValeur());
                                    existing.setDateIntegration(v.getDateIntegration());
                                    existing.setDenominationOrd(v.getDenominationOrd());
                                    existing.setDenominationBnf(v.getDenominationBnf());
                                    existing.setNumCompteOrd(v.getNumCompteOrd());
                                    existing.setNumCompteBnf(v.getNumCompteBnf());
                                    existing.setBicOrdonnateur(v.getBicOrdonnateur());
                                    existing.setBicBeneficiaire(v.getBicBeneficiaire());
                                    existing.setRenseignement(v.getRenseignement());
                                    existing.setUetr(v.getUetr());
                                    existing.setEndToEnd(v.getEndToEnd());
                                    existing.setStatutRecu(v.getStatutRecu());
                                    return existing;
                                })
                                .orElse(v);

                        virementRecuService.save(entityToPersist);
                        syncAdresseForExistingRecu(existingOpt, v, entityToPersist);
                        entityManager.flush();
                        // Historiser chaque création et chaque modification.
                        createHistoFromVirementRecu(entityToPersist, ID_VIREMENT_EMIS_NON_RAPPROCHE);
                        if (!existingOpt.isPresent() && !messageRecuRepository.existsByIdVrtRecuVirementRecu(entityToPersist.getIdVrtRecu())) {
                            MessageRecu m = mapToMessageRecu(entityToPersist, tx.getRefs() != null ? tx.getRefs().getMsgId() : grpMsgId, sourceFilePath);
                            m.setIdMsgRecu(messageRecuRepository.nextIdMsgRecu());
                            messageRecuService.save(m);
                            entityManager.flush();
                        } else if (!existingOpt.isPresent()) {
                            updateLatestMessageRecuFileMeta(entityToPersist.getIdVrtRecu(), sourceFilePath);
                            log.warn("OUT_SAA CAMT054: MessageRecu déjà existant pour idVrtRecu={}, insertion ignorée", entityToPersist.getIdVrtRecu());
                        } else {
                            updateLatestMessageRecuFileMeta(entityToPersist.getIdVrtRecu(), sourceFilePath);
                        }
                        log.info("OUT_SAA CAMT054: VirementRecu {}, idVrtRecu={}",
                                existingOpt.isPresent() ? "actualisé + historisé" : "créé + historisé (MessageRecu si absent)",
                                entityToPersist.getIdVrtRecu());
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
        long idAdresse = safeResolveAdresseIdFromPostalAdr(pstlAdr, ID_TYPE_ADRESSE_DBTR, "OUT_SAA CAMT054");

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

    private void parseAndSaveRecuOne(String xmlToParse, String sourceFilePath) {
        MessageMX messageType = MessageMX.fromXmlContent(xmlToParse);
        if (messageType == null || messageType == MessageMX.CAMT054) {
            throw new IllegalArgumentException("Document recu non reconnu (pacs.008 ou pacs.009 attendu pour fragment)");
        }
        try {
            if (messageType == MessageMX.PACS008) {
                MxPacs00800108 mx = MxPacs00800108.parse(xmlToParse);
                String bicCode = extractBicFromPacs008(mx);
                long idSop = resolveSopIdFromBusinessSource(xmlToParse, "OUT_SAA PACS008", sourceFilePath);
                long idStatutStatut = referenceDataResolver.resolveStatutIdByCode("INTEGRE");
                long codeBic = referenceDataResolver.resolveCodeBicFromBic(bicCode != null ? bicCode : "", idSop);
                long idAdresse = safeResolveAdresseIdFromPacs008(mx, "OUT_SAA PACS008");
                String msgId = mx.getFIToFICstmrCdtTrf() != null && mx.getFIToFICstmrCdtTrf().getGrpHdr() != null
                        ? mx.getFIToFICstmrCdtTrf().getGrpHdr().getMsgId() : null;
                Long nextIdRecu = virementRecuRepository.nextIdVrtRecu();
                VirementRecu v = pacs008ToVirementRecuMapper.toVirementRecu(mx, nextIdRecu,
                        idStatutStatut, idAdresse, idSop, codeBic, CODE_MSG_PACS008);
                // Statut rapprochement initial côté reçu : INTEGRE
                v.setStatutRecu("INTEGRE");
                validateUetrForRecu(v, "OUT_SAA PACS008");

                // Idempotence basée uniquement sur UETR.
                Optional<VirementRecu> existingOpt = virementRecuRepository
                        .findByUetrForDedup(v.getUetr(), PageRequest.of(0, 1))
                        .stream()
                        .findFirst();
                VirementRecu entityToPersist = existingOpt
                        .map(existing -> {
                            existing.setMontant(v.getMontant());
                            existing.setCodeDevise(v.getCodeDevise());
                            existing.setDateValeur(v.getDateValeur());
                            existing.setDateIntegration(v.getDateIntegration());
                            existing.setDenominationOrd(v.getDenominationOrd());
                            existing.setDenominationBnf(v.getDenominationBnf());
                            existing.setNumCompteOrd(v.getNumCompteOrd());
                            existing.setNumCompteBnf(v.getNumCompteBnf());
                            existing.setBicOrdonnateur(v.getBicOrdonnateur());
                            existing.setBicBeneficiaire(v.getBicBeneficiaire());
                            existing.setRenseignement(v.getRenseignement());
                            existing.setUetr(v.getUetr());
                            existing.setEndToEnd(v.getEndToEnd());
                            existing.setStatutRecu(v.getStatutRecu());
                            return existing;
                        })
                        .orElse(v);

                virementRecuService.save(entityToPersist);
                syncAdresseForExistingRecu(existingOpt, v, entityToPersist);
                entityManager.flush();
                // Historiser chaque création et chaque modification.
                createHistoFromVirementRecu(entityToPersist, ID_VIREMENT_EMIS_NON_RAPPROCHE);
                // Ne créer MessageRecu et histo que pour un nouveau VirementRecu (évite doublons et "More than one row").
                if (!existingOpt.isPresent() && !messageRecuRepository.existsByIdVrtRecuVirementRecu(entityToPersist.getIdVrtRecu())) {
                    MessageRecu m = mapToMessageRecu(entityToPersist, msgId, sourceFilePath);
                    m.setIdMsgRecu(messageRecuRepository.nextIdMsgRecu());
                    messageRecuService.save(m);
                    entityManager.flush();
                } else if (!existingOpt.isPresent()) {
                    updateLatestMessageRecuFileMeta(entityToPersist.getIdVrtRecu(), sourceFilePath);
                    log.warn("OUT_SAA PACS008: MessageRecu déjà existant pour idVrtRecu={}, insertion ignorée", entityToPersist.getIdVrtRecu());
                } else {
                    updateLatestMessageRecuFileMeta(entityToPersist.getIdVrtRecu(), sourceFilePath);
                }
                log.info("OUT_SAA PACS008: VirementRecu {}, idVrtRecu={}", existingOpt.isPresent() ? "actualisé + historisé" : "créé + historisé (MessageRecu si absent)", entityToPersist.getIdVrtRecu());
                return;
            }
            MxPacs00900108 mx = MxPacs00900108.parse(xmlToParse);
            String bicCode = extractBicFromPacs009(mx);
            long idSop = resolveSopIdFromBusinessSource(xmlToParse, "OUT_SAA PACS009", sourceFilePath);
            long idStatutStatut = referenceDataResolver.resolveStatutIdByCode("INTEGRE");
            long codeBic = referenceDataResolver.resolveCodeBicFromBic(bicCode != null ? bicCode : "", idSop);
            long idAdresse = safeResolveAdresseIdFromPacs009(mx, "OUT_SAA PACS009");
            String msgId = mx.getFICdtTrf() != null && mx.getFICdtTrf().getGrpHdr() != null
                    ? mx.getFICdtTrf().getGrpHdr().getMsgId() : null;
            Long nextIdRecu = virementRecuRepository.nextIdVrtRecu();
            VirementRecu v = pacs009ToVirementRecuMapper.toVirementRecu(mx, nextIdRecu,
                    idStatutStatut, idAdresse, idSop, codeBic, CODE_MSG_PACS009);
            // Statut rapprochement initial côté reçu : INTEGRE
            v.setStatutRecu("INTEGRE");
            validateUetrForRecu(v, "OUT_SAA PACS009");

            // Idempotence basée uniquement sur UETR.
            Optional<VirementRecu> existingOpt = virementRecuRepository
                    .findByUetrForDedup(v.getUetr(), PageRequest.of(0, 1))
                    .stream()
                    .findFirst();
            VirementRecu entityToPersist = existingOpt
                    .map(existing -> {
                        existing.setMontant(v.getMontant());
                        existing.setCodeDevise(v.getCodeDevise());
                        existing.setDateValeur(v.getDateValeur());
                        existing.setDateIntegration(v.getDateIntegration());
                        existing.setDenominationOrd(v.getDenominationOrd());
                        existing.setDenominationBnf(v.getDenominationBnf());
                        existing.setNumCompteOrd(v.getNumCompteOrd());
                        existing.setNumCompteBnf(v.getNumCompteBnf());
                        existing.setBicOrdonnateur(v.getBicOrdonnateur());
                        existing.setBicBeneficiaire(v.getBicBeneficiaire());
                        existing.setRenseignement(v.getRenseignement());
                        existing.setUetr(v.getUetr());
                        existing.setEndToEnd(v.getEndToEnd());
                        existing.setStatutRecu(v.getStatutRecu());
                        return existing;
                    })
                    .orElse(v);

            virementRecuService.save(entityToPersist);
            syncAdresseForExistingRecu(existingOpt, v, entityToPersist);
            entityManager.flush();
            // Historiser chaque création et chaque modification.
            createHistoFromVirementRecu(entityToPersist, ID_VIREMENT_EMIS_NON_RAPPROCHE);
            if (!existingOpt.isPresent() && !messageRecuRepository.existsByIdVrtRecuVirementRecu(entityToPersist.getIdVrtRecu())) {
                MessageRecu m = mapToMessageRecu(entityToPersist, msgId, sourceFilePath);
                m.setIdMsgRecu(messageRecuRepository.nextIdMsgRecu());
                messageRecuService.save(m);
                entityManager.flush();
            } else if (!existingOpt.isPresent()) {
                updateLatestMessageRecuFileMeta(entityToPersist.getIdVrtRecu(), sourceFilePath);
                log.warn("OUT_SAA PACS009: MessageRecu déjà existant pour idVrtRecu={}, insertion ignorée", entityToPersist.getIdVrtRecu());
            } else {
                updateLatestMessageRecuFileMeta(entityToPersist.getIdVrtRecu(), sourceFilePath);
            }
            log.info("OUT_SAA PACS009: VirementRecu {}, idVrtRecu={}", existingOpt.isPresent() ? "actualisé + historisé" : "créé + historisé (MessageRecu si absent)", entityToPersist.getIdVrtRecu());
        } catch (Exception e) {
            log.error("Erreur parsing recu {} via Prowide", messageType.getLabel(), e);
            throw new IllegalArgumentException("Erreur parsing MX recu: " + e.getMessage(), e);
        }
    }

    /**
     * Si le même UETR revient avec une nouvelle adresse, on met à jour id_adresse
     * sur la même ligne virement_recu (id_vrt_recu constant).
     */
    private void syncAdresseForExistingRecu(Optional<VirementRecu> existingOpt,
                                            VirementRecu incoming,
                                            VirementRecu persisted) {
        if (!existingOpt.isPresent() || incoming == null || persisted == null) {
            return;
        }
        Long oldAdresse = existingOpt.get().getIdAdresseAdresse();
        Long newAdresse = incoming.getIdAdresseAdresse();
        if (newAdresse == null || java.util.Objects.equals(oldAdresse, newAdresse)) {
            return;
        }
        int updated = virementRecuRepository.updateAdresseByIdVrtRecu(persisted.getIdVrtRecu(), newAdresse);
        if (updated > 0) {
            log.info("RECU UETR {}: id_adresse mis à jour {} -> {} sur idVrtRecu={}",
                    persisted.getUetr(), oldAdresse, newAdresse, persisted.getIdVrtRecu());
        } else {
            log.warn("RECU UETR {}: échec mise à jour id_adresse (idVrtRecu={}, old={}, new={})",
                    persisted.getUetr(), persisted.getIdVrtRecu(), oldAdresse, newAdresse);
        }
    }

    private void validateUetrForRecu(VirementRecu incoming, String flow) {
        String uetr = incoming != null && incoming.getUetr() != null ? incoming.getUetr().trim() : "";
        if (uetr.isEmpty()) {
            log.warn("{}: UETR manquant -> message RECU stocké sans idempotence UETR (pas de sync id_adresse par UETR)", flow);
        }
        incoming.setUetr(uetr);
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

    private long resolveSopIdFromBusinessSource(String xml, String flow, String sourceFilePath) {
        Optional<Long> sopFromPath = extractSopIdFromPath(sourceFilePath);
        if (sopFromPath.isPresent()) {
            Long sopId = sopFromPath.get();
            return ensureSopExistsFromPath(sopId, sourceFilePath, flow);
        }
        Optional<String> sopFolderLabel = extractSopFolderLabelFromPath(sourceFilePath);
        if (sopFolderLabel.isPresent()) {
            String folderLabel = sopFolderLabel.get().trim();
            return sopRepository.findByLibelleSopNormalized(folderLabel)
                    .map(s -> s.getId())
                    .orElseGet(() -> {
                        Long newId = sopRepository.nextIdSop();
                        Sop sop = new Sop();
                        sop.setId(newId);
                        sop.setLibelleSop(folderLabel);
                        sopRepository.save(sop);
                        log.warn("{}: SOP auto-créé depuis nom de dossier: id={}, libelle='{}'", flow, newId, sop.getLibelleSop());
                        return newId;
                    });
        }
        String sopLibelle = extractFirstSopBusinessValue(xml);
        if (sopLibelle.isEmpty()) {
            if (flow != null && flow.toUpperCase().contains("OUT_SAA")) {
                return resolveSopFromOutSaaFallback(flow);
            }
            throw new IllegalArgumentException(flow + ": SOP introuvable dans le message.");
        }
        return sopRepository.findByLibelleSopNormalized(sopLibelle)
                .map(s -> s.getId())
                .orElseGet(() -> {
                    Long newId = sopRepository.nextIdSop();
                    Sop sop = new Sop();
                    sop.setId(newId);
                    sop.setLibelleSop(sopLibelle.trim());
                    sopRepository.save(sop);
                    log.warn("{}: SOP auto-créé depuis message: id={}, libelle='{}'", flow, newId, sop.getLibelleSop());
                    return newId;
                });
    }

    private long resolveSopFromOutSaaFallback(String flow) {
        final String fallbackLabel = "SAA";
        final long fallbackId = 0L;
        return sopRepository.findById(fallbackId)
                .map(Sop::getId)
                .orElseGet(() -> {
                    Sop sop = new Sop();
                    sop.setId(fallbackId);
                    sop.setLibelleSop(fallbackLabel);
                    sopRepository.save(sop);
                    log.warn("{}: SOP absent dans OUT_SAA -> SOP fallback auto-créé: id={}, libelle='{}'", flow, fallbackId, fallbackLabel);
                    return fallbackId;
                });
    }

    private Optional<Long> extractSopIdFromPath(String sourceFilePath) {
        if (sourceFilePath == null || sourceFilePath.trim().isEmpty()) {
            return Optional.empty();
        }
        java.util.regex.Matcher matcher = SOP_FOLDER_PATTERN.matcher(sourceFilePath);
        if (!matcher.find()) {
            return Optional.empty();
        }
        try {
            return Optional.of(Long.parseLong(matcher.group(1)));
        } catch (NumberFormatException ignored) {
            return Optional.empty();
        }
    }

    private Optional<String> extractSopFolderLabelFromPath(String sourceFilePath) {
        if (sourceFilePath == null || sourceFilePath.trim().isEmpty()) {
            return Optional.empty();
        }
        String normalized = sourceFilePath.replace('\\', '/');
        String[] segments = normalized.split("/");
        for (int i = 0; i < segments.length - 2; i++) {
            String current = segments[i];
            String child = segments[i + 1];
            if (current == null || child == null || current.trim().isEmpty()) {
                continue;
            }
            String upperChild = child.trim().toUpperCase();
            if ("IN_SOP".equals(upperChild) || "OUT_SOP".equals(upperChild)) {
                return Optional.of(current.trim().replace('_', ' '));
            }
        }
        return Optional.empty();
    }

    private long ensureSopExistsFromPath(Long sopId, String sourceFilePath, String flow) {
        if (sopRepository.findById(sopId).isPresent()) {
            return sopId;
        }
        Sop sop = new Sop();
        sop.setId(sopId);
        sop.setLibelleSop(extractSopLibelleFromPath(sourceFilePath, sopId));
        sopRepository.save(sop);
        log.warn("{}: SOP auto-créé depuis dossier: id={}, libelle='{}'", flow, sopId, sop.getLibelleSop());
        return sopId;
    }

    private String extractSopLibelleFromPath(String sourceFilePath, Long sopId) {
        if (sourceFilePath != null) {
            java.util.regex.Matcher matcher = SOP_FOLDER_PATTERN.matcher(sourceFilePath);
            if (matcher.find()) {
                String labelPart = matcher.group(2);
                if (labelPart != null && !labelPart.trim().isEmpty()) {
                    return labelPart.replace('_', ' ').trim();
                }
            }
        }
        return "SOP " + sopId;
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

    private void createHistoFromVirementRecu(VirementRecu v, long idVrtEmis) {
        Optional<VirementRecuHisto> existing = virementRecuHistoRepository
                .findTopByIdVrtRecuVirementRecuOrderByDateHistorisationDesc(v.getIdVrtRecu());
        if (existing.isPresent()) {
            VirementRecuHisto row = existing.get();
            row.setDateHistorisation(OffsetDateTime.now());
            virementRecuHistoRepository.save(row);
            return;
        }

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

    private MessageRecu mapToMessageRecu(VirementRecu v, String msgId, String sourceFilePath) {
        MessageRecu m = new MessageRecu();
        m.setIdVrtRecuVirementRecu(v.getIdVrtRecu());
        m.setIdStatutStatutVirementRecu(v.getIdStatutStatut());
        m.setIdAdresseAdresseVirementRecu(v.getIdAdresseAdresse());
        m.setIdSopVirementRecu(v.getIdSop());
        m.setCodeBicBicVirementRecu(v.getCodeBicBic());
        m.setCodeMsgTypeMessageVirementRecu(v.getCodeMsgTypeMessage());
        m.setReference(msgId != null ? truncate(msgId, 35) : v.getReference());
        m.setSop(v.getIdSop());
        m.setNom(sourceFilePath == null ? null : new java.io.File(sourceFilePath).getName());
        m.setPath(sourceFilePath);
        m.setVirementRecu(v);
        return m;
    }

    private void updateLatestMessageRecuFileMeta(Long idVrtRecu, String sourceFilePath) {
        if (idVrtRecu == null || sourceFilePath == null || sourceFilePath.trim().isEmpty()) {
            return;
        }
        messageRecuRepository.findTopByIdVrtRecuVirementRecuOrderByIdMsgRecuDesc(idVrtRecu).ifPresent(m -> {
            m.setNom(new java.io.File(sourceFilePath).getName());
            m.setPath(sourceFilePath);
            messageRecuRepository.save(m);
        });
    }
}
