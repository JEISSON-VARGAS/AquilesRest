package com.api.aquilesApi.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExcuseDTO {
    private Long excuseId;
    private String excuseDescription;
    private String excuseDocument;
}
