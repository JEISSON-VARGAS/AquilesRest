package com.api.aquilesApi.Repository;

import com.api.aquilesApi.Entity.TrainingProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingProjectRepository extends JpaRepository<TrainingProject, Long> {
    // Puedes definir métodos personalizados si es necesario, por ejemplo:

    // Buscar TrainingProject por nombre
    TrainingProject findByName(String name);

    // Si necesitas métodos de búsqueda complejos, puedes usar @Query para definir consultas personalizadas
}
