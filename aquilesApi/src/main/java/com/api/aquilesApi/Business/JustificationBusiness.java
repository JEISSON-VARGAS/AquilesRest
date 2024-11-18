/*
package com.api.aquilesApi.Business;

import com.api.aquilesApi.Dto.AttendancesDto;
import com.api.aquilesApi.Dto.JustificationDto;
import com.api.aquilesApi.Entity.AttendancesEntity;
import com.api.aquilesApi.Entity.JustificationEntity;
import com.api.aquilesApi.Service.JustificationService;
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
public class JustificationBusiness {
    @Autowired
    private JustificationService justificationService;

    @Autowired
    private Util util;

    private final ModelMapper modelMapper = new ModelMapper();

    // Validación de Objeto
    private JustificationDto validationObject(Map<String, Object> json, JustificationDto justificationDto) {
        JSONObject dataObject = util.getData(json);

        if (dataObject.has("justificationId")) {
            justificationDto.setJustificationId(dataObject.getLong("justificationId"));
        } else {
            throw new CustomException("Justification ID is required", HttpStatus.BAD_REQUEST);
        }

        if (dataObject.has("justificationDocument") && !dataObject.getString("justificationDocument").isEmpty()) {
            justificationDto.setJustificationDocument(dataObject.getString("justificationDocument"));
        } else {
            throw new CustomException("Justification document is required", HttpStatus.BAD_REQUEST);
        }

        if (dataObject.has("justificationDescription") && !dataObject.getString("justificationDescription").isEmpty()) {
            justificationDto.setJustificationDescription(dataObject.getString("justificationDescription"));
        } else {
            throw new CustomException("Justification description is required", HttpStatus.BAD_REQUEST);
        }

        if (justificationService.existsByJustificationDocument(justificationDto.getJustificationDocument())) {
            throw new CustomException("Justification document already exists", HttpStatus.BAD_REQUEST);
        }

        return justificationDto;
    }

    // Find All
    public Page<JustificationDto> findAll(int page, int size) {
        try {
            PageRequest pageRequest = PageRequest.of(page, size);
            Page<JustificationEntity> justificationEntityPage = justificationService.findAll(pageRequest);
            return justificationEntityPage.map(entity -> modelMapper.map(entity, JustificationDto.class));
        } catch (DataAccessException e) {
            throw new CustomException("Error retrieving Justifications due to data access issues: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            throw new CustomException("An unexpected error occurred while retrieving justifications.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Find By Id
    public JustificationDto findById(Long id) {
        try {
            JustificationEntity justification = justificationService.getById(id);
            return modelMapper.map(justification, JustificationDto.class);
        } catch (CustomException e) {
            throw e;
        } catch (Exception e) {
            throw new CustomException("Error Getting Justification: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Add
    public void add(Map<String, Object> json) {
        try {
            JustificationDto justificationDto = new JustificationDto();
            justificationDto = validationObject(json, justificationDto);

            JustificationEntity justificationEntity = modelMapper.map(justificationDto, JustificationEntity.class);
            justificationService.save(justificationEntity);
        } catch (CustomException e) {
            throw e;
        } catch (DataAccessException e) {
            throw new CustomException("Error saving Justification due to data access issues: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            throw new CustomException("An unexpected error occurred while adding the justification.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Update
    public void update(Long justificationId, Map<String, Object> json) {
        try {
            var justificationDTO = modelMapper.map(justificationService.getById(justificationId), JustificationDto.class);
            var justification = modelMapper.map(this.validationObject(json, justificationDTO), JustificationEntity.class);
            justificationService.save(justification);
        } catch (CustomException e) {
            throw e;
        } catch (Exception e) {
            throw new CustomException("Error Updating Justification: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Delete
    public void delete(Long justificationId) {
        try {
            JustificationEntity justification = justificationService.getById(justificationId);
            justificationService.delete(justification);
        } catch (CustomException e) {
            throw e;
        } catch (Exception e) {
            throw new CustomException("Error Deleting Justification: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}

 */
