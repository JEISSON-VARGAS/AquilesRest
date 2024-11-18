package com.api.aquilesApi.Repository;

import com.api.aquilesApi.Entity.Excuse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExcuseRepository extends JpaRepository<Excuse, Long> {
    boolean existsByExcuseDocument(String excuseDocument);
}
