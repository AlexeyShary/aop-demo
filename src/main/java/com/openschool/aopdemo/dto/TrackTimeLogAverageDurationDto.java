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
@Schema(description = "Dto for response to average duration by method name request")
public class TrackTimeLogAverageDurationDto {
    private String methodName;
    private Double averageDuration;
}