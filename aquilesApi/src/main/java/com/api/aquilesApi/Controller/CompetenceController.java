package com.api.aquilesApi.Controller;

import com.api.aquilesApi.Dto.CompetenceDTO;
import com.api.aquilesApi.Entity.Competence;
import com.api.aquilesApi.Service.CompetenceService;
import com.api.aquilesApi.Utilities.CustomException;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/competences")
public class CompetenceController {

    @Autowired
    private CompetenceService competenceService;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Obtener todas las competencias con paginación.
     */
    @GetMapping
    public ResponseEntity<Page<CompetenceDTO>> getAllCompetences(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<Competence> competences = competenceService.findAll(PageRequest.of(page, size));
        Page<CompetenceDTO> competenceDtos = competences.map(this::convertToDto);
        return ResponseEntity.ok(competenceDtos);
    }

    /**
     * Obtener una competencia por su ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CompetenceDTO> getCompetenceById(@PathVariable Long id) {
        Competence competence = competenceService.getById(id);
        return ResponseEntity.ok(convertToDto(competence));
    }

    /**
     * Crear una nueva competencia.
     */
    @PostMapping
    public ResponseEntity<CompetenceDTO> createCompetence(@Valid @RequestBody CompetenceDTO competenceDto) {
        if (competenceService.existsCode(competenceDto.getCode())) {
            throw new CustomException("El código ya existe", HttpStatus.BAD_REQUEST);
        }
        Competence competence = convertToEntity(competenceDto);
        competenceService.save(competence);
        return new ResponseEntity<>(convertToDto(competence), HttpStatus.CREATED);
    }

    /**
     * Actualizar una competencia existente.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CompetenceDTO> updateCompetence(
            @PathVariable Long id, @Valid @RequestBody CompetenceDTO competenceDto) {
        Competence competence = competenceService.getById(id);
        competence.setCode(competenceDto.getCode());
        competence.setName(competenceDto.getName());
        competence.setDescription(competenceDto.getDescription());
        competence.setState(competenceDto.getState());
        competenceService.save(competence);
        return ResponseEntity.ok(convertToDto(competence));
    }

    /**
     * Eliminar una competencia.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompetence(@PathVariable Long id) {
        Competence competence = competenceService.getById(id);
        competenceService.delete(competence);
        return ResponseEntity.noContent().build();
    }

    /**
     * Convertir una entidad Competence a CompetenceDto.
     */
    private CompetenceDTO convertToDto(Competence competence) {
        return modelMapper.map(competence, CompetenceDTO.class);
    }

    /**
     * Convertir un CompetenceDto a una entidad Competence.
     */
    private Competence convertToEntity(CompetenceDTO competenceDto) {
        return modelMapper.map(competenceDto, Competence.class);
    }
}
