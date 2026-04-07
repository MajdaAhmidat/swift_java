package com.stage.swift.controller.entity;

import com.stage.swift.dto.entity.MessageRecuDTO;
import com.stage.swift.entity.MessageRecu;
import com.stage.swift.mapper.EntityMapper;
import com.stage.swift.service.entity.MessageRecuService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/messages-recu")
@CrossOrigin(origins = "*")
public class MessageRecuController {

    private final MessageRecuService messageRecuService;
    private final EntityMapper mapper;

    public MessageRecuController(MessageRecuService messageRecuService, EntityMapper mapper) {
        this.messageRecuService = messageRecuService;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<MessageRecuDTO>> findAll() {
        List<MessageRecuDTO> list = messageRecuService.findAll().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @GetMapping("/by-virement/{idVrtRecu}")
    public ResponseEntity<List<MessageRecuDTO>> listByVirement(@PathVariable Long idVrtRecu) {
        List<MessageRecuDTO> list = messageRecuService.findByVirementRecuId(idVrtRecu).stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @GetMapping(value = "/xml", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> getXmlByPk(
            @RequestParam Long idMsgRecu,
            @RequestParam Long idVrtRecuVirementRecu,
            @RequestParam Long idStatutStatutVirementRecu,
            @RequestParam Long idAdresseAdresseVirementRecu,
            @RequestParam Long idSopVirementRecu,
            @RequestParam Long codeBicBicVirementRecu,
            @RequestParam Long codeMsgTypeMessageVirementRecu) {
        MessageRecu.MessageRecuPK pk = new MessageRecu.MessageRecuPK();
        pk.setIdMsgRecu(idMsgRecu);
        pk.setIdVrtRecuVirementRecu(idVrtRecuVirementRecu);
        pk.setIdStatutStatutVirementRecu(idStatutStatutVirementRecu);
        pk.setIdAdresseAdresseVirementRecu(idAdresseAdresseVirementRecu);
        pk.setIdSopVirementRecu(idSopVirementRecu);
        pk.setCodeBicBicVirementRecu(codeBicBicVirementRecu);
        pk.setCodeMsgTypeMessageVirementRecu(codeMsgTypeMessageVirementRecu);
        return messageRecuService.generateXmlByMessageRecuPk(pk)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/by-id")
    public ResponseEntity<MessageRecuDTO> findById(
            @RequestParam Long idMsgRecu,
            @RequestParam Long idVrtRecuVirementRecu,
            @RequestParam Long idStatutStatutVirementRecu,
            @RequestParam Long idAdresseAdresseVirementRecu,
            @RequestParam Long idSopVirementRecu,
            @RequestParam Long codeBicBicVirementRecu,
            @RequestParam Long codeMsgTypeMessageVirementRecu) {
        MessageRecu.MessageRecuPK pk = new MessageRecu.MessageRecuPK();
        pk.setIdMsgRecu(idMsgRecu);
        pk.setIdVrtRecuVirementRecu(idVrtRecuVirementRecu);
        pk.setIdStatutStatutVirementRecu(idStatutStatutVirementRecu);
        pk.setIdAdresseAdresseVirementRecu(idAdresseAdresseVirementRecu);
        pk.setIdSopVirementRecu(idSopVirementRecu);
        pk.setCodeBicBicVirementRecu(codeBicBicVirementRecu);
        pk.setCodeMsgTypeMessageVirementRecu(codeMsgTypeMessageVirementRecu);
        return messageRecuService.findById(pk)
                .map(mapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/virement/{idVrtRecu}/xml", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> getXmlByVirementRecu(@PathVariable Long idVrtRecu) {
        return messageRecuService.generateXmlByVirementRecuId(idVrtRecu)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<MessageRecuDTO> create(@Valid @RequestBody MessageRecuDTO dto) {
        MessageRecu entity = mapper.toEntity(dto);
        entity = messageRecuService.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDTO(entity));
    }

    @PutMapping
    public ResponseEntity<MessageRecuDTO> update(@Valid @RequestBody MessageRecuDTO dto) {
        MessageRecu.MessageRecuPK pk = new MessageRecu.MessageRecuPK();
        pk.setIdMsgRecu(dto.getIdMsgRecu());
        pk.setIdVrtRecuVirementRecu(dto.getIdVrtRecuVirementRecu());
        pk.setIdStatutStatutVirementRecu(dto.getIdStatutStatutVirementRecu());
        pk.setIdAdresseAdresseVirementRecu(dto.getIdAdresseAdresseVirementRecu());
        pk.setIdSopVirementRecu(dto.getIdSopVirementRecu());
        pk.setCodeBicBicVirementRecu(dto.getCodeBicBicVirementRecu());
        pk.setCodeMsgTypeMessageVirementRecu(dto.getCodeMsgTypeMessageVirementRecu());
        if (!messageRecuService.findById(pk).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        MessageRecu entity = mapper.toEntity(dto);
        entity = messageRecuService.save(entity);
        return ResponseEntity.ok(mapper.toDTO(entity));
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(
            @RequestParam Long idMsgRecu,
            @RequestParam Long idVrtRecuVirementRecu,
            @RequestParam Long idStatutStatutVirementRecu,
            @RequestParam Long idAdresseAdresseVirementRecu,
            @RequestParam Long idSopVirementRecu,
            @RequestParam Long codeBicBicVirementRecu,
            @RequestParam Long codeMsgTypeMessageVirementRecu) {
        MessageRecu.MessageRecuPK pk = new MessageRecu.MessageRecuPK();
        pk.setIdMsgRecu(idMsgRecu);
        pk.setIdVrtRecuVirementRecu(idVrtRecuVirementRecu);
        pk.setIdStatutStatutVirementRecu(idStatutStatutVirementRecu);
        pk.setIdAdresseAdresseVirementRecu(idAdresseAdresseVirementRecu);
        pk.setIdSopVirementRecu(idSopVirementRecu);
        pk.setCodeBicBicVirementRecu(codeBicBicVirementRecu);
        pk.setCodeMsgTypeMessageVirementRecu(codeMsgTypeMessageVirementRecu);
        if (!messageRecuService.findById(pk).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        messageRecuService.deleteById(pk);
        return ResponseEntity.noContent().build();
    }
}
