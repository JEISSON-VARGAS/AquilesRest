package com.api.aquilesApi.Dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompetenceDTO {

    private Long id;

    @NotNull(message = "El código es obligatorio")
    private Long code;

    @NotNull(message = "El nombre es obligatorio")
    @Size(max = 50, message = "El nombre debe tener un máximo de 50 caracteres")
    private String name;

    @NotNull(message = "La descripción es obligatoria")
    @Size(max = 255, message = "La descripción debe tener un máximo de 255 caracteres")
    private String description;

    @NotNull(message = "El estado es obligatorio")
    private Boolean state;

    private Date createdAt;
    private Date updatedAt;

    // Relaciones
    @JsonIgnoreProperties({"competences"})
    private List<PhaseDTO> phases;
}
