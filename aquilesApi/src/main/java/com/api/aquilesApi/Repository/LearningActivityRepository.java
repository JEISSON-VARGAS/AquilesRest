package com.api.aquilesApi.Repository;

import com.api.aquilesApi.Entity.LearningActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LearningActivityRepository extends JpaRepository<LearningActivity, Long> {

    /**
     * Verifica si existe una actividad de aprendizaje con un nombre específico.
     *
     * @param name el nombre de la actividad de aprendizaje.
     * @return true si existe, de lo contrario false.
     */
    boolean existsByName(String name);

    /**
     * Verifica si existe una actividad de aprendizaje con una descripción específica.
     *
     * @param description la descripción de la actividad de aprendizaje.
     * @return true si existe, de lo contrario false.
     */
    boolean existsByDescription(String description);
}
