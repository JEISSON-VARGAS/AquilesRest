package com.api.aquilesApi.Business;

import com.api.aquilesApi.Dto.ProjectActivityDTO;
import com.api.aquilesApi.Dto.TrainingProjectDTO;  // Importa el DTO de TrainingProject
import com.api.aquilesApi.Entity.LearningOutcome;
import com.api.aquilesApi.Dto.LearningOutcomeDTO;
import com.api.aquilesApi.Entity.ProjectActivity;
import com.api.aquilesApi.Entity.TrainingProject;
import com.api.aquilesApi.Service.LearningOutcomeService;
import com.api.aquilesApi.Service.ProjectActivityService;
import com.api.aquilesApi.Service.TrainingProjectService;
import com.api.aquilesApi.Utilities.CustomException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class ProjectActivityBusiness {

    @Autowired
    private ProjectActivityService projectActivityService;

    @Autowired
    private LearningOutcomeService learningOutcomeService;

    @Autowired
    private TrainingProjectService trainingProjectService;

    private final ModelMapper modelMapper = new ModelMapper();

    // Obtener todas las actividades con paginación
    public Page<ProjectActivityDTO> findAll(int page, int size) {
        try {
            PageRequest pageRequest = PageRequest.of(page, size);
            Page<ProjectActivity> projectActivityPage = projectActivityService.findAll(pageRequest);
            if (projectActivityPage.isEmpty()) {
                return Page.empty();
            }
            return projectActivityPage.map(
                    projectActivity -> modelMapper.map(projectActivity, ProjectActivityDTO.class)
            );
        } catch (Exception e) {
            throw new CustomException("Error obteniendo actividades de proyecto", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Buscar actividad por ID
    public ProjectActivityDTO findById(Long id) {
        try {
            ProjectActivity projectActivity = projectActivityService.getById(id);
            if (projectActivity != null) {
                return modelMapper.map(projectActivity, ProjectActivityDTO.class);
            } else {
                throw new CustomException("Actividad de proyecto no encontrada con ID: " + id, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            throw new CustomException("Error buscando actividad de proyecto", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Agregar nueva actividad de proyecto
    public Boolean add(ProjectActivityDTO projectActivityDTO) {
        try {
            ProjectActivity projectActivity = modelMapper.map(projectActivityDTO, ProjectActivity.class);

            LearningOutcome learningOutcome = learningOutcomeService.getById(projectActivityDTO.getLearningOutcome().getId());
            TrainingProject trainingProject = trainingProjectService.getById(projectActivityDTO.getTrainingProjects().getId());

            projectActivity.setLearningOutcome(learningOutcome);  // Establecer LearningOutcome
            projectActivity.setTrainingProject(trainingProject);  // Establecer el TrainingProject

            projectActivityService.save(projectActivity);

            return true;
        } catch (Exception e) {
            throw new CustomException("Error creando actividad de proyecto", HttpStatus.BAD_REQUEST);
        }
    }

    // Actualizar actividad de proyecto
    public Boolean update(Long id, ProjectActivityDTO projectActivityDTO) {
        try {
            ProjectActivity projectActivity = projectActivityService.getById(id);
            if (projectActivity == null) {
                throw new CustomException("Actividad de proyecto no encontrada con ID: " + id, HttpStatus.NOT_FOUND);
            }

            LearningOutcome learningOutcome = learningOutcomeService.getById(projectActivityDTO.getLearningOutcome().getId());
            TrainingProject trainingProject = trainingProjectService.getById(projectActivityDTO.getTrainingProjects().getId());

            // Mapeo de los objetos TrainingProject y LearningOutcome a sus DTOs correspondientes
            projectActivityDTO.setLearningOutcome(modelMapper.map(learningOutcome, LearningOutcomeDTO.class));
            projectActivityDTO.setTrainingProjects(modelMapper.map(trainingProject, TrainingProjectDTO.class)); // Aquí está el cambio

            modelMapper.map(projectActivityDTO, projectActivity);
            projectActivityService.save(projectActivity);

            return true;
        } catch (Exception e) {
            throw new CustomException("Error actualizando actividad de proyecto", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Eliminar actividad de proyecto
    public Boolean delete(Long id) {
        try {
            ProjectActivity projectActivity = projectActivityService.getById(id);
            if (projectActivity == null || projectActivity.getName() == null) {
                throw new CustomException("Actividad de proyecto no encontrada con ID: " + id, HttpStatus.NOT_FOUND);
            }

            // Aquí pasas el objeto projectActivity en lugar de solo el ID
            projectActivityService.delete(projectActivity);
            return true;
        } catch (Exception e) {
            throw new CustomException("Error eliminando actividad de proyecto", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
