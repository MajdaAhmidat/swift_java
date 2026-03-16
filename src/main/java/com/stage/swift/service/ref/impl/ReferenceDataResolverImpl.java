package com.stage.swift.service.ref.impl;

import com.stage.swift.entity.Bic;
import com.stage.swift.entity.Statut;
import com.stage.swift.repository.BicRepository;
import com.stage.swift.repository.StatutRepository;
import com.stage.swift.service.ref.ReferenceDataResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Résout SOP et Statut depuis la base (BIC → id_sop, code_statut → id_statut).
 * Fallback sur 1L si non trouvé.
 */
@Service
public class ReferenceDataResolverImpl implements ReferenceDataResolver {

    private static final Logger log = LoggerFactory.getLogger(ReferenceDataResolverImpl.class);
    private static final long FALLBACK_SOP_ID = 1L;
    private static final long FALLBACK_STATUT_ID = 1L;

    private final BicRepository bicRepository;
    private final StatutRepository statutRepository;

    public ReferenceDataResolverImpl(BicRepository bicRepository, StatutRepository statutRepository) {
        this.bicRepository = bicRepository;
        this.statutRepository = statutRepository;
    }

    @Override
    public long resolveSopIdFromBic(String bicCode) {
        if (bicCode == null || bicCode.trim().isEmpty()) {
            return FALLBACK_SOP_ID;
        }
        String code = bicCode.trim();
        Optional<Bic> bic = bicRepository.findFirstByBicOrdonnateurOrBicBeneficiare(code, code);
        if (bic.isPresent() && bic.get().getIdSop() != null) {
            log.debug("SOP résolu depuis BIC {} : id_sop={}", code, bic.get().getIdSop());
            return bic.get().getIdSop();
        }
        return FALLBACK_SOP_ID;
    }

    @Override
    public long resolveStatutIdByCode(String codeStatut) {
        if (codeStatut == null || codeStatut.trim().isEmpty()) {
            return FALLBACK_STATUT_ID;
        }
        Optional<Statut> statut = statutRepository.findByCodeStatut(codeStatut.trim());
        if (statut.isPresent()) {
            log.debug("Statut résolu depuis code {} : id_statut={}", codeStatut, statut.get().getIdStatut());
            return statut.get().getIdStatut();
        }
        log.warn("Code statut '{}' introuvable en base, fallback id=1", codeStatut);
        return FALLBACK_STATUT_ID;
    }

    @Override
    public long resolveCodeBicFromBic(String bicCode) {
        if (bicCode == null || bicCode.trim().isEmpty()) {
            return 1L;
        }
        String code = bicCode.trim();
        Optional<Bic> bic = bicRepository.findFirstByBicOrdonnateurOrBicBeneficiare(code, code);
        if (bic.isPresent()) {
            return bic.get().getCodeBic();
        }
        // Si le BIC n'existe pas encore, on le crée en base et on retourne son code_bic.
        Long nextCode = bicRepository.nextCodeBic();
        Bic newBic = new Bic();
        newBic.setCodeBic(nextCode);
        newBic.setBicOrdonnateur(code);
        newBic.setBicBeneficiare(code);
        // On utilise le BIC comme libellé par défaut
        newBic.setLibelleBic(code);
        // Code banque et libellé banque peuvent être affinés plus tard
        newBic.setCodeBq("0000");
        newBic.setLibelleBq(code);
        // Si aucun SOP spécifique n'est connu pour ce BIC, on met le fallback
        newBic.setIdSop(FALLBACK_SOP_ID);
        bicRepository.save(newBic);
        log.info("BIC créé en base pour code={}, code_bic={}", code, nextCode);
        return nextCode;
    }
}
