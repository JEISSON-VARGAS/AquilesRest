package com.api.aquilesApi.Dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhaseDTO {

    private Long id;

    @NotNull(message = "El nombre es obligatorio")
    @Size(max = 50, message = "El nombre no puede exceder los 50 caracteres")
    private String name;

    @NotNull(message = "La descripción es obligatoria")
    @Size(max = 255, message = "La descripción no puede exceder los 255 caracteres")
    private String description;

    @NotNull(message = "El estado es obligatorio")
    private Boolean state;

    private Date createdAt;
    private Date updatedAt;

    // Relaciones
    @NotNull(message = "El proyecto formativo es obligatorio")
    private TrainingProjectDTO trainingProjectDTO;
}
