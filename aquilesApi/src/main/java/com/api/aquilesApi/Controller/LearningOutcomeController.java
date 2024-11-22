package com.api.aquilesApi.Controller;

import com.api.aquilesApi.Dto.LearningOutcomeDTO;
import com.api.aquilesApi.Entity.LearningOutcome;
import com.api.aquilesApi.Service.LearningOutcomeService;
import com.api.aquilesApi.Utilities.CustomException;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/learning-outcomes")
@Validated
public class LearningOutcomeController {

    @Autowired
    private LearningOutcomeService learningOutcomeService;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Obtener todos los Learning Outcomes paginados.
     */
    @GetMapping
    public ResponseEntity<Page<LearningOutcomeDTO>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<LearningOutcomeDTO> learningOutcomes = learningOutcomeService
                .findAll(pageRequest)
                .map(outcome -> modelMapper.map(outcome, LearningOutcomeDTO.class));
        return ResponseEntity.ok(learningOutcomes);
    }

    /**
     * Obtener un Learning Outcome por ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<LearningOutcomeDTO> getById(@PathVariable Long id) {
        LearningOutcome learningOutcome = learningOutcomeService.getById(id);
        LearningOutcomeDTO dto = modelMapper.map(learningOutcome, LearningOutcomeDTO.class);
        return ResponseEntity.ok(dto);
    }

    /**
     * Crear un nuevo Learning Outcome.
     */
    @PostMapping
    public ResponseEntity<LearningOutcomeDTO> create(@Valid @RequestBody LearningOutcomeDTO learningOutcomeDTO) {
        LearningOutcome entity = modelMapper.map(learningOutcomeDTO, LearningOutcome.class);
        learningOutcomeService.save(entity);
        LearningOutcomeDTO response = modelMapper.map(entity, LearningOutcomeDTO.class);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Actualizar un Learning Outcome.
     */
    @PutMapping("/{id}")
    public ResponseEntity<LearningOutcomeDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody LearningOutcomeDTO learningOutcomeDTO) {
        LearningOutcome existing = learningOutcomeService.getById(id);
        LearningOutcome updated = modelMapper.map(learningOutcomeDTO, LearningOutcome.class);
        updated.setId(existing.getId());
        learningOutcomeService.save(updated);
        LearningOutcomeDTO response = modelMapper.map(updated, LearningOutcomeDTO.class);
        return ResponseEntity.ok(response);
    }

    /**
     * Cambiar el estado de un Learning Outcome.
     */
    @PatchMapping("/{id}/toggle")
    public ResponseEntity<Boolean> toggleState(@PathVariable Long id) {
        Boolean newState = learningOutcomeService.toggle(id);
        return ResponseEntity.ok(newState);
    }

    /**
     * Eliminar un Learning Outcome.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        LearningOutcome entity = learningOutcomeService.getById(id);
        learningOutcomeService.delete(entity);
        return ResponseEntity.noContent().build();
    }

    /**
     * Crear múltiples Learning Outcomes.
     */
    @PostMapping("/batch")
    public ResponseEntity<Void> createBatch(@Valid @RequestBody List<LearningOutcomeDTO> learningOutcomes) {
        List<LearningOutcome> entities = learningOutcomes.stream()
                .map(dto -> modelMapper.map(dto, LearningOutcome.class))
                .collect(Collectors.toList());
        learningOutcomeService.saveAll(entities);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
