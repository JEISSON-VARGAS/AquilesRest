package com.api.aquilesApi.Business;

import com.api.aquilesApi.Dto.ProjectDto;
import com.api.aquilesApi.Entity.ProjectEntity;
import com.api.aquilesApi.Entity.Teams_ScrumEntity;
import com.api.aquilesApi.Service.ProjectService;
import com.api.aquilesApi.Service.StateAttendanceService;
import com.api.aquilesApi.Service.Team_ScrumService;
import com.api.aquilesApi.Utilities.CustomException;
import com.api.aquilesApi.Utilities.Util;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ProjectBusiness {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private Team_ScrumService teamScrumService;

    @Autowired
    private StateAttendanceService stateAttendanceService;

    @Autowired
    private Util util;

    private final ModelMapper modelMapper = new ModelMapper();

    // Validación Objeto
    private ProjectDto validationObject(Map<String, Object> json, ProjectDto projectDto) {
        JSONObject dataObject = util.getData(json);

        if (!dataObject.has("projectId") || dataObject.isNull("projectId")) {
            throw new CustomException("Project ID is missing", HttpStatus.BAD_REQUEST);
        }

        projectDto.setProjectId(dataObject.getLong("projectId"));
        projectDto.setDescription(dataObject.getString("description"));
        projectDto.setProblem(dataObject.getString("problem"));
        projectDto.setObjectives(dataObject.getString("objectives"));
        projectDto.setJustification(dataObject.getString("justification"));

        // Obtener el ID del equipo Scrum
        Long teamScrumId = dataObject.getLong("fk_team_scrum_id");
        Teams_ScrumEntity teamScrum = teamScrumService.getById(teamScrumId);

        if (teamScrum == null) {
            throw new CustomException("Team Scrum not found for id: " + teamScrumId, HttpStatus.BAD_REQUEST);
        }

        // Aquí solo estás asignando el ID, no la entidad completa
        projectDto.setFk_team_scrum_id(teamScrum.getTeamScrumId());

        return projectDto;
    }

    // Find All
    public Page<ProjectDto>findAll(int page , int size){
        try {
            PageRequest pageRequest = PageRequest.of(page, size);
            Page<ProjectEntity> projectEntityPage = projectService.findAll(pageRequest);

            List<ProjectDto> projectDtoList = projectEntityPage.getContent()
                    .stream()
                    .map(entity -> modelMapper.map(entity, ProjectDto.class))
                    .collect(Collectors.toList());

            return new PageImpl<>(projectDtoList , pageRequest , projectEntityPage.getTotalElements());
        } catch (Exception e){
            throw new CustomException("Error Retrieving Projects: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Find By ID
    public ProjectDto findById(Long id){
        try {
            ProjectEntity project = projectService.getById(id);
            //Configurar El Mapeo Manualmente Si Es Necesario
            modelMapper.typeMap(ProjectEntity.class , ProjectDto.class)
                    .addMapping(ProjectEntity::getProjectId , ProjectDto::setProjectId);
            return modelMapper.map(project , ProjectDto.class);
        } catch (CustomException e){
            throw e;
        } catch (Exception e){
            throw new CustomException("Error Getting Project By Id: " + e.getMessage() , HttpStatus.BAD_REQUEST);
        }
    }

    // add
    public void add(Map<String, Object> json) {
        try {
            // Crear un nuevo DTO
            ProjectDto projectDto = new ProjectDto();

            // Validar y asignar datos del JSON al DTO
            projectDto = validationObject(json, projectDto);

            // Convertir DTO a Entidad
            ProjectEntity projectEntity = modelMapper.map(projectDto, ProjectEntity.class);

            // Obtener la entidad del equipo Scrum por su ID usando directamente fk_team_scrum_id
            Teams_ScrumEntity teamScrum = teamScrumService.getById(projectDto.getFk_team_scrum_id());

            // Asignar la entidad del equipo Scrum al proyecto antes de guardarlo
            projectEntity.setFk_team_scrum_id(teamScrum);

            // Guardar la entidad de proyecto
            projectService.create(projectEntity);

        } catch (CustomException e) {
            throw e;
        } catch (Exception e) {
            throw new CustomException("Error Creating Project: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Update
    public void update(Long projectId , Map<String , Object> json ){
        try {
            var projectDto = modelMapper.map(projectService.getById(projectId), ProjectDto.class);
            var project = modelMapper.map(this.validationObject(json, projectDto), ProjectEntity.class);
            projectService.save(project);
        } catch (CustomException e){
            throw e;
        } catch (Exception e){
            throw new CustomException("Error Updating Project : " + e.getMessage() , HttpStatus.BAD_REQUEST);
        }
    }

    // Delete
    public void delete(Long projectId){
        try {
            ProjectEntity project = projectService.getById(projectId);
            projectService.delete(project);
        } catch (CustomException e){
            throw e;
        } catch (Exception e){
            throw new CustomException("Error Deleting Project: " + e.getMessage() , HttpStatus.BAD_REQUEST);
        }
    }



    /*
    public void delete(Long teamScrumId){
        try {
            Teams_ScrumEntity teamsScrum = teamScrumService.getById(teamScrumId);
            teamScrumService.delete(teamsScrum);
        } catch (CustomException e){
            throw e;
        } catch (Exception e){
            throw new CustomException("Error Deleting Team Scrum:" + e.getMessage() , HttpStatus.BAD_REQUEST);
        }
    }
    }
     */
    }
