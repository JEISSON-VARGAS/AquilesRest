package com.api.aquilesApi.Dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LearningOutcomeDTO {

    private Long id;

    @NotNull(message = "El código es obligatorio")
    private Long code;

    @NotNull(message = "El nombre es obligatorio")
    @Size(max = 50, message = "El nombre no debe exceder los 50 caracteres")
    private String name;

    @NotNull(message = "La descripción es obligatoria")
    @Size(max = 255, message = "La descripción no debe exceder los 255 caracteres")
    private String description;

    @NotNull(message = "El estado es obligatorio")
    private Boolean state;

    private Date createdAt;
    private Date updatedAt;

    // Relaciones
    @NotNull(message = "La competencia es obligatoria")
    private CompetenceDTO competence;
}
