package com.api.aquilesApi.Service;

import com.api.aquilesApi.Entity.ExcusesEntity;
import com.api.aquilesApi.Repository.ExcusesRepository;
import com.api.aquilesApi.Service.Dao.Idao;
import com.api.aquilesApi.Utilities.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExcusesService implements Idao<ExcusesEntity , Long> {
    @Autowired
    private ExcusesRepository excusesRepository;

    @Override
    public Page<ExcusesEntity> findAll(PageRequest pageRequest) {
        return excusesRepository.findAll(pageRequest);
    }

    @Override
    public ExcusesEntity getById(Long id) {
        return excusesRepository.findById(id).orElseThrow(() ->
                new CustomException("Excuse with id " + id + " not found", HttpStatus.NO_CONTENT));
    }

    @Override
    public void update(ExcusesEntity entity) {
        this.excusesRepository.save(entity);
    }

    @Override
    public ExcusesEntity save(ExcusesEntity entity) {
        return excusesRepository.save(entity);
    }

    @Override
    public void delete(ExcusesEntity entity) {
        this.excusesRepository.delete(entity);
    }

    @Override
    public void create(ExcusesEntity entity) {
        this.excusesRepository.save(entity);
    }

    public boolean existsByExcuseDocument(String excuseDocument) {
        return excusesRepository.existsByExcuseDocument(excuseDocument);
    }
}
