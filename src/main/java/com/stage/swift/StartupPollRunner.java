package com.stage.swift;

import com.stage.swift.service.mx.impl.MxAckScheduler;
import com.stage.swift.service.mx.impl.OutScheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * Lance un premier passage de traitement des dossiers IN / IN_ACK et OUT (OUT_SAA / OUT_SOP)
 * environ 2 secondes après le démarrage, pour ne pas dépendre uniquement des schedulers.
 */
@Component
public class StartupPollRunner implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(StartupPollRunner.class);

    private final MxAckScheduler mxAckScheduler;
    private final OutScheduler outScheduler;

    public StartupPollRunner(MxAckScheduler mxAckScheduler, OutScheduler outScheduler) {
        this.mxAckScheduler = mxAckScheduler;
        this.outScheduler = outScheduler;
    }

    @Override
    public void run(ApplicationArguments args) {
        log.info("[StartupPollRunner] Premier passage dans 2 secondes...");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.warn("[StartupPollRunner] Interrompu");
            return;
        }
        log.info("[StartupPollRunner] Exécution du premier polling IN / IN_ACK");
        mxAckScheduler.pollInputDirectories();
        log.info("[StartupPollRunner] Exécution du premier polling OUT (OUT_SAA / OUT_SOP)");
        outScheduler.pollOutDirectories();
    }
}
