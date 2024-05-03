package com.openschool.aopdemo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openschool.aopdemo.dto.DirectorNewDto;
import com.openschool.aopdemo.dto.DirectorResponseDto;
import com.openschool.aopdemo.service.DirectorService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = DirectorController.class)
@DisplayName("Director Controller tests")
class ProducerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private DirectorService directorService;

    @Test
    @DisplayName("Get all directors endpoint")
    void getAllTest() throws Exception {
        List<DirectorResponseDto> directorResponseDtoList = Arrays.asList(
                generateDirectorResponseDto(1),
                generateDirectorResponseDto(2)
        );

        when(directorService.getAll()).thenReturn(directorResponseDtoList);

        mockMvc.perform(get("/directors"))
                .andExpect(status().isOk());

        verify(directorService, times(1)).getAll();
        verifyNoMoreInteractions(directorService);
    }

    @Test
    @DisplayName("Get director by id endpoint")
    void getByIdTest() throws Exception {
        DirectorResponseDto directorResponseDto = generateDirectorResponseDto(1);

        when(directorService.getById(eq(directorResponseDto.getId()))).thenReturn(directorResponseDto);

        mockMvc.perform(get("/directors/" + directorResponseDto.getId()))
                .andExpect(status().isOk());

        verify(directorService, times(1)).getById(eq(directorResponseDto.getId()));
        verifyNoMoreInteractions(directorService);
    }

    @Test
    @DisplayName("Create director normal behaviour")
    void createOkTest() throws Exception {
        DirectorNewDto directorNewDto = generateDirectorNewDto();
        DirectorResponseDto directorResponseDto = generateDirectorResponseDto(1);

        when(directorService.create(eq(directorNewDto))).thenReturn(directorResponseDto);

        mockMvc.perform(post("/directors")
                        .content(objectMapper.writeValueAsString(directorNewDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        verify(directorService, times(1)).create(eq(directorNewDto));
        verifyNoMoreInteractions(directorService);
    }

    @Test
    @DisplayName("Create director without required field")
    void createFailTest() throws Exception {
        DirectorNewDto directorNewDto = generateDirectorNewDto();
        directorNewDto.setName(null);

        mockMvc.perform(post("/directors")
                        .content(objectMapper.writeValueAsString(directorNewDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verifyNoMoreInteractions(directorService);
    }

    @Test
    @DisplayName("Delete director endpoint")
    public void deleteTest() throws Exception {
        mockMvc.perform(delete("/directors/1"))
                .andExpect(status().isNoContent());

        verify(directorService, times(1)).delete(1);
        verifyNoMoreInteractions(directorService);
    }

    private DirectorNewDto generateDirectorNewDto() {
        return DirectorNewDto.builder()
                .name("Director name")
                .birthYear(1950)
                .build();
    }

    private DirectorResponseDto generateDirectorResponseDto(long id) {
        return DirectorResponseDto.builder()
                .id(1L)
                .name("Director " + id)
                .birthYear((int) (1950 + id))
                .build();
    }
}