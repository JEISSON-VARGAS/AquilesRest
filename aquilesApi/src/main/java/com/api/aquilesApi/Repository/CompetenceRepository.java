package com.api.aquilesApi.Repository;

import com.api.aquilesApi.Entity.Competence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompetenceRepository extends JpaRepository<Competence, Long> {

    // Verifica si existe una competencia con un código específico
    boolean existsByCode(Long code);

    // Verifica si existe una competencia con un nombre específico
    boolean existsByName(String name);

    // Verifica si existe una competencia con una descripción específica
    boolean existsByDescription(String description);
}
