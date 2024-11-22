package com.api.aquilesApi.Business;

import com.api.aquilesApi.Dto.CompetenceDTO;
import com.api.aquilesApi.Dto.PhaseDTO;
import com.api.aquilesApi.Entity.Competence;
import com.api.aquilesApi.Service.CompetenceService;
import com.api.aquilesApi.Utilities.Util;
import com.api.aquilesApi.Utilities.CustomException;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class CompetenceBusiness {

    @Autowired
    private CompetenceService competenceService;

    @Autowired
    private Util util;

    private final ModelMapper modelMapper = new ModelMapper();

    /**
     * Interfaz funcional personalizada para validar condiciones booleanas.
     */
    @FunctionalInterface
    private interface BooleanCheck {
        boolean evaluate();
    }

    /**
     * Valida los datos de una competencia y asegura que no haya duplicados.
     */
    private CompetenceDTO validateCompetence(Map<String, Object> json, CompetenceDTO competenceDTO) {
        JSONObject dataObject = util.getData(json);

        competenceDTO.setCode(dataObject.getLong("code"));
        competenceDTO.setName(dataObject.getString("name"));
        competenceDTO.setDescription(dataObject.getString("description"));
        competenceDTO.setState(dataObject.getBoolean("state"));

        PhaseDTO phaseDTO = new PhaseDTO();
        phaseDTO.setId(dataObject.getLong("phase"));

        validateDuplicate("code", competenceDTO.getCode(), () -> competenceService.existsCode(competenceDTO.getCode()));
        validateDuplicate("name", competenceDTO.getName(), () -> competenceService.existsName(competenceDTO.getName()));
        validateDuplicate("description", competenceDTO.getDescription(), () -> competenceService.existsDescription(competenceDTO.getDescription()));

        return competenceDTO;
    }

    private void validateDuplicate(String fieldName, Object value, BooleanCheck checkDuplicate) {
        if (!"update".equals(methodName()) || !checkDuplicate.evaluate()) {
            if (checkDuplicate.evaluate()) {
                throw new CustomException("Duplicate " + fieldName + ": " + value, HttpStatus.BAD_REQUEST);
            }
        }
    }

    /**
     * Devuelve una lista paginada de competencias.
     */
    public Page<CompetenceDTO> findAll(int page, int size) {
        try {
            PageRequest pageRequest = PageRequest.of(page, size);
            return competenceService.findAll(pageRequest).map(competence -> modelMapper.map(competence, CompetenceDTO.class));
        } catch (Exception e) {
            throw new CustomException("Error getting Competences: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Devuelve una competencia por ID.
     */
    public CompetenceDTO findById(Long id) {
        try {
            Competence competence = competenceService.getById(id);
            return modelMapper.map(competence, CompetenceDTO.class);
        } catch (Exception e) {
            throw new CustomException("Error getting Competence: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Crea una nueva competencia.
     */
    public void add(Map<String, Object> json) {
        try {
            CompetenceDTO competenceDTO = new CompetenceDTO();
            Competence competence = modelMapper.map(validateCompetence(json, competenceDTO), Competence.class);
            competenceService.save(competence);
        } catch (Exception e) {
            throw new CustomException("Error adding Competence: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Crea competencias de forma masiva.
     */
    public void addMassive(List<Map<String, Object>> json) {
        try {
            List<Competence> competences = new ArrayList<>();
            for (Map<String, Object> data : json) {
                CompetenceDTO competenceDTO = new CompetenceDTO();
                Competence competence = modelMapper.map(validateCompetence(data, competenceDTO), Competence.class);
                competences.add(competence);
            }
            competenceService.saveAll(competences);
        } catch (Exception e) {
            throw new CustomException("Error adding Competences: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Actualiza una competencia.
     */
    public void update(Long id, Map<String, Object> json) {
        try {
            CompetenceDTO competenceDTO = modelMapper.map(competenceService.getById(id), CompetenceDTO.class);
            Competence competence = modelMapper.map(validateCompetence(json, competenceDTO), Competence.class);
            competenceService.save(competence);
        } catch (Exception e) {
            throw new CustomException("Error updating Competence: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Alterna el estado de una competencia.
     */
    public Boolean toggle(Long id) {
        try {
            Competence competence = competenceService.getById(id);
            competence.setState(!competence.getState());
            competenceService.save(competence);
            return competence.getState();
        } catch (Exception e) {
            throw new CustomException("Error toggling Competence: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Elimina una competencia.
     */
    public void delete(Long id) {
        try {
            Competence competence = competenceService.getById(id);
            competenceService.delete(competence);
        } catch (Exception e) {
            throw new CustomException("Error deleting Competence: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
