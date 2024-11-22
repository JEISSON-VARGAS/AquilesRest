package com.api.aquilesApi.Service;

import com.api.aquilesApi.Entity.ProjectActivity;
import com.api.aquilesApi.Repository.ProjectActivityRepository;
import com.api.aquilesApi.Service.Dao.Idao;
import com.api.aquilesApi.Utilities.CustomException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ProjectActivityService implements Idao<ProjectActivity, Long> {

    @Autowired
    private ProjectActivityRepository projectActivityRepository;

    @Override
    public ProjectActivity getById(Long id) {
        return projectActivityRepository.findById(id).orElseThrow(() ->
                new CustomException("La actividad de proyecto con id " + id + " no fue encontrada", HttpStatus.NO_CONTENT));
    }

    @Transactional
    @Override
    public void save(ProjectActivity obje) {
        this.projectActivityRepository.save(obje);
    }

    @Override
    public void saveAll(Iterable<ProjectActivity> obje) {
        this.projectActivityRepository.saveAll(obje);
    }

    @Transactional
    @Override
    public void delete(ProjectActivity obje) {
        this.projectActivityRepository.delete(obje);
    }

    @Override
    public Page<ProjectActivity> findAll(PageRequest pageRequest) {
        return projectActivityRepository.findAll(pageRequest);
    }

    public boolean existsName(String name) {
        return projectActivityRepository.existsByName(name);
    }

    public boolean existsDescription(String description) {
        return projectActivityRepository.existsByDescription(description);
    }
}
