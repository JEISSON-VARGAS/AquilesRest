package com.api.aquilesApi.Repository;


import com.api.aquilesApi.Entity.ProjectActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectActivityRepository extends JpaRepository<ProjectActivity, Long> {
    boolean existsByName(String name);
    boolean existsByDescription(String description);
}
