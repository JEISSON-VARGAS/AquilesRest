package com.api.aquilesApi.Service;

import com.api.aquilesApi.Entity.LearningOutcome;
import com.api.aquilesApi.Repository.LearningOutcomeRepository;
import com.api.aquilesApi.Service.Dao.Idao;
import com.api.aquilesApi.Utilities.CustomException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class LearningOutcomeService implements Idao<LearningOutcome, Long> {

    @Autowired
    private LearningOutcomeRepository learningOutcomeRepository;

    @Override
    public LearningOutcome getById(Long id) {
        // Lanza una excepción personalizada si no se encuentra el objeto
        return learningOutcomeRepository.findById(id).orElseThrow(() ->
                new CustomException("Learning Outcome con id " + id + " no encontrado", HttpStatus.NO_CONTENT));
    }

    @Override
    public void update(LearningOutcome entity) {
        // Implementación para actualizar entidad si es necesario
    }

    @Transactional
    @Override
    public LearningOutcome save(LearningOutcome obj) {
        // Guarda el objeto y lo devuelve (útil si tiene campos autogenerados)
        this.learningOutcomeRepository.save(obj);
        return obj;
    }

    @Transactional
    @Override
    public void saveAll(Iterable<LearningOutcome> obj) {
        // Guarda una colección de objetos
        this.learningOutcomeRepository.saveAll(obj);
    }

    @Transactional
    @Override
    public void delete(LearningOutcome obj) {
        // Elimina el objeto de la base de datos
        this.learningOutcomeRepository.delete(obj);
    }

    @Override
    public void create(LearningOutcome entity) {
        // Implementación para crear entidad si es necesario
    }

    @Override
    public Page<LearningOutcome> findAll(PageRequest pageRequest) {
        // Devuelve una lista paginada de objetos
        return learningOutcomeRepository.findAll(pageRequest);
    }

    public boolean existsCode(Long code) {
        // Verifica si existe un LearningOutcome con el código dado
        return learningOutcomeRepository.existsByCode(code);
    }

    public boolean existsName(String name) {
        // Verifica si existe un LearningOutcome con el nombre dado
        return learningOutcomeRepository.existsByName(name);
    }

    public boolean existsDescription(String description) {
        // Verifica si existe un LearningOutcome con la descripción dada
        return learningOutcomeRepository.existsByDescription(description);
    }

    /**
     * Cambiar el estado activo/inactivo de un Learning Outcome.
     * @param id ID del Learning Outcome
     * @return El nuevo estado
     */
    @Transactional
    public Boolean toggle(Long id) {
        // Obtiene el LearningOutcome
        LearningOutcome learningOutcome = this.getById(id);

        // Cambia el estado
        learningOutcome.setState(!learningOutcome.getState());

        // Guarda el cambio
        learningOutcomeRepository.save(learningOutcome);

        // Devuelve el nuevo estado
        return learningOutcome.getState();
    }
}
