package com.api.aquilesApi.Controller;

import com.api.aquilesApi.Dto.TrainingProjectDTO;
import com.api.aquilesApi.Service.TrainingProjectService;
import com.api.aquilesApi.Entity.TrainingProject;
import com.api.aquilesApi.Utilities.CustomException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/training-projects")
public class TrainingProjectController {

    @Autowired
    private TrainingProjectService trainingProjectService;

    private final ModelMapper modelMapper = new ModelMapper();

    // Obtener todos los proyectos de formación
    @GetMapping
    public ResponseEntity<List<TrainingProjectDTO>> getAllTrainingProjects() {
        try {
            List<TrainingProject> trainingProjects = trainingProjectService.getAll();
            List<TrainingProjectDTO> trainingProjectDTOs = trainingProjects.stream()
                    .map(project -> modelMapper.map(project, TrainingProjectDTO.class))
                    .toList();
            return new ResponseEntity<>(trainingProjectDTOs, HttpStatus.OK);
        } catch (Exception e) {
            throw new CustomException("Error obteniendo proyectos de formación", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Obtener un proyecto de formación por ID
    @GetMapping("/{id}")
    public ResponseEntity<TrainingProjectDTO> getTrainingProjectById(@PathVariable Long id) {
        try {
            Optional<TrainingProject> trainingProjectOpt = trainingProjectService.getById(id);
            if (trainingProjectOpt.isPresent()) {
                TrainingProjectDTO trainingProjectDTO = modelMapper.map(trainingProjectOpt.get(), TrainingProjectDTO.class);
                return new ResponseEntity<>(trainingProjectDTO, HttpStatus.OK);
            } else {
                throw new CustomException("Proyecto de formación no encontrado con ID: " + id, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            throw new CustomException("Error buscando proyecto de formación", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Crear un nuevo proyecto de formación
    @PostMapping
    public ResponseEntity<TrainingProjectDTO> createTrainingProject(@RequestBody TrainingProjectDTO trainingProjectDTO) {
        try {
            TrainingProject trainingProject = modelMapper.map(trainingProjectDTO, TrainingProject.class);
            TrainingProject savedProject = trainingProjectService.save(trainingProject);
            TrainingProjectDTO savedProjectDTO = modelMapper.map(savedProject, TrainingProjectDTO.class);
            return new ResponseEntity<>(savedProjectDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new CustomException("Error creando proyecto de formación", HttpStatus.BAD_REQUEST);
        }
    }

    // Actualizar un proyecto de formación
    @PutMapping("/{id}")
    public ResponseEntity<TrainingProjectDTO> updateTrainingProject(@PathVariable Long id, @RequestBody TrainingProjectDTO trainingProjectDTO) {
        try {
            Optional<TrainingProject> existingProjectOpt = trainingProjectService.getById(id);
            if (existingProjectOpt.isPresent()) {
                TrainingProject updatedProject = modelMapper.map(trainingProjectDTO, TrainingProject.class);
                updatedProject.setId(id); // Asegurarse de que se mantiene el ID del proyecto existente
                TrainingProject savedProject = trainingProjectService.save(updatedProject);
                TrainingProjectDTO savedProjectDTO = modelMapper.map(savedProject, TrainingProjectDTO.class);
                return new ResponseEntity<>(savedProjectDTO, HttpStatus.OK);
            } else {
                throw new CustomException("Proyecto de formación no encontrado con ID: " + id, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            throw new CustomException("Error actualizando proyecto de formación", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Eliminar un proyecto de formación
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrainingProject(@PathVariable Long id) {
        try {
            Optional<TrainingProject> existingProjectOpt = trainingProjectService.getById(id);
            if (existingProjectOpt.isPresent()) {
                trainingProjectService.delete(id);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                throw new CustomException("Proyecto de formación no encontrado con ID: " + id, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            throw new CustomException("Error eliminando proyecto de formación", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
