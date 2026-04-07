package com.stage.swift.controller.entity;

import com.stage.swift.dto.entity.MessageEmisDTO;
import com.stage.swift.entity.MessageEmis;
import com.stage.swift.mapper.EntityMapper;
import com.stage.swift.service.entity.MessageEmisService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/messages-emis")
@CrossOrigin(origins = "*")
public class MessageEmisController {

    private final MessageEmisService messageEmisService;
    private final EntityMapper mapper;

    public MessageEmisController(MessageEmisService messageEmisService, EntityMapper mapper) {
        this.messageEmisService = messageEmisService;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<MessageEmisDTO>> findAll() {
        List<MessageEmisDTO> list = messageEmisService.findAll().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @GetMapping("/by-virement/{idVrtEmis}")
    public ResponseEntity<List<MessageEmisDTO>> listByVirement(@PathVariable Long idVrtEmis) {
        List<MessageEmisDTO> list = messageEmisService.findByVirementEmisId(idVrtEmis).stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @GetMapping(value = "/xml", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> getXmlByPk(
            @RequestParam Long idMsgEmis,
            @RequestParam Long idVrtEmisVirementEmis,
            @RequestParam Long idSopVirementEmis,
            @RequestParam Long idStatutStatutVirementEmis,
            @RequestParam Long idAdresseAdresseVirementEmis,
            @RequestParam Long codeBicBicVirementEmis,
            @RequestParam Long codeMsgTypeMessageVirementEmis) {
        MessageEmis.MessageEmisPK pk = buildMessageEmisPK(idMsgEmis, idVrtEmisVirementEmis, idSopVirementEmis,
                idStatutStatutVirementEmis, idAdresseAdresseVirementEmis, codeBicBicVirementEmis, codeMsgTypeMessageVirementEmis);
        return messageEmisService.generateXmlByMessageEmisPk(pk)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/by-id")
    public ResponseEntity<MessageEmisDTO> findById(
            @RequestParam Long idMsgEmis,
            @RequestParam Long idVrtEmisVirementEmis,
            @RequestParam Long idSopVirementEmis,
            @RequestParam Long idStatutStatutVirementEmis,
            @RequestParam Long idAdresseAdresseVirementEmis,
            @RequestParam Long codeBicBicVirementEmis,
            @RequestParam Long codeMsgTypeMessageVirementEmis) {
        MessageEmis.MessageEmisPK pk = buildMessageEmisPK(idMsgEmis, idVrtEmisVirementEmis, idSopVirementEmis,
                idStatutStatutVirementEmis, idAdresseAdresseVirementEmis, codeBicBicVirementEmis, codeMsgTypeMessageVirementEmis);
        return messageEmisService.findById(pk)
                .map(mapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/virement/{idVrtEmis}/xml", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> getXmlByVirementEmis(@PathVariable Long idVrtEmis) {
        return messageEmisService.generateXmlByVirementEmisId(idVrtEmis)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<MessageEmisDTO> create(@Valid @RequestBody MessageEmisDTO dto) {
        MessageEmis entity = mapper.toEntity(dto);
        entity = messageEmisService.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDTO(entity));
    }

    @PutMapping
    public ResponseEntity<MessageEmisDTO> update(@Valid @RequestBody MessageEmisDTO dto) {
        MessageEmis.MessageEmisPK pk = buildMessageEmisPK(dto.getIdMsgEmis(), dto.getIdVrtEmisVirementEmis(),
                dto.getIdSopVirementEmis(), dto.getIdStatutStatutVirementEmis(), dto.getIdAdresseAdresseVirementEmis(),
                dto.getCodeBicBicVirementEmis(), dto.getCodeMsgTypeMessageVirementEmis());
        if (!messageEmisService.findById(pk).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        MessageEmis entity = mapper.toEntity(dto);
        entity = messageEmisService.save(entity);
        return ResponseEntity.ok(mapper.toDTO(entity));
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(
            @RequestParam Long idMsgEmis,
            @RequestParam Long idVrtEmisVirementEmis,
            @RequestParam Long idSopVirementEmis,
            @RequestParam Long idStatutStatutVirementEmis,
            @RequestParam Long idAdresseAdresseVirementEmis,
            @RequestParam Long codeBicBicVirementEmis,
            @RequestParam Long codeMsgTypeMessageVirementEmis) {
        MessageEmis.MessageEmisPK pk = buildMessageEmisPK(idMsgEmis, idVrtEmisVirementEmis, idSopVirementEmis,
                idStatutStatutVirementEmis, idAdresseAdresseVirementEmis, codeBicBicVirementEmis, codeMsgTypeMessageVirementEmis);
        if (!messageEmisService.findById(pk).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        messageEmisService.deleteById(pk);
        return ResponseEntity.noContent().build();
    }

    private static MessageEmis.MessageEmisPK buildMessageEmisPK(Long idMsgEmis, Long idVrtEmisVirementEmis,
            Long idSopVirementEmis, Long idStatutStatutVirementEmis, Long idAdresseAdresseVirementEmis,
            Long codeBicBicVirementEmis, Long codeMsgTypeMessageVirementEmis) {
        MessageEmis.MessageEmisPK pk = new MessageEmis.MessageEmisPK();
        pk.setIdMsgEmis(idMsgEmis);
        pk.setIdVrtEmisVirementEmis(idVrtEmisVirementEmis);
        pk.setIdSopVirementEmis(idSopVirementEmis);
        pk.setIdStatutStatutVirementEmis(idStatutStatutVirementEmis);
        pk.setIdAdresseAdresseVirementEmis(idAdresseAdresseVirementEmis);
        pk.setCodeBicBicVirementEmis(codeBicBicVirementEmis);
        pk.setCodeMsgTypeMessageVirementEmis(codeMsgTypeMessageVirementEmis);
        return pk;
    }
}
