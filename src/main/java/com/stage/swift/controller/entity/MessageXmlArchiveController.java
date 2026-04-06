package com.stage.swift.controller.entity;

import com.stage.swift.service.mx.MessageXmlArchiveService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/messages-xml")
@CrossOrigin(origins = "*")
public class MessageXmlArchiveController {

    private final MessageXmlArchiveService messageXmlArchiveService;

    public MessageXmlArchiveController(MessageXmlArchiveService messageXmlArchiveService) {
        this.messageXmlArchiveService = messageXmlArchiveService;
    }

    @GetMapping(value = "/virement-emis/{idVrtEmis}", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> getByVirementEmis(@PathVariable Long idVrtEmis) {
        return messageXmlArchiveService.findLatestXmlByVirementEmisId(idVrtEmis)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/virement-recu/{idVrtRecu}", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> getByVirementRecu(@PathVariable Long idVrtRecu) {
        return messageXmlArchiveService.findLatestXmlByVirementRecuId(idVrtRecu)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
