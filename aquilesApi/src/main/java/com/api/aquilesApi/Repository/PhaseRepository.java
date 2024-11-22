package com.api.aquilesApi.Repository;

import com.api.aquilesApi.Entity.Phase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhaseRepository extends JpaRepository<Phase, Long> {
    boolean existsByName(String name);
    boolean existsByDescription(String description);
}
