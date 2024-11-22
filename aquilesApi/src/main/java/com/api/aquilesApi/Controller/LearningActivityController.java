package com.api.aquilesApi.Controller;

import com.api.aquilesApi.Business.LearningActivityBusiness;
import com.api.aquilesApi.Dto.LearningActivityDTO;
import com.api.aquilesApi.Utilities.response.ResponseHandler;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/learning-activities")
public class LearningActivityController {

    @Autowired
    private LearningActivityBusiness learningActivityBusiness;

    @GetMapping
    public ResponseEntity<Object> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Page<LearningActivityDTO> activities = learningActivityBusiness.findAll(page, size);
            return ResponseHandler.generateResponse("Activities retrieved successfully", activities);
        } catch (Exception e) {
            return ResponseHandler.generateErrorResponse(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id) {
        try {
            List<LearningActivityDTO> activity = learningActivityBusiness.findById(id);
            return ResponseHandler.generateResponse("Activity retrieved successfully", activity);
        } catch (Exception e) {
            return ResponseHandler.generateErrorResponse(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<Object> create(@Valid @RequestBody LearningActivityDTO learningActivityDTO) {
        try {
            Boolean result = learningActivityBusiness.add(learningActivityDTO);
            if (result) {
                return ResponseHandler.generateResponse("Activity created successfully", null);
            } else {
                return ResponseHandler.generateErrorResponse("Failed to create activity");
            }
        } catch (Exception e) {
            return ResponseHandler.generateErrorResponse(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(
            @PathVariable Long id,
            @Valid @RequestBody LearningActivityDTO learningActivityDTO) {
        try {
            Boolean result = learningActivityBusiness.update(learningActivityDTO, id);
            if (result) {
                return ResponseHandler.generateResponse("Activity updated successfully", null);
            } else {
                return ResponseHandler.generateErrorResponse("Failed to update activity");
            }
        } catch (Exception e) {
            return ResponseHandler.generateErrorResponse(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        try {
            Boolean result = learningActivityBusiness.delete(id);
            if (result) {
                return ResponseHandler.generateResponse("Activity deleted successfully", null);
            } else {
                return ResponseHandler.generateErrorResponse("Failed to delete activity");
            }
        } catch (Exception e) {
            return ResponseHandler.generateErrorResponse(e.getMessage());
        }
    }
}
