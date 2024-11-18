package com.api.aquilesApi.Service;

import com.api.aquilesApi.Entity.Excuse;
import com.api.aquilesApi.Repository.ExcuseRepository;
import com.api.aquilesApi.Service.Dao.Idao;
import com.api.aquilesApi.Utilities.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ExcuseService implements Idao<Excuse, Long> {

    @Autowired
    private ExcuseRepository excuseRepository;

    @Override
    public Page<Excuse> findAll(PageRequest pageRequest) {
        return excuseRepository.findAll(pageRequest);
    }

    @Override
    public Excuse getById(Long id) {
        return excuseRepository.findById(id).orElseThrow(() ->
                new CustomException("Excuse with id " + id + " not found", HttpStatus.NO_CONTENT));
    }

    @Override
    public void update(Excuse entity) {
        this.excuseRepository.save(entity);
    }

    @Override
    public Excuse save(Excuse entity) {
        return excuseRepository.save(entity);
    }

    @Override
    public void delete(Excuse entity) {
        this.excuseRepository.delete(entity);
    }

    @Override
    public void create(Excuse entity) {
        this.excuseRepository.save(entity);
    }

    public boolean existsByExcuseDocument(String excuseDocument) {
        return excuseRepository.existsByExcuseDocument(excuseDocument);
    }
}
