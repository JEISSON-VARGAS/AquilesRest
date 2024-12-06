package com.api.aquilesApi.Service;

import com.api.aquilesApi.Entity.TrainingProject;
import com.api.aquilesApi.Repository.TrainingProjectRepository;
import com.api.aquilesApi.Utilities.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainingProjectService {

    @Autowired
    private TrainingProjectRepository trainingProjectRepository;

    // Obtener un proyecto de formación por ID
    public TrainingProject getById(Long id) {
        return trainingProjectRepository.findById(id)
                .orElseThrow(() -> new CustomException("Proyecto de formación no encontrado con ID: " + id, HttpStatus.NOT_FOUND));
    }

    // Guardar un nuevo proyecto de formación
    public TrainingProject save(TrainingProject trainingProject) {
        return trainingProjectRepository.save(trainingProject);
    }

    // Actualizar un proyecto de formación existente
    public TrainingProject update(Long id, TrainingProject trainingProject) {
        // Verifica si el proyecto existe
        TrainingProject existingProject = getById(id);

        // Aquí puedes actualizar los campos del proyecto existente con los nuevos valores
        existingProject.setName(trainingProject.getName());
        existingProject.setDescription(trainingProject.getDescription());

        // Guarda el proyecto actualizado
        return trainingProjectRepository.save(existingProject);
    }

    // Eliminar un proyecto de formación
    public void delete(Long id) {
        TrainingProject existingProject = getById(id);
        trainingProjectRepository.delete(existingProject);
    }

    // Obtener todos los proyectos de formación
    public List<TrainingProject> getAll() {
        return trainingProjectRepository.findAll();
    }

    // Obtener todos los proyectos de formación con paginación
    public Page<TrainingProject> findAll(PageRequest pageRequest) {
        return trainingProjectRepository.findAll(pageRequest);
    }
}
