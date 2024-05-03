package com.openschool.aopdemo.controller;

import com.openschool.aopdemo.dto.TrackTimeLogAverageDurationDto;
import com.openschool.aopdemo.dto.TrackTimeLogDto;
import com.openschool.aopdemo.dto.TrackTimeLogStatDto;
import com.openschool.aopdemo.service.TrackTimeLogService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TrackTimeLogController.class)
@DisplayName("TrackTimeLog Controller tests")
class TrackTimeLogControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TrackTimeLogService trackTimeLogService;

    @Test
    @DisplayName("Get all TrackTimeLogs endpoint")
    void getAllTest() throws Exception {
        List<TrackTimeLogDto> trackTimeLogDtoList = Arrays.asList(
                generateTrackTimeLogDto(),
                generateTrackTimeLogDto()
        );

        when(trackTimeLogService.getAll()).thenReturn(trackTimeLogDtoList);

        mockMvc.perform(get("/tracktime"))
                .andExpect(status().isOk());

        verify(trackTimeLogService, times(1)).getAll();
        verifyNoMoreInteractions(trackTimeLogService);
    }

    @Test
    @DisplayName("Get all TrackTimeLogs by method name endpoint")
    void getAllByMethodNameTest() throws Exception {
        List<TrackTimeLogDto> trackTimeLogDtoList = Arrays.asList(
                generateTrackTimeLogDto(),
                generateTrackTimeLogDto()
        );

        when(trackTimeLogService.getAllByMethodName("abc")).thenReturn(trackTimeLogDtoList);

        mockMvc.perform(get("/tracktime/abc"))
                .andExpect(status().isOk());

        verify(trackTimeLogService, times(1)).getAllByMethodName("abc");
        verifyNoMoreInteractions(trackTimeLogService);
    }

    @Test
    @DisplayName("Get average duration by method name endpoint")
    void getAverageDurationByMethodNameTest() throws Exception {
        TrackTimeLogAverageDurationDto dto = generateTrackTimeLogAverageDurationDto();

        when(trackTimeLogService.getAverageDurationByMethodName("abc")).thenReturn(dto);

        mockMvc.perform(get("/tracktime/average/abc"))
                .andExpect(status().isOk());

        verify(trackTimeLogService, times(1)).getAverageDurationByMethodName("abc");
        verifyNoMoreInteractions(trackTimeLogService);
    }

    @Test
    @DisplayName("Get full stats by method name endpoint")
    void getStatByMethodNameTest() throws Exception {
        TrackTimeLogStatDto dto = generateTrackTimeLogStatDto();

        when(trackTimeLogService.getStatByMethodName("abc")).thenReturn(dto);

        mockMvc.perform(get("/tracktime/stat/abc"))
                .andExpect(status().isOk());

        verify(trackTimeLogService, times(1)).getStatByMethodName("abc");
        verifyNoMoreInteractions(trackTimeLogService);
    }

    private TrackTimeLogDto generateTrackTimeLogDto() {
        return TrackTimeLogDto.builder()
                .id(1L)
                .methodName("Method name")
                .isAsync(true)
                .duration(10L)
                .build();
    }

    private TrackTimeLogAverageDurationDto generateTrackTimeLogAverageDurationDto() {
        return TrackTimeLogAverageDurationDto.builder()
                .methodName("Method name")
                .averageDuration(2.5)
                .build();
    }

    private TrackTimeLogStatDto generateTrackTimeLogStatDto() {
        return TrackTimeLogStatDto.builder()
                .methodName("Method name")
                .callCount(100L)
                .minimumDuration(1L)
                .maximumDuration(100L)
                .averageDuration(55.55)
                .build();
    }
}