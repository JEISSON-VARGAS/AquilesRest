package com.api.aquilesApi.Business;

import com.api.aquilesApi.Dto.LearningActivityDTO;
import com.api.aquilesApi.Entity.LearningActivity;
import com.api.aquilesApi.Entity.ProjectActivity;
import com.api.aquilesApi.Service.LearningActivityService;
import com.api.aquilesApi.Service.ProjectActivityService;
import com.api.aquilesApi.Utilities.CustomException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LearningActivityBusiness {

    @Autowired
    private LearningActivityService learningActivityService;

    @Autowired
    private ProjectActivityService projectActivityService;

    private final ModelMapper modelMapper = new ModelMapper();

    public Page<LearningActivityDTO> findAll(int page, int size) {
        try {
            PageRequest pageRequest = PageRequest.of(page, size);
            Page<LearningActivity> learningActivityPage = learningActivityService.findAll(pageRequest);
            if (learningActivityPage.isEmpty()) {
                return Page.empty();
            }
            return learningActivityPage.map(learningActivity -> modelMapper.map(learningActivity, LearningActivityDTO.class));
        } catch (Exception e) {
            throw new CustomException("Error getting Learning Activities", HttpStatus.NOT_FOUND);
        }
    }

    public List<LearningActivityDTO> findById(Long id) {
        List<LearningActivityDTO> learningActivityDTOList = new ArrayList<>();
        try {
            LearningActivity learningActivity = learningActivityService.getById(id);
            if (learningActivity != null) {
                learningActivityDTOList.add(modelMapper.map(learningActivity, LearningActivityDTO.class));
                return learningActivityDTOList;
            } else {
                throw new CustomException("Learning Activity not found", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            throw new CustomException("Error getting Learning Activity", HttpStatus.NOT_FOUND);
        }
    }

    public Boolean add(LearningActivityDTO learningActivityDTO) {
        try {
            LearningActivity learningActivity = modelMapper.map(learningActivityDTO, LearningActivity.class);

            // Vincular la actividad de proyecto
            ProjectActivity projectActivity = projectActivityService.getById(learningActivityDTO.getProjectActivity().getId());
            learningActivity.setProjectActivity(projectActivity);

            learningActivityService.save(learningActivity);
            return true;
        } catch (Exception e) {
            throw new CustomException("Error adding Learning Activity", HttpStatus.BAD_REQUEST);
        }
    }

    public Boolean update(LearningActivityDTO learningActivityDTO, Long id) {
        try {
            LearningActivity existingActivity = learningActivityService.getById(id);
            if (existingActivity != null) {
                LearningActivity learningActivity = modelMapper.map(learningActivityDTO, LearningActivity.class);
                learningActivity.setId(id);

                // Vincular la actividad de proyecto
                ProjectActivity projectActivity = projectActivityService.getById(learningActivityDTO.getProjectActivity().getId());
                learningActivity.setProjectActivity(projectActivity);

                learningActivityService.save(learningActivity);
                return true;
            } else {
                throw new CustomException("Learning Activity with id " + id + " not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            throw new CustomException("Error updating Learning Activity", HttpStatus.BAD_REQUEST);
        }
    }

    public Boolean delete(Long id) {
        try {
            LearningActivity learningActivity = learningActivityService.getById(id);
            if (learningActivity != null) {
                learningActivityService.delete(learningActivity);
                return true;
            } else {
                throw new CustomException("Learning Activity with id " + id + " not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            throw new CustomException("Error deleting Learning Activity", HttpStatus.BAD_REQUEST);
        }
    }
}
