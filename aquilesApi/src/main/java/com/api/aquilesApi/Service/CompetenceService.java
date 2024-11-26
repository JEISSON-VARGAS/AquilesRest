package com.api.aquilesApi.Service;

import com.api.aquilesApi.Entity.Competence;
import com.api.aquilesApi.Repository.CompetenceRepository;
import com.api.aquilesApi.Service.Dao.Idao;
import com.api.aquilesApi.Utilities.CustomException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class CompetenceService implements Idao<Competence, Long> {

    @Autowired
    private CompetenceRepository competenceRepository;

    @Override
    public Competence getById(Long id) {
        return competenceRepository.findById(id).orElseThrow(() ->
                new CustomException("Competence with id " + id + " not found", HttpStatus.NO_CONTENT));
    }

    @Override
    public void update(Competence entity) {
        // Implementa la lógica de actualización
    }

    @Transactional
    @Override
    public Competence save(Competence obje) {
        return this.competenceRepository.save(obje);
    }

    @Transactional
    @Override
    public void saveAll(Iterable<Competence> obje) {
        this.competenceRepository.saveAll(obje);
    }

    @Transactional
    @Override
    public void delete(Competence obje) {
        this.competenceRepository.delete(obje);
    }

    @Override
    public void create(Competence entity) {
        // Implementa la lógica de creación
    }

    @Override
    public Page<Competence> findAll(PageRequest pageRequest) {
        return competenceRepository.findAll(pageRequest);
    }

    public boolean existsCode(Long code) {
        return competenceRepository.existsByCode(code);
    }

    public boolean existsName(String name) {
        return competenceRepository.existsByName(name);
    }

    public boolean existsDescription(String description) {
        return competenceRepository.existsByDescription(description);
    }
}
