package com.api.aquilesApi.Controller;

import com.api.aquilesApi.Dto.PhaseDTO;
import com.api.aquilesApi.Entity.Phase;
import com.api.aquilesApi.Service.PhaseService;
import com.api.aquilesApi.Utilities.Http.ResponseHttpApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("api/phase")  // Ruta ajustada a "api/phase"
public class PhaseController {

    @Autowired
    private PhaseService phaseService;

    @GetMapping("/all")
    public Map<String, Object> all(@RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "10") int size) {
        Page<Phase> phasePage = phaseService.findAll(PageRequest.of(page, size));
        if (!phasePage.isEmpty()) {
            Map<String, Object> response = new HashMap<>();
            response.put("phases", phasePage.getContent());
            response.put("currentPage", phasePage.getNumber());
            response.put("totalItems", phasePage.getTotalElements());
            response.put("totalPages", phasePage.getTotalPages());
            response.put("status", HttpStatus.OK);
            return response;
        } else {
            return ResponseHttpApi.responseHttpFind("Nothing found", new ArrayList<>(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/add")
    public Map<String, Object> addPhase(@RequestBody PhaseDTO phaseDTO) {
        Phase phase = new Phase();
        phase.setName(phaseDTO.getName());
        phase.setDescription(phaseDTO.getDescription());
        phase.setState(phaseDTO.getState());
        phase.setTrainingProject(phaseDTO.getTrainingProjectDTO().toEntity());

        try {
            phaseService.save(phase);
            return ResponseHttpApi.responseHttpPost("Phase added successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseHttpApi.responseHttpPost("Error adding Phase", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/find/{id}")
    public Map<String, Object> findById(@PathVariable Long id) {
        Phase phase = phaseService.getById(id);
        if (phase != null) {
            return ResponseHttpApi.responseHttpFind("Successfully found", phase, HttpStatus.OK);
        } else {
            return ResponseHttpApi.responseHttpFind("Phase not found", new HashMap<>(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{id}")
    public Map<String, Object> update(@PathVariable("id") Long id, @RequestBody PhaseDTO phaseDTO) {
        Phase phase = phaseService.getById(id);
        if (phase != null) {
            phase.setName(phaseDTO.getName());
            phase.setDescription(phaseDTO.getDescription());
            phase.setState(phaseDTO.getState());
            phase.setTrainingProject(phaseDTO.getTrainingProjectDTO().toEntity());

            try {
                phaseService.save(phase);
                return ResponseHttpApi.responseHttpPut("Phase updated successfully", HttpStatus.OK);
            } catch (Exception e) {
                return ResponseHttpApi.responseHttpPut("Error updating Phase", HttpStatus.BAD_REQUEST);
            }
        } else {
            return ResponseHttpApi.responseHttpPut("Phase not found", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public Map<String, Object> delete(@PathVariable Long id) {
        Phase phase = phaseService.getById(id);
        if (phase != null) {
            phaseService.delete(phase);
            return ResponseHttpApi.responseHttpDelete("Phase deleted successfully", HttpStatus.OK);
        } else {
            return ResponseHttpApi.responseHttpDelete("Error Deleting Phase", HttpStatus.BAD_REQUEST);
        }
    }
}
