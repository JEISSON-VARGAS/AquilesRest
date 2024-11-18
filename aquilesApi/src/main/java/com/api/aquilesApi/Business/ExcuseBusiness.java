package com.api.aquilesApi.Business;

import com.api.aquilesApi.Dto.AttendanceDTO;
import com.api.aquilesApi.Dto.ExcuseDTO;
import com.api.aquilesApi.Entity.Attendance;
import com.api.aquilesApi.Entity.Excuse;
import com.api.aquilesApi.Service.ExcuseService;
import com.api.aquilesApi.Utilities.CustomException;
import com.api.aquilesApi.Utilities.Util;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ExcuseBusiness {

    @Autowired
    private ExcuseService excuseService;

    @Autowired
    private Util util;

    private final ModelMapper modelMapper = new ModelMapper();

    // Validación de Objeto
    private ExcuseDTO validationObject(Map<String, Object> json, ExcuseDTO excuseDto) {
        // Extrae datos del objeto JSON utilizando la utilidad 'getData'
        JSONObject dataObject = util.getData(json);

        // Asigna los valores del JSON al DTO, asegurando que se realicen las validaciones necesarias
        if (dataObject.has("excuseId")) {
            excuseDto.setExcuseId(dataObject.getLong("excuseId"));
        } else {
            throw new CustomException("Excuse ID is required", HttpStatus.BAD_REQUEST);
        }

        if (dataObject.has("excuseDocument") && !dataObject.getString("excuseDocument").isEmpty()) {
            excuseDto.setExcuseDocument(dataObject.getString("excuseDocument"));
        } else {
            throw new CustomException("Excuse document is required", HttpStatus.BAD_REQUEST);
        }

        if (dataObject.has("excuseDescription") && !dataObject.getString("excuseDescription").isEmpty()) {
            excuseDto.setExcuseDescription(dataObject.getString("excuseDescription"));
        } else {
            throw new CustomException("Excuse description is required", HttpStatus.BAD_REQUEST);
        }

        // Realiza validaciones adicionales según los requisitos de negocio

        // Por ejemplo, si necesitas verificar si un documento ya ha sido registrado:
        if (excuseService.existsByExcuseDocument(excuseDto.getExcuseDocument())) {
            throw new CustomException("Excuse document already exists", HttpStatus.BAD_REQUEST);
        }

        return excuseDto;
    }

    // Find All
    public Page<ExcuseDTO> findAll(int page , int size) {
        try {
            PageRequest pageRequest = PageRequest.of(page, size);
            Page<Excuse> excuseEntityPage = excuseService.findAll(pageRequest);
            return excuseEntityPage.map(entity -> modelMapper.map(entity, ExcuseDTO.class));
        } catch (DataAccessException e) {
            // Manejo específico para errores de acceso a datos
            throw new CustomException("Error retrieving Excuses due to data access issues: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            // Manejo genérico para cualquier otra excepción
            throw new CustomException("An unexpected error occurred while retrieving excuses.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Find By Id
    public ExcuseDTO findById(Long id) {
        try {
            Excuse excuse = excuseService.getById(id);
            return modelMapper.map(excuse, ExcuseDTO.class);
        } catch (CustomException e) {
            throw e; // Lanzar la excepción personalizada
        } catch (Exception e) {
            throw new CustomException("Error Getting Excuse: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Add
    public void add(Map<String, Object> json) {
        try {
            // Crea un nuevo objeto ExcuseDto
            ExcuseDTO excuseDto = new ExcuseDTO();

            // Válida y asigna los valores del JSON al DTO
            excuseDto = validationObject(json, excuseDto);

            // Mapea el DTO a la entidad
            Excuse excuseEntity = modelMapper.map(excuseDto, Excuse.class);

            // Guarda la entidad en la base de datos
            excuseService.save(excuseEntity);
        } catch (CustomException e) {
            // Lanza la excepción personalizada si ocurre
            throw e;
        } catch (DataAccessException e) {
            // Manejo específico para errores de acceso a datos
            throw new CustomException("Error saving Excuse due to data access issues: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            // Manejo genérico para cualquier otra excepción
            throw new CustomException("An unexpected error occurred while adding the excuse.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Update
    public void update(Long excuseId , Map<String , Object> json) {
        try {
            var excuseDTO = modelMapper.map(excuseService.getById(excuseId), ExcuseDTO.class);
            var excuse = modelMapper.map(this.validationObject(json, excuseDTO), Excuse.class);
            excuseService.save(excuse);
        } catch (CustomException e) {
            throw e; // Lanzar la excepción personalizada
        } catch (Exception e) {
            throw new CustomException("Error Updating Excuse: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Delete
    public void delete(Long excuseId) {
        try {
            Excuse excuse = excuseService.getById(excuseId);
            excuseService.delete(excuse);
        } catch (CustomException e) {
            throw e; // Lanzar la excepción personalizada
        } catch (Exception e) {
            throw new CustomException("Error Deleting Excuse: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
