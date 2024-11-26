package com.api.aquilesApi.Business;

import com.api.aquilesApi.Entity.LearningOutcome;
import com.api.aquilesApi.Repository.LearningOutcomeRepository;
import com.api.aquilesApi.Service.LearningOutcomeService;
import com.api.aquilesApi.Utilities.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;  // Aquí se agrega el import de HttpStatus
import org.springframework.stereotype.Service;

@Service
public class LearningOutcomeBusiness {

    @Autowired
    private LearningOutcomeRepository learningOutcomeRepository;

    @Autowired
    private LearningOutcomeService learningOutcomeService;

    /**
     * Verificar si ya existe un Learning Outcome con el código.
     */
    public void validateCode(Long code) {
        if (learningOutcomeRepository.existsByCode(code)) {
            throw new CustomException("El código ya está en uso.", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Verificar si ya existe un Learning Outcome con el nombre.
     */
    public void validateName(String name) {
        if (learningOutcomeRepository.existsByName(name)) {
            throw new CustomException("El nombre ya está en uso.", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Verificar si ya existe un Learning Outcome con la descripción.
     */
    public void validateDescription(String description) {
        if (learningOutcomeRepository.existsByDescription(description)) {
            throw new CustomException("La descripción ya está en uso.", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Validación de creación y actualización.
     * Asegura que no haya duplicados en el código, nombre y descripción.
     */
    public void validateBeforeSave(LearningOutcome learningOutcome) {
        validateCode(learningOutcome.getCode());
        validateName(learningOutcome.getName());
        validateDescription(learningOutcome.getDescription());
    }

    /**
     * Validar si la ID proporcionada es válida.
     */
    public void validateId(Long id) {
        if (!learningOutcomeRepository.existsById(id)) {
            throw new CustomException("Learning Outcome con ID " + id + " no encontrado.", HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Cambiar el estado de un Learning Outcome.
     * Este método alterna el estado de un Learning Outcome entre activo y no activo.
     */
    public Boolean toggleState(Long id) {
        LearningOutcome learningOutcome = learningOutcomeService.getById(id);
        learningOutcome.setState(!learningOutcome.getState());
        learningOutcomeService.save(learningOutcome);
        return learningOutcome.getState();
    }
}
