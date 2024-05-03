package com.openschool.aopdemo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dto for response to film request")
public class FilmResponseDto {
    private Long id;
    private String name;
    private String description;
    private Double rating;
    private DirectorResponseDto director;
}
