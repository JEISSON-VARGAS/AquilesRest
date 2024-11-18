/* package com.api.aquilesApi.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "justifications")
public class JustificationEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "justification_id", nullable = false)
    private Long justificationId;

    @Column(name = "justification_description", nullable = false, length = 100)
    private String justificationDescription;

    @Column(name = "justification_document", nullable = false, length = 100)
    private String justificationDocument;

    // Relación OneToMany con AttendancesEntity (puedes modificar el mappedBy según lo necesites)
    @OneToMany(mappedBy = "justification", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<AttendancesEntity> attendanceSet;

    // Puedes agregar otros campos si es necesario.
}
*/