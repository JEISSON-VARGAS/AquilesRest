package com.api.aquilesApi.Utilities;

import com.api.aquilesApi.Dto.PhaseDTO;
import com.api.aquilesApi.Entity.Phase;
import com.api.aquilesApi.Dto.TrainingProjectDTO;

public class PhaseMapper {

    // Método para convertir una entidad Phase en un DTO
    public static PhaseDTO toDTO(Phase phase) {
        PhaseDTO dto = new PhaseDTO();
        dto.setId(phase.getId());
        dto.setName(phase.getName());
        dto.setDescription(phase.getDescription());
        dto.setState(phase.getState());
        dto.setCreatedAt(phase.getCreatedAt());
        dto.setUpdatedAt(phase.getUpdatedAt());

        if (phase.getTrainingProject() != null) {
            TrainingProjectDTO trainingProjectDTO = new TrainingProjectDTO();
            trainingProjectDTO.setId(phase.getTrainingProject().getId());
            trainingProjectDTO.setName(phase.getTrainingProject().getName());
            // Mapear otras propiedades de TrainingProject si son necesarias
            dto.setTrainingProjectDTO(trainingProjectDTO);
        }
        return dto;
    }

    // Si necesitas la conversión de DTO a entidad, también puedes crear un método similar
    public static Phase toEntity(PhaseDTO dto) {
        Phase phase = new Phase();
        phase.setId(dto.getId());
        phase.setName(dto.getName());
        phase.setDescription(dto.getDescription());
        phase.setState(dto.getState());
        phase.setCreatedAt(dto.getCreatedAt());
        phase.setUpdatedAt(dto.getUpdatedAt());

        // Aquí puedes mapear también el TrainingProjectDTO a TrainingProject si es necesario
        // phase.setTrainingProject( ... );

        return phase;
    }
}
