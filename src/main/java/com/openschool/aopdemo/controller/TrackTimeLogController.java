package com.openschool.aopdemo.controller;

import com.openschool.aopdemo.dto.TrackTimeLogAverageDurationDto;
import com.openschool.aopdemo.dto.TrackTimeLogDto;
import com.openschool.aopdemo.dto.TrackTimeLogStatDto;
import com.openschool.aopdemo.service.TrackTimeLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/tracktime")
@RequiredArgsConstructor
@Tag(name = "TrackTimeLog Controller")
public class TrackTimeLogController {
    private final TrackTimeLogService trackTimeLogService;

    @GetMapping()
    @Operation(summary = "Get all track time logs")
    public List<TrackTimeLogDto> getAll() {
        return trackTimeLogService.getAll();
    }

    @GetMapping("/{methodName}")
    @Operation(summary = "Get all track time logs by method name")
    public List<TrackTimeLogDto> getAllByMethodName(@PathVariable String methodName) {
        return trackTimeLogService.getAllByMethodName(methodName);
    }

    @GetMapping("/average/{methodName}")
    @Operation(summary = "Get average duration by method name")
    public TrackTimeLogAverageDurationDto getAverageDurationByMethodName(@PathVariable String methodName) {
        return trackTimeLogService.getAverageDurationByMethodName(methodName);
    }

    @GetMapping("/stat/{methodName}")
    @Operation(summary = "Get full stats by method name")
    public TrackTimeLogStatDto getStatByMethodName(@PathVariable String methodName) {
        return trackTimeLogService.getStatByMethodName(methodName);
    }
}
