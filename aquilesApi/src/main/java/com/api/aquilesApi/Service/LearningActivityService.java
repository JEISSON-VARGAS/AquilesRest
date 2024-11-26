package com.api.aquilesApi.Service;

import com.api.aquilesApi.Entity.LearningActivity;
import com.api.aquilesApi.Repository.LearningActivityRepository;
import com.api.aquilesApi.Service.Dao.Idao;
import com.api.aquilesApi.Utilities.CustomException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class LearningActivityService implements Idao<LearningActivity, Long> {

    @Autowired
    private LearningActivityRepository learningActivityRepository;

    /**
     * Obtiene una actividad de aprendizaje por su ID.
     *
     * @param id Identificador de la actividad de aprendizaje.
     * @return La actividad de aprendizaje encontrada.
     * @throws CustomException Si no se encuentra la actividad.
     */
    @Override
    public LearningActivity getById(Long id) {
        return learningActivityRepository.findById(id).orElseThrow(() ->
                new CustomException("Learning Activity with id " + id + " not found", HttpStatus.NO_CONTENT));
    }

    @Override
    public void update(LearningActivity entity) {

    }

    /**
     * Guarda una actividad de aprendizaje.
     *
     * @param obje La actividad de aprendizaje a guardar.
     */
    @Override
    public LearningActivity save(LearningActivity obje) {
        return this.learningActivityRepository.save(obje);
    }

    /**
     * Guarda una colección de actividades de aprendizaje.
     *
     * @param obje Iterable de actividades a guardar.
     */
    @Override
    public void saveAll(Iterable<LearningActivity> obje) {
        this.learningActivityRepository.saveAll(obje);
    }

    /**
     * Elimina una actividad de aprendizaje.
     *
     * @param obje La actividad de aprendizaje a eliminar.
     */
    @Transactional
    @Override
    public void delete(LearningActivity obje) {
        this.learningActivityRepository.delete(obje);
    }

    @Override
    public void create(LearningActivity entity) {

    }

    /**
     * Obtiene una página de actividades de aprendizaje.
     *
     * @param pageRequest Información de paginación.
     * @return Página de actividades de aprendizaje.
     */
    @Override
    public Page<LearningActivity> findAll(PageRequest pageRequest) {
        return learningActivityRepository.findAll(pageRequest);
    }

    /**
     * Verifica si existe una actividad de aprendizaje con un nombre específico.
     *
     * @param name Nombre de la actividad de aprendizaje.
     * @return true si existe, de lo contrario false.
     */
    public boolean existsName(String name) {
        return learningActivityRepository.existsByName(name);
    }

    /**
     * Verifica si existe una actividad de aprendizaje con una descripción específica.
     *
     * @param description Descripción de la actividad de aprendizaje.
     * @return true si existe, de lo contrario false.
     */
    public boolean existsDescription(String description) {
        return learningActivityRepository.existsByDescription(description);
    }
}
