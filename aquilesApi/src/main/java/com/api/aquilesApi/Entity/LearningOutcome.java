package com.api.aquilesApi.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(exclude = {"competence", "projectActivities"})
@ToString(exclude = {"competence", "projectActivities"})
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "learning_outcomes")
public class LearningOutcome implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Long code;

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
    @JoinColumn(name = "id_competence")
    private Competence competence;

    @OneToMany(mappedBy = "learningOutcome")
    private List<ProjectActivity> projectActivities;
}
