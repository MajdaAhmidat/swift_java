package com.stage.swift.controller.entity;

import com.stage.swift.dto.entity.TypeMessageDTO;
import com.stage.swift.entity.TypeMessage;
import com.stage.swift.mapper.EntityMapper;
import com.stage.swift.service.entity.TypeMessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/** REST Controller pour TypeMessage (TYPE_MESSAGE has_many VIREMENT_EMIS, VIREMENT_RECU). */
@RestController
@RequestMapping("/api/type-messages")
@CrossOrigin(origins = "*")
public class TypeMessageController {

    private final TypeMessageService typeMessageService;
    private final EntityMapper mapper;

    public TypeMessageController(TypeMessageService typeMessageService, EntityMapper mapper) {
        this.typeMessageService = typeMessageService;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<TypeMessageDTO>> findAll() {
        List<TypeMessageDTO> list = typeMessageService.findAll().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TypeMessageDTO> findById(@PathVariable Long id) {
        return typeMessageService.findById(id)
                .map(mapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TypeMessageDTO> create(@Valid @RequestBody TypeMessageDTO dto) {
        TypeMessage entity = mapper.toEntity(dto);
        entity = typeMessageService.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDTO(entity));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TypeMessageDTO> update(@PathVariable Long id, @Valid @RequestBody TypeMessageDTO dto) {
        if (!typeMessageService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        dto.setCodeMsg(id);
        TypeMessage entity = mapper.toEntity(dto);
        entity = typeMessageService.save(entity);
        return ResponseEntity.ok(mapper.toDTO(entity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!typeMessageService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        typeMessageService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
