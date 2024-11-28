package com.api.aquilesApi.Business;

import com.api.aquilesApi.Entity.TrainingProject;
import com.api.aquilesApi.Repository.TrainingProjectRepository;
import com.api.aquilesApi.Dto.TrainingProjectDTO;
import com.api.aquilesApi.Utilities.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TrainingProjectBusiness {

    @Autowired
    private TrainingProjectRepository trainingProjectRepository;

    // Lógica de negocio para obtener todos los proyectos de formación
    public List<TrainingProject> getAll() {
        try {
            return trainingProjectRepository.findAll();
        } catch (Exception e) {
            throw new CustomException("Error obteniendo los proyectos de formación", e);
        }
    }

    // Lógica de negocio para obtener un proyecto de formación por su ID
    public Optional<TrainingProject> getById(Long id) {
        try {
            return trainingProjectRepository.findById(id);
        } catch (Exception e) {
            throw new CustomException("Error buscando proyecto de formación con ID: " + id, e);
        }
    }

    // Lógica de negocio para guardar un nuevo proyecto de formación
    public TrainingProject save(TrainingProject trainingProject) {
        try {
            return trainingProjectRepository.save(trainingProject);
        } catch (Exception e) {
            throw new CustomException("Error guardando el proyecto de formación", e);
        }
    }

    // Lógica de negocio para actualizar un proyecto de formación existente
    public TrainingProject update(Long id, TrainingProjectDTO trainingProjectDTO) {
        try {
            Optional<TrainingProject> existingProjectOpt = trainingProjectRepository.findById(id);
            if (existingProjectOpt.isPresent()) {
                TrainingProject existingProject = existingProjectOpt.get();
                // Actualizamos los campos necesarios
                existingProject.setName(trainingProjectDTO.getName());
                existingProject.setDescription(trainingProjectDTO.getDescription());
                existingProject.setState(trainingProjectDTO.getState());
                // Asumiendo que hay más campos que se pueden actualizar
                return trainingProjectRepository.save(existingProject);
            } else {
                throw new CustomException("Proyecto de formación no encontrado con ID: " + id, null);
            }
        } catch (Exception e) {
            throw new CustomException("Error actualizando el proyecto de formación", e);
        }
    }

    // Lógica de negocio para eliminar un proyecto de formación
    public void delete(Long id) {
        try {
            Optional<TrainingProject> existingProjectOpt = trainingProjectRepository.findById(id);
            if (existingProjectOpt.isPresent()) {
                trainingProjectRepository.deleteById(id);
            } else {
                throw new CustomException("Proyecto de formación no encontrado con ID: " + id, null);
            }
        } catch (Exception e) {
            throw new CustomException("Error eliminando el proyecto de formación", e);
        }
    }
}
