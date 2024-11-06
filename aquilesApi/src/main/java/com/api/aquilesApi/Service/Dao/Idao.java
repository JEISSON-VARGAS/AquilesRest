package com.api.aquilesApi.Service.Dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface Idao<T, ID>{
    // Método para obtener o retornar una lista de todas las instancias de una entidad en la base de datos.
    // Para obtener una lista de todas las entidades con paginación
    Page<T> findAll(PageRequest pageable);

    // Método para obtener una instancia específica de la entidad basada en un identificador único.
    T getById(ID id);

    // Método para actualizar una instancia existente de la entidad en la base de datos.
    void update(T entity);

    // Método para guardar una instancia de la entidad en la base de datos, ya sea creando una nueva entrada o actualizando una existente, devuelve la entidad guardada
    T save(T entity);

    // Método para eliminar una instancia existente de la entidad de la base de datos.
    void delete(T entity);

    // Método para crear una nueva instancia de la entidad en la base de datos (puede ser redundante con save dependiendo del uso).
    void create(T entity);
}
