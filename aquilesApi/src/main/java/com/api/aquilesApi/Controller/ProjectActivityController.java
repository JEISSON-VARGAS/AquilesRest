package com.api.aquilesApi.Controller;

import com.api.aquilesApi.Business.ProjectActivityBusiness;
import com.api.aquilesApi.Dto.ProjectActivityDTO;
import com.api.aquilesApi.Utilities.Http.ResponseHttpApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
            return ResponseHttpApi.responseHttpFindAll(
                    projectActivityPage.getContent(),
                    ResponseHttpApi.CODE_OK,
                    "Consulta exitosa",
                    projectActivityPage.getTotalPages(),
                    projectActivityPage.getNumber(),
                    (int) projectActivityPage.getTotalElements()
            );
        } else {
            return ResponseHttpApi.responseHttpAction(
                    ResponseHttpApi.NO_CONTENT,
                    "No hay actividades de proyecto disponibles"
            );
        }
    }

    // Obtener actividad de proyecto por ID
    @GetMapping("/find/{id}")
    public Map<String, Object> findById(@PathVariable Long id) {
        ProjectActivityDTO projectActivityDTO = projectActivityBusiness.findById(id);
        if (projectActivityDTO != null) {
            return ResponseHttpApi.responseHttpFindId(
                    projectActivityDTO,
                    ResponseHttpApi.CODE_OK,
                    "Consulta exitosa"
            );
        } else {
            return ResponseHttpApi.responseHttpAction(
                    ResponseHttpApi.NO_CONTENT,
                    "No se encontró la actividad de proyecto"
            );
        }
    }

    // Agregar una nueva actividad de proyecto
    @PostMapping("/add")
    public Map<String, Object> add(@RequestBody ProjectActivityDTO projectActivityDTO) {
        boolean isAdded = projectActivityBusiness.add(projectActivityDTO);
        if (isAdded) {
            return ResponseHttpApi.responseHttpAction(
                    ResponseHttpApi.CODE_OK,
                    "Actividad de proyecto añadida exitosamente"
            );
        } else {
            return ResponseHttpApi.responseHttpError(
                    "Error al añadir la actividad de proyecto",
                    HttpStatus.BAD_REQUEST,
                    null
            );
        }
    }

    // Actualizar actividad de proyecto por ID
    @PutMapping("/update/{id}")
    public Map<String, Object> update(@PathVariable Long id, @RequestBody ProjectActivityDTO projectActivityDTO) {
        boolean isUpdated = projectActivityBusiness.update(id, projectActivityDTO);
        if (isUpdated) {
            return ResponseHttpApi.responseHttpAction(
                    ResponseHttpApi.CODE_OK,
                    "Actividad de proyecto actualizada exitosamente"
            );
        } else {
            return ResponseHttpApi.responseHttpError(
                    "Error al actualizar la actividad de proyecto",
                    HttpStatus.BAD_REQUEST,
                    null
            );
        }
    }

    // Eliminar actividad de proyecto por ID
    @DeleteMapping("/delete/{id}")
    public Map<String, Object> delete(@PathVariable Long id) {
        boolean isDeleted = projectActivityBusiness.delete(id);
        if (isDeleted) {
            return ResponseHttpApi.responseHttpAction(
                    ResponseHttpApi.CODE_OK,
                    "Actividad de proyecto eliminada exitosamente"
            );
        } else {
            return ResponseHttpApi.responseHttpError(
                    "Error al eliminar la actividad de proyecto",
                    HttpStatus.BAD_REQUEST,
                    null
            );
        }
    }
}
