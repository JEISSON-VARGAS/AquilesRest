package com.api.aquilesApi.Business;

import com.api.aquilesApi.Dto.ProjectActivityDTO;
import com.api.aquilesApi.Entity.Phase;
import com.api.aquilesApi.Entity.TrainingProject;
import com.api.aquilesApi.Utilities.CustomException;
import com.api.aquilesApi.Service.PhaseService;
import com.api.aquilesApi.Service.ProjectActivityService;
import com.api.aquilesApi.Service.TrainingProjectService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProjectActivityBusiness {

    @Autowired
    private ProjectActivityService projectActivitieService;

    @Autowired
    private PhaseService phaseService;

    @Autowired
    private TrainingProjectService trainingProjectService;

    private final ModelMapper modelMapper = new ModelMapper();

    // Obtener todas las actividades con paginación
    public Page<ProjectActivityDTO> findAll(int page, int size) {
        try {
            PageRequest pageRequest = PageRequest.of(page, size);
            Page<ProjectActivityService> projectActivitiePage = projectActivitieService.findAll(pageRequest);
            if (projectActivitiePage.isEmpty()) {
                return Page.empty();
            }
            return projectActivitiePage.map(
                    projectActivitie -> modelMapper.map(projectActivitie, ProjectActivityDTO.class)
            );
        } catch (Exception e) {
            throw new CustomException("Error obteniendo actividades de proyecto", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Buscar actividad por ID
    public List<ProjectActivityDTO> findById(Long id) {
        List<ProjectActivityDTO> projectActivityDTOList = new ArrayList<>();
        try {
            ProjectActivityService projectActivitie = projectActivitieService.getById(id);
            if (projectActivitie != null) {
                projectActivityDTOList.add(modelMapper.map(projectActivitie, ProjectActivityDTO.class));
            } else {
                throw new CustomException("Actividad de proyecto no encontrada con ID: " + id, HttpStatus.NOT_FOUND);
            }
            return projectActivityDTOList;
        } catch (Exception e) {
            throw new CustomException("Error buscando actividad de proyecto", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Agregar nueva actividad de proyecto
    public Boolean add(ProjectActivityDTO projectActivityDTO) {
        try {
            ProjectActivityService projectActivitie = modelMapper.map(projectActivityDTO, ProjectActivityService.class);

            Phase phase = phaseService.getById(projectActivitie.getPhase().getId());
            TrainingProject trainingProject = trainingProjectService.getById(projectActivitie.getTrainingProjects().getId());

            projectActivitie.setPhase(phase);
            projectActivitie.setTrainingProjects(trainingProject);
            projectActivitieService.save(projectActivitie);

            return true;
        } catch (Exception e) {
            throw new CustomException("Error creando actividad de proyecto", HttpStatus.BAD_REQUEST);
        }
    }

    // Actualizar actividad de proyecto
    public Boolean update(Long id, ProjectActivityDTO projectActivityDTO) {
        try {
            ProjectActivityService projectActivitie = projectActivitieService.getById(id);
            if (projectActivitie == null) {
                throw new CustomException("Actividad de proyecto no encontrada con ID: " + id, HttpStatus.NOT_FOUND);
            }

            Phase phase = phaseService.getById(projectActivityDTO.getPhase().getId());
            TrainingProject trainingProject = trainingProjectService.getById(projectActivityDTO.getTrainingProjects().getId());

            projectActivityDTO.setPhase(modelMapper.map(phase, ProjectActivityDTO.PhaseDTO.class));
            projectActivityDTO.setTrainingProjects(modelMapper.map(trainingProject, ProjectActivityDTO.TrainingProjectDTO.class));

            modelMapper.map(projectActivityDTO, projectActivitie);
            projectActivitieService.save(projectActivitie);

            return true;
        } catch (Exception e) {
            throw new CustomException("Error actualizando actividad de proyecto", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Eliminar actividad de proyecto
    public Boolean delete(Long id) {
        try {
            ProjectActivityService projectActivitie = projectActivitieService.getById(id);
            if (projectActivitie == null || projectActivitie.getName() == null) {
                throw new CustomException("Actividad de proyecto no encontrada con ID: " + id, HttpStatus.NOT_FOUND);
            }
            projectActivitieService.delete(id);
            return true;
        } catch (Exception e) {
            throw new CustomException("Error eliminando actividad de proyecto", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
