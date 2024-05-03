package com.openschool.aopdemo.service;

import com.openschool.aopdemo.dto.TrackTimeLogAverageDurationDto;
import com.openschool.aopdemo.dto.TrackTimeLogDto;
import com.openschool.aopdemo.dto.TrackTimeLogStatDto;
import com.openschool.aopdemo.model.TrackTimeLog;
import com.openschool.aopdemo.repository.TrackTimeLogRepository;
import com.openschool.aopdemo.util.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrackTimeLogService {
    private final TrackTimeLogRepository trackTimeLogRepository;

    @Transactional(readOnly = true)
    public List<TrackTimeLogDto> getAll() {
        return trackTimeLogRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<TrackTimeLogDto> getAllByMethodName(String methodName) {
        return trackTimeLogRepository.findAllByMethodName(methodName).stream()
                .map(this::toDto)
                .collect(Collectors.collectingAndThen(Collectors.toList(), list -> {
                    if (list.isEmpty()) {
                        throw new NotFoundException("Records for method with name=" + methodName + "was not found");
                    }
                    return list;
                }));
    }

    @Transactional(readOnly = true)
    public TrackTimeLogAverageDurationDto getAverageDurationByMethodName(String methodName) {
        Double averageDuration = trackTimeLogRepository.findAverageDurationByMethodName(methodName)
                .orElseThrow(() -> new NotFoundException("Records for method with name=" + methodName + " was not found"));

        return TrackTimeLogAverageDurationDto.builder()
                .methodName(methodName)
                .averageDuration(averageDuration)
                .build();
    }

    @Transactional(readOnly = true)
    public TrackTimeLogStatDto getStatByMethodName(String methodName) {
        List<TrackTimeLog> logs = trackTimeLogRepository.findAllByMethodName(methodName);

        if (logs.isEmpty()) {
            throw new NotFoundException("Records for method with name=" + methodName + " was not found");
        }

        Long callCount = (long) logs.size();
        Long minimumDuration = logs.stream().mapToLong(TrackTimeLog::getDuration).min().orElse(0);
        Long maximumDuration = logs.stream().mapToLong(TrackTimeLog::getDuration).max().orElse(0);
        Double averageDuration = logs.stream().mapToLong(TrackTimeLog::getDuration).average().orElse(0);

        return TrackTimeLogStatDto.builder()
                .methodName(methodName)
                .callCount(callCount)
                .minimumDuration(minimumDuration)
                .maximumDuration(maximumDuration)
                .averageDuration(averageDuration)
                .build();
    }

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void create(String methodName, Object[] methodArgs, boolean isAsync, LocalDateTime timestamp, long duration) {
        TrackTimeLog trackTimeLog = new TrackTimeLog();

        trackTimeLog.setMethodName(methodName);
        trackTimeLog.setMethodArgs(methodArgs != null && methodArgs.length > 0 ? Arrays.stream(methodArgs)
                .map(Object::toString)
                .collect(Collectors.joining(", "))
                : null);
        trackTimeLog.setIsAsync(isAsync);
        trackTimeLog.setTimestamp(timestamp);
        trackTimeLog.setDuration(duration);

        trackTimeLogRepository.save(trackTimeLog);
    }

    private TrackTimeLogDto toDto(TrackTimeLog entity) {
        return TrackTimeLogDto.builder()
                .id(entity.getId())
                .methodName(entity.getMethodName())
                .methodArgs(entity.getMethodArgs())
                .isAsync(entity.getIsAsync())
                .timestamp(entity.getTimestamp())
                .duration(entity.getDuration())
                .build();
    }
}