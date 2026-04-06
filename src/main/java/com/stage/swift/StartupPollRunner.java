package com.stage.swift;

import com.stage.swift.service.mx.impl.MxAckScheduler;
import com.stage.swift.service.mx.impl.OutScheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

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
        // Le polling OUT (OUT_SAA / OUT_SOP) est géré uniquement par OutScheduler via @Scheduled
        // pour éviter les traitements concurrents sur les mêmes fichiers et les doublons en base.
    }
}
