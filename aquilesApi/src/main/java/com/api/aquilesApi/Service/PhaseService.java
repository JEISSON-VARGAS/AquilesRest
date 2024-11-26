package com.api.aquilesApi.Service;

import com.api.aquilesApi.Entity.Phase;
import com.api.aquilesApi.Repository.PhaseRepository;
import com.api.aquilesApi.Service.Dao.Idao;
import  com.api.aquilesApi.Utilities.CustomException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class PhaseService implements Idao<Phase, Long> {

    @Autowired
    private PhaseRepository phaseRepository;

    @Override
    public Phase getById(Long id) {
        return phaseRepository.findById(id).orElseThrow(() ->
                new CustomException("Phase with id " + id + " not found", HttpStatus.NO_CONTENT));
    }

    @Override
    public void update(Phase entity) {

    }

    @Transactional
    @Override
    public Phase save(Phase obje) {
        this.phaseRepository.save(obje);
        return obje;
    }

    @Override
    public void saveAll(Iterable<Phase> obje) {
        this.phaseRepository.saveAll(obje);
    }

    @Transactional
    @Override
    public void delete(Phase obje) {
        this.phaseRepository.delete(obje);
    }

    @Override
    public void create(Phase entity) {

    }

    @Override
    public Page<Phase> findAll(PageRequest pageRequest) {
        return phaseRepository.findAll(pageRequest);
    }

    public boolean existsName(String name) {
        return phaseRepository.existsByName(name);
    }

    public boolean existsDescription(String description) {
        return phaseRepository.existsByDescription(description);
    }
}
