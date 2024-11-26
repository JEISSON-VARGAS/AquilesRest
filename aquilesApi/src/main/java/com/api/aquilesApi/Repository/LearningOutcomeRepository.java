package com.api.aquilesApi.Repository;

import com.api.aquilesApi.Entity.LearningOutcome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LearningOutcomeRepository extends JpaRepository<LearningOutcome, Long> {

    /**
     * Verifica si existe un LearningOutcome con un código específico.
     *
     * @param code Código del LearningOutcome.
     * @return true si existe, false si no.
     */
    boolean existsByCode(Long code);

    /**
     * Verifica si existe un LearningOutcome con un nombre específico.
     *
     * @param name Nombre del LearningOutcome.
     * @return true si existe, false si no.
     */
    boolean existsByName(String name);

    /**
     * Verifica si existe un LearningOutcome con una descripción específica.
     *
     * @param description Descripción del LearningOutcome.
     * @return true si existe, false si no.
     */
    boolean existsByDescription(String description);

    /**
     * Obtiene todos los LearningOutcome activos.
     *
     * @return Lista de LearningOutcome donde active = true.
     */
    List<LearningOutcome> findAllByActiveTrue();

    /**
     * Obtiene todos los LearningOutcome inactivos.
     *
     * @return Lista de LearningOutcome donde active = false.
     */
    List<LearningOutcome> findAllByActiveFalse();
}
