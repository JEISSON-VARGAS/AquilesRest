package com.api.aquilesApi.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@EqualsAndHashCode(exclude = {"trainingProject", "competences"})
@ToString(exclude = {"trainingProject", "competences"})
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "phases")
public class Phase implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50, unique = true)
    private String name;

    @Column(nullable = false, length = 255, unique = true)
    private String description;

    @Column(nullable = false, columnDefinition = "boolean default true")
    private Boolean state;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", updatable = false)
    private Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt;

    // Relaciones
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_training_project")
    private TrainingProject trainingProject;

    @ManyToMany
    @JoinTable(
            name = "phase_competence",
            joinColumns = @JoinColumn(name = "id_phase", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "id_competence", nullable = false)
    )
    private List<Competence> competences;
}
