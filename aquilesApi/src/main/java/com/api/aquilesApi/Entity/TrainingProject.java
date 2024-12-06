package com.api.aquilesApi.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "training_projects")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainingProject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "description", nullable = false, length = 255)
    private String description;

    @Column(name = "state", nullable = false)
    private Boolean state;

    @OneToMany(mappedBy = "trainingProject")
    private List<ProjectActivity> projectActivities; // Relación con las actividades de proyecto

    @OneToMany(mappedBy = "trainingProject")
    private List<LearningOutcome> learningOutcomes; // Relación con los resultados de aprendizaje (si corresponde)

    // Métodos adicionales si es necesario
}
