package com.openschool.aopdemo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dto for response to track time log request")
public class TrackTimeLogDto {
    private Long id;
    private String methodName;
    private String methodArgs;
    private Boolean isAsync;
    private LocalDateTime timestamp;
    private Long duration;
}