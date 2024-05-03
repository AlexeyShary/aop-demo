package com.openschool.aopdemo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dto for creating new film")
public class FilmNewDto {
    @NotBlank(message = "Name must be not blank")
    private String name;
    @NotBlank(message = "Description must be not blank")
    private String description;
    @PositiveOrZero(message = "Rating must be positive or zero")
    private Double rating;
    @NotNull(message = "Director must be selected")
    private Long directorId;
}