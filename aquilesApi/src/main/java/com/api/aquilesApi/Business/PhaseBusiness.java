package com.api.aquilesApi.Business;

import com.api.aquilesApi.Dto.PhaseDTO;
import com.api.aquilesApi.Entity.Phase;
import com.api.aquilesApi.Service.PhaseService;
import com.api.aquilesApi.Utilities.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PhaseBusiness {

    @Autowired
    private PhaseService phaseService;

    public PhaseDTO getPhase(Long id) {
        Phase phase = phaseService.getById(id);
        return phase.toDTO(); // Método de conversión de entidad a DTO
    }

    public void createPhase(PhaseDTO phaseDTO) {
        if (phaseService.existsName(phaseDTO.getName())) {
            throw new CustomException("Phase name already exists", HttpStatus.BAD_REQUEST);
        }
        if (phaseService.existsDescription(phaseDTO.getDescription())) {
            throw new CustomException("Phase description already exists", HttpStatus.BAD_REQUEST);
        }
        Phase phase = phaseDTO.toEntity(); // Conversión a entidad
        phaseService.save(phase); // Guardar la fase
    }

    public void updatePhase(Long id, PhaseDTO phaseDTO) {
        if (phaseService.existsName(phaseDTO.getName())) {
            throw new CustomException("Phase name already exists", HttpStatus.BAD_REQUEST);
        }
        if (phaseService.existsDescription(phaseDTO.getDescription())) {
            throw new CustomException("Phase description already exists", HttpStatus.BAD_REQUEST);
        }
        Phase phase = phaseDTO.toEntity(); // Convertir DTO a entidad
        phase.setId(id); // Setear el ID para actualizar
        phaseService.save(phase); // Guardar la fase actualizada
    }

    public void deletePhase(Long id) {
        Phase phase = phaseService.getById(id);
        phaseService.delete(phase);
    }
}
