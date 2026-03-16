package com.stage.swift.mappers.mx;

import com.prowidesoftware.swift.model.mx.MxPacs00800108;
import com.prowidesoftware.swift.model.mx.dic.CreditTransferTransaction39;
import com.prowidesoftware.swift.model.mx.dic.FIToFICustomerCreditTransferV08;
import com.stage.swift.entity.VirementEmis;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static com.stage.swift.helpers.MxMapperHelper.truncate;
import static com.stage.swift.helpers.MxMapperHelper.parseLocalDate;

/**
 * Mapper PACS 008 → VirementEmis à partir des chemins documentés :
 * MsgId, Cdtr/Nm, CdtrAcct/Id/Othr/Id, DbtrAcct/Id/Othr/Id, Dbtr/Nm,
 * IntrBkSttlmAmt, IntrBkSttlmDt, RmtInf (Document/FIToFICstmrCdtTrf/...).
 */
@Component
public class PACS008ToVirementEmisMapper {

    /**
     * Construit un VirementEmis à partir du XML PACS 008 (getters = XPaths fournis).
     */
    public VirementEmis toVirementEmis(MxPacs00800108 mx, Long nextIdVrtEmis,
                                      long defaultIdStatut, long defaultIdAdresse, long defaultIdSop, long defaultCodeBic,
                                      long codeMsgTypeMessage) {
        FIToFICustomerCreditTransferV08 doc = mx.getFIToFICstmrCdtTrf();
        if (doc == null || doc.getCdtTrfTxInf() == null || doc.getCdtTrfTxInf().isEmpty()) {
            throw new IllegalArgumentException(
                    "Aucun CdtTrfTxInf (détail de virement) dans le message PACS 008. "
                            + "Vérifiez que le XML contient bien FIToFICstmrCdtTrf/CdtTrfTxInf (instruction de virement complète), et non une simple référence (ex. dans un camt.054).");
        }
        CreditTransferTransaction39 tx = doc.getCdtTrfTxInf().get(0);

        VirementEmis v = new VirementEmis();
        v.setIdVrtEmis(nextIdVrtEmis);
        v.setIdSop(defaultIdSop);
        v.setIdStatutStatut(defaultIdStatut);
        v.setIdAdresseAdresse(defaultIdAdresse);
        v.setCodeBicBic(defaultCodeBic);
        v.setCodeMsgTypeMessage(codeMsgTypeMessage);
        v.setIdStatut(defaultIdStatut);
        v.setIdAdresse(defaultIdAdresse);
        v.setIdTypeMessage(codeMsgTypeMessage);

        // MsgId → reference  (/Document/FIToFICstmrCdtTrf/GrpHdr/MsgId)
        String msgId = doc.getGrpHdr() != null ? doc.getGrpHdr().getMsgId() : null;
        v.setReference(truncate(msgId != null ? msgId : " ", 35));

        // UETR / EndToEnd
        if (tx.getPmtId() != null) {
            v.setUetr(truncate(tx.getPmtId().getUETR(), 254));
            String endToEnd = tx.getPmtId().getEndToEndId();
            v.setEndToEnd(endToEnd == null || endToEnd.isEmpty() ? "NOTPROVIDED" : truncate(endToEnd, 35));
        } else {
            v.setUetr("");
            v.setEndToEnd("NOTPROVIDED");
        }

        // Amount → montant, codeDevise  (/Document/.../CdtTrfTxInf/IntrBkSttlmAmt)
        if (tx.getIntrBkSttlmAmt() != null) {
            v.setMontant(tx.getIntrBkSttlmAmt().getValue() != null ? tx.getIntrBkSttlmAmt().getValue() : BigDecimal.ZERO);
            v.setCodeDevise((tx.getIntrBkSttlmAmt().getCcy() != null && !tx.getIntrBkSttlmAmt().getCcy().isEmpty()) ? truncate(tx.getIntrBkSttlmAmt().getCcy(), 3) : "MAD");
        } else {
            v.setMontant(BigDecimal.ZERO);
            v.setCodeDevise("MAD");
        }

        // Date → dateValeur  (/Document/.../CdtTrfTxInf/IntrBkSttlmDt)
        v.setDateValeur(parseLocalDate(tx.getIntrBkSttlmDt()));
        v.setDateIntegration(LocalDate.now());

        // Cdtr Name → denominationBnf  (/Document/.../CdtTrfTxInf/Cdtr/Nm)
        String cdtrNm = tx.getCdtr() != null ? tx.getCdtr().getNm() : null;
        v.setDenominationBnf(truncate(cdtrNm != null && !cdtrNm.isEmpty() ? cdtrNm : " ", 255));

        // Dbtr Name → denominationOrd  (/Document/.../CdtTrfTxInf/Dbtr/Nm)
        String dbtrNm = tx.getDbtr() != null ? tx.getDbtr().getNm() : null;
        v.setDenominationOrd(truncate(dbtrNm != null && !dbtrNm.isEmpty() ? dbtrNm : " ", 255));

        // CdtrAcct/Id/Othr/Id → numCompteBnf
        String numCompteBnf = null;
        if (tx.getCdtrAcct() != null && tx.getCdtrAcct().getId() != null && tx.getCdtrAcct().getId().getOthr() != null) {
            numCompteBnf = tx.getCdtrAcct().getId().getOthr().getId();
        }
        v.setNumCompteBnf(truncate(numCompteBnf != null ? numCompteBnf : " ", 255));

        // DbtrAcct/Id/Othr/Id → numCompteOrd
        String numCompteOrd = null;
        if (tx.getDbtrAcct() != null && tx.getDbtrAcct().getId() != null && tx.getDbtrAcct().getId().getOthr() != null) {
            numCompteOrd = tx.getDbtrAcct().getId().getOthr().getId();
        }
        v.setNumCompteOrd(truncate(numCompteOrd != null ? numCompteOrd : " ", 255));

        // RmtInf → renseignement  (/Document/.../CdtTrfTxInf/RmtInf)
        StringBuilder renseignement = new StringBuilder();
        if (tx.getRmtInf() != null && tx.getRmtInf().getUstrd() != null) {
            for (String s : tx.getRmtInf().getUstrd()) {
                if (renseignement.length() > 0) renseignement.append(" ");
                renseignement.append(s != null ? s : "");
            }
        }
        v.setRenseignement(truncate(renseignement.length() > 0 ? renseignement.toString() : " ", 255));

        // BIC ordonnateur / bénéficiaire (agents)
        String bicOrd = null;
        if (tx.getInstgAgt() != null && tx.getInstgAgt().getFinInstnId() != null) {
            bicOrd = tx.getInstgAgt().getFinInstnId().getBICFI();
        }
        String bicBnf = null;
        if (tx.getInstdAgt() != null && tx.getInstdAgt().getFinInstnId() != null) {
            bicBnf = tx.getInstdAgt().getFinInstnId().getBICFI();
        }
        v.setBicOrdonnateur(truncate(bicOrd != null ? bicOrd : " ", 255));
        v.setBicBeneficiaire(truncate(bicBnf != null ? bicBnf : " ", 255));

        return v;
    }
}
