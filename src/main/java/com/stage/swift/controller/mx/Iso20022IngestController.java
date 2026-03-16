package com.stage.swift.controller.mx;

import com.prowidesoftware.swift.model.MessageStandardType;
import com.stage.swift.entity.VirementRecu;
import com.stage.swift.exception.MissingReferenceDataException;
import com.stage.swift.service.mx.DocumentProcessorService;
import com.stage.swift.SwiftApplication;
import com.stage.swift.service.mx.Iso20022MessageService;
import com.stage.swift.service.mx.AckService;
import com.stage.swift.utils.PropertiesLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Endpoint pour l'ingestion de messages ISO 20022 (Pacs 008, Pacs 009) et des ACK SAA.
 * - POST /ingest       : un XML en corps (SOP)
 * - POST /ingest-ack   : un ACK SAA en corps
 * - POST /process-documents :
 *      - traite les dossiers input.mt.directory (MT éventuels)
 *      - traite les dossiers input.mx.directory (SOP .xml)
 *      - traite les dossiers input.ack.directory (SAA .ack)
 */
@RestController
@RequestMapping("/api/mx")
public class Iso20022IngestController {

    private final Iso20022MessageService iso20022MessageService;
    private final DocumentProcessorService documentProcessorService;
    private final AckService ackService;

    public Iso20022IngestController(Iso20022MessageService iso20022MessageService,
                                   DocumentProcessorService documentProcessorService,
                                   AckService ackService) {
        this.iso20022MessageService = iso20022MessageService;
        this.documentProcessorService = documentProcessorService;
        this.ackService = ackService;
    }

    @PostMapping(value = "/ingest", consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.TEXT_XML_VALUE, "application/xml", "text/xml"})
    public ResponseEntity<VirementRecu> ingest(@RequestBody String xml) {
        VirementRecu saved = iso20022MessageService.parseAndSave(xml);
        return ResponseEntity.ok(saved);
    }

    /** Ingestion d'un message .ack (SAA) : met à jour statut_swift sur le virement concerné (ACK, NACK...). */
    @PostMapping(value = "/ingest-ack", consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.TEXT_XML_VALUE, "application/xml", "text/xml", MediaType.TEXT_PLAIN_VALUE})
    public ResponseEntity<String> ingestAck(@RequestBody String ackContent) {
        ackService.processAckFile(ackContent);
        return ResponseEntity.ok("ACK enregistré");
    }

    /**
     * Lance le traitement des dossiers configurés (input.mt.directory, input.mx.directory), comme le batch.
     * Une seule application : on lance SwiftApplication, puis on appelle cet endpoint pour traiter les fichiers.
     */
    @PostMapping("/process-documents")
    public ResponseEntity<Map<String, String>> processDocuments() {
        List<String> mtDirs = PropertiesLoader.getDirectories("input.mt.directory");
        if (mtDirs != null && !mtDirs.isEmpty()) {
            documentProcessorService.processDocuments(mtDirs, MessageStandardType.MT);
        }
        // Dossiers pour les SOP (.xml)
        List<String> mxDirs = PropertiesLoader.getDirectories("input.mx.directory");
        if (mxDirs == null || mxDirs.isEmpty()) {
            String pathIn = PropertiesLoader.getProperty("swift.app.path.in");
            mxDirs = (pathIn != null && !pathIn.trim().isEmpty())
                    ? Collections.singletonList(pathIn.trim())
                    : Collections.singletonList("IN");
        }
        documentProcessorService.processDocuments(mxDirs, MessageStandardType.MX);

        // Dossiers pour les SAA (.ack)
        List<String> ackDirs = PropertiesLoader.getDirectories("input.ack.directory");
        if (ackDirs != null && !ackDirs.isEmpty()) {
            documentProcessorService.processDocuments(ackDirs, MessageStandardType.MX);
        }
        Map<String, String> result = new HashMap<>();
        result.put("status", "ok");
        result.put("message", "Traitement MT/MX terminé");
        return ResponseEntity.ok(result);
    }

    @ExceptionHandler(MissingReferenceDataException.class)
    public ResponseEntity<String> handleMissingReferenceData(MissingReferenceDataException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
