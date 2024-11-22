package com.api.aquilesApi.Dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LearningActivityDTO {

    private Long id;

    @NotNull(message = "El nombre de la actividad es obligatorio")
    @Size(max = 50, message = "El nombre de la actividad no puede exceder los 50 caracteres")
    private String name;

    @NotNull(message = "La descripción de la actividad es obligatoria")
    @Size(max = 255, message = "La descripción de la actividad no puede exceder los 255 caracteres")
    private String description;

    @NotNull(message = "El estado es obligatorio")
    private Boolean state;

    private Date createdAt;
    private Date updatedAt;

    // Relaciones
    @NotNull(message = "La actividad de proyecto es obligatoria")
    private ProjectActivityDTO projectActivity;
}
