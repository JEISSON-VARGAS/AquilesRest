package com.api.aquilesApi.Business;

import com.api.aquilesApi.Dto.PhaseDTO;
import com.api.aquilesApi.Entity.Phase;
import com.api.aquilesApi.Service.PhaseService;
import com.api.aquilesApi.Utilities.CustomException;
import com.api.aquilesApi.Utilities.PhaseMapper; // Importamos el mapper
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class PhaseBusiness {

    @Autowired
    private PhaseService phaseService;

    // Obtener una fase por ID y convertirla a DTO
    public PhaseDTO getPhase(Long id) {
        Phase phase = phaseService.getById(id);
        return PhaseMapper.toDTO(phase); // Usamos el PhaseMapper
    }

    // Crear una nueva fase
    public void createPhase(PhaseDTO phaseDTO) {
        if (phaseService.existsName(phaseDTO.getName())) {
            throw new CustomException("Phase name already exists", HttpStatus.BAD_REQUEST);
        }
        if (phaseService.existsDescription(phaseDTO.getDescription())) {
            throw new CustomException("Phase description already exists", HttpStatus.BAD_REQUEST);
        }
        Phase phase = PhaseMapper.toEntity(phaseDTO); // Convertimos el DTO a entidad usando el mapper
        phaseService.save(phase); // Guardamos la fase
    }

    // Actualizar una fase existente
    public void updatePhase(Long id, PhaseDTO phaseDTO) {
        if (phaseService.existsName(phaseDTO.getName())) {
            throw new CustomException("Phase name already exists", HttpStatus.BAD_REQUEST);
        }
        if (phaseService.existsDescription(phaseDTO.getDescription())) {
            throw new CustomException("Phase description already exists", HttpStatus.BAD_REQUEST);
        }
        Phase phase = PhaseMapper.toEntity(phaseDTO); // Convertimos el DTO a entidad usando el mapper
        phase.setId(id); // Establecemos el ID para la actualización
        phaseService.save(phase); // Guardamos la fase actualizada
    }

    // Eliminar una fase
    public void deletePhase(Long id) {
        Phase phase = phaseService.getById(id);
        phaseService.delete(phase);
    }
}
