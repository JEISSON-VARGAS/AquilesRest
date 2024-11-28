package com.api.aquilesApi.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainingProjectDTO {

    private Long id;  // ID del proyecto de formación

    private String name;  // Nombre del proyecto de formación

    private String description;  // Descripción del proyecto de formación (si es necesario)

    // Otros posibles campos del proyecto de formación pueden ir aquí
}
