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
@Schema(description = "Dto for response to director request")
public class DirectorResponseDto {
    private Long id;
    private String name;
    private Integer birthYear;
}
