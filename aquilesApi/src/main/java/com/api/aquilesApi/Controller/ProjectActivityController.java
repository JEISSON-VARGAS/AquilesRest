package com.api.aquilesApi.Controller;

import com.api.aquilesApi.Business.ProjectActivityBusiness;
import com.api.aquilesApi.Dto.ProjectActivityDTO;
import com.api.aquilesApi.Utilities.Http.ResponseHttpApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/project-activity")
public class ProjectActivityController {

    @Autowired
    private ProjectActivityBusiness projectActivityBusiness;

    // Obtener todas las actividades de proyecto (paginadas)
    @GetMapping("/all")
    public Map<String, Object> findAll(@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size) {
        Page<ProjectActivityDTO> projectActivityPage = projectActivityBusiness.findAll(page, size);
        if (!projectActivityPage.isEmpty()) {
            Map<String, Object> response = new HashMap<>();
            response.put("activities", projectActivityPage.getContent());
            response.put("currentPage", projectActivityPage.getNumber());
            response.put("totalItems", projectActivityPage.getTotalElements());
            response.put("totalPages", projectActivityPage.getTotalPages());
            response.put("status", HttpStatus.OK);
            return response;
        } else {
            return ResponseHttpApi.responseHttpFind(
                    "No hay actividades de proyecto disponibles",
                    List.of(),
                    HttpStatus.NOT_FOUND
            );
        }
    }

    // Obtener actividad de proyecto por ID
    @GetMapping("/find/{id}")
    public Map<String, Object> findById(@PathVariable Long id) {
        ProjectActivityDTO projectActivityDTO = projectActivityBusiness.findById(id);
        if (projectActivityDTO != null) {
            return ResponseHttpApi.responseHttpFind(
                    "Consulta exitosa",
                    List.of(projectActivityDTO),
                    HttpStatus.OK
            );
        } else {
            return ResponseHttpApi.responseHttpFind(
                    "No se encontró la actividad de proyecto",
                    List.of(),
                    HttpStatus.NOT_FOUND
            );
        }
    }

    // Agregar una nueva actividad de proyecto
    @PostMapping("/add")
    public Map<String, Object> add(@RequestBody ProjectActivityDTO projectActivityDTO) {
        boolean isAdded = projectActivityBusiness.add(projectActivityDTO);
        if (isAdded) {
            return ResponseHttpApi.responseHttpPost(
                    "Actividad de proyecto añadida exitosamente",
                    HttpStatus.CREATED
            );
        } else {
            return ResponseHttpApi.responseHttpPost(
                    "Error al añadir la actividad de proyecto",
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    // Actualizar actividad de proyecto por ID
    @PutMapping("/update/{id}")
    public Map<String, Object> update(@PathVariable Long id, @RequestBody ProjectActivityDTO projectActivityDTO) {
        boolean isUpdated = projectActivityBusiness.update(id, projectActivityDTO);
        if (isUpdated) {
            return ResponseHttpApi.responseHttpPut(
                    "Actividad de proyecto actualizada exitosamente",
                    HttpStatus.OK
            );
        } else {
            return ResponseHttpApi.responseHttpPut(
                    "Error al actualizar la actividad de proyecto",
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    // Eliminar actividad de proyecto por ID
    @DeleteMapping("/delete/{id}")
    public Map<String, Object> delete(@PathVariable Long id) {
        boolean isDeleted = projectActivityBusiness.delete(id);
        if (isDeleted) {
            return ResponseHttpApi.responseHttpDelete(
                    "Actividad de proyecto eliminada exitosamente",
                    HttpStatus.OK
            );
        } else {
            return ResponseHttpApi.responseHttpDelete(
                    "Error al eliminar la actividad de proyecto",
                    HttpStatus.BAD_REQUEST
            );
        }
    }
}
