package com.api.aquilesApi.Service;

import com.api.aquilesApi.Entity.LearningOutcome;
import com.api.aquilesApi.Repository.LearningOutcomeRepository;
import com.api.aquilesApi.Service.Dao.Idao;
import com.api.aquilesApi.Utilities.CustomException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class LearningOutcomeService implements Idao<LearningOutcome, Long> {

    @Autowired
    private LearningOutcomeRepository learningOutcomeRepository;

    @Override
    public LearningOutcome getById(Long id) {
        return learningOutcomeRepository.findById(id).orElseThrow(() ->
                new CustomException("Learning Outcome with id " + id + " not found", HttpStatus.NO_CONTENT));
    }

    @Transactional
    @Override
    public void save(LearningOutcome obj) {
        this.learningOutcomeRepository.save(obj);
    }

    @Transactional
    @Override
    public void saveAll(Iterable<LearningOutcome> obj) {
        this.learningOutcomeRepository.saveAll(obj);
    }

    @Transactional
    @Override
    public void delete(LearningOutcome obj) {
        this.learningOutcomeRepository.delete(obj);
    }

    @Override
    public Page<LearningOutcome> findAll(PageRequest pageRequest) {
        return learningOutcomeRepository.findAll(pageRequest);
    }

    public boolean existsCode(Long code) {
        return learningOutcomeRepository.existsByCode(code);
    }

    public boolean existsName(String name) {
        return learningOutcomeRepository.existsByName(name);
    }

    public boolean existsDescription(String description) {
        return learningOutcomeRepository.existsByDescription(description);
    }
}
