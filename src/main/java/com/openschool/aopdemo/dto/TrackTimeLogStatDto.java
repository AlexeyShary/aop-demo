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
@Schema(description = "Dto for response to full stats by method name request")
public class TrackTimeLogStatDto {
    private String methodName;
    private Long callCount;
    private Long minimumDuration;
    private Long maximumDuration;
    private Double averageDuration;
}