package com.stage.swift.mappers.mx;

import com.prowidesoftware.swift.model.mx.MxPacs00900108;
import com.prowidesoftware.swift.model.mx.dic.CreditTransferTransaction36;
import com.prowidesoftware.swift.model.mx.dic.FinancialInstitutionCreditTransferV08;
import com.prowidesoftware.swift.model.mx.dic.InstructionForNextAgent1;
import com.stage.swift.entity.VirementEmis;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.stage.swift.helpers.MxMapperHelper.truncate;
import static com.stage.swift.helpers.MxMapperHelper.parseLocalDate;

/**
 * Mapper PACS 009 → VirementEmis (tout en virement_emis / message_emis).
 */
@Component
public class PACS009ToVirementEmisMapper {

    public VirementEmis toVirementEmis(MxPacs00900108 mx, Long nextIdVrtEmis,
                                       long defaultIdStatut, long defaultIdAdresse, long defaultIdSop, long defaultCodeBic,
                                       long codeMsgTypeMessage) {
        FinancialInstitutionCreditTransferV08 doc = mx.getFICdtTrf();
        if (doc == null || doc.getCdtTrfTxInf() == null || doc.getCdtTrfTxInf().isEmpty()) {
            throw new IllegalArgumentException("Aucun CdtTrfTxInf dans le message Pacs 009");
        }
        CreditTransferTransaction36 tx = doc.getCdtTrfTxInf().get(0);

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

        String msgId = doc.getGrpHdr() != null ? doc.getGrpHdr().getMsgId() : null;
        v.setReference(truncate(msgId != null ? msgId : " ", 35));

        if (tx.getPmtId() != null) {
            v.setUetr(truncate(tx.getPmtId().getUETR(), 254));
            String endToEnd = tx.getPmtId().getEndToEndId();
            v.setEndToEnd(endToEnd == null || endToEnd.isEmpty() ? "NOTPROVIDED" : truncate(endToEnd, 35));
        } else {
            v.setUetr("");
            v.setEndToEnd("NOTPROVIDED");
        }

        if (tx.getIntrBkSttlmAmt() != null) {
            v.setMontant(tx.getIntrBkSttlmAmt().getValue() != null ? tx.getIntrBkSttlmAmt().getValue() : BigDecimal.ZERO);
            v.setCodeDevise((tx.getIntrBkSttlmAmt().getCcy() != null && !tx.getIntrBkSttlmAmt().getCcy().isEmpty()) ? truncate(tx.getIntrBkSttlmAmt().getCcy(), 3) : "MAD");
        } else {
            v.setMontant(BigDecimal.ZERO);
            v.setCodeDevise("MAD");
        }

        v.setDateValeur(parseLocalDate(tx.getIntrBkSttlmDt()));
        v.setDateIntegration(LocalDate.now());

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

        // PACS 009 : Dbtr/Cdtr sans Nm → on utilise BIC comme dénomination
        v.setDenominationOrd(truncate(bicOrd != null && !bicOrd.isEmpty() ? bicOrd : " ", 255));
        v.setDenominationBnf(truncate(bicBnf != null && !bicBnf.isEmpty() ? bicBnf : " ", 255));

        String numCompteOrd = null;
        if (tx.getDbtrAcct() != null && tx.getDbtrAcct().getId() != null && tx.getDbtrAcct().getId().getOthr() != null) {
            numCompteOrd = tx.getDbtrAcct().getId().getOthr().getId();
        }
        String numCompteBnf = null;
        if (tx.getCdtrAcct() != null && tx.getCdtrAcct().getId() != null && tx.getCdtrAcct().getId().getOthr() != null) {
            numCompteBnf = tx.getCdtrAcct().getId().getOthr().getId();
        }
        v.setNumCompteOrd(truncate(numCompteOrd != null ? numCompteOrd : " ", 255));
        v.setNumCompteBnf(truncate(numCompteBnf != null ? numCompteBnf : " ", 255));

        StringBuilder renseignement = new StringBuilder();
        if (tx.getRmtInf() != null && tx.getRmtInf().getUstrd() != null) {
            for (String s : tx.getRmtInf().getUstrd()) {
                if (renseignement.length() > 0) renseignement.append(" ");
                renseignement.append(s != null ? s : "");
            }
        }
        if (tx.getInstrForNxtAgt() != null && !tx.getInstrForNxtAgt().isEmpty()) {
            for (InstructionForNextAgent1 instr : tx.getInstrForNxtAgt()) {
                if (instr != null && instr.getInstrInf() != null && !instr.getInstrInf().isEmpty()) {
                    if (renseignement.length() > 0) renseignement.append(" | ");
                    renseignement.append(instr.getInstrInf());
                }
            }
        }
        v.setRenseignement(truncate(renseignement.length() > 0 ? renseignement.toString() : " ", 255));

        return v;
    }
}
