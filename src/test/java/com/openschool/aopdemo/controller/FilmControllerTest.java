package com.openschool.aopdemo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openschool.aopdemo.dto.DirectorResponseDto;
import com.openschool.aopdemo.dto.FilmNewDto;
import com.openschool.aopdemo.dto.FilmResponseDto;
import com.openschool.aopdemo.service.FilmService;
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

@WebMvcTest(controllers = FilmController.class)
@DisplayName("Film Controller tests")
class FilmControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private FilmService filmService;

    @Test
    @DisplayName("Get all films endpoint")
    void getAllTest() throws Exception {
        List<FilmResponseDto> filmResponseDtoList = Arrays.asList(
                generateFilmResponseDto(1),
                generateFilmResponseDto(2)
        );

        when(filmService.getAll()).thenReturn(filmResponseDtoList);

        mockMvc.perform(get("/films"))
                .andExpect(status().isOk());

        verify(filmService, times(1)).getAll();
        verifyNoMoreInteractions(filmService);
    }

    @Test
    @DisplayName("Get film by id endpoint")
    void getByFilmIdTest() throws Exception {
        FilmResponseDto filmResponseDto = generateFilmResponseDto(1);

        when(filmService.getByFilmId(eq(filmResponseDto.getId()))).thenReturn(filmResponseDto);

        mockMvc.perform(get("/films/" + filmResponseDto.getId()))
                .andExpect(status().isOk());

        verify(filmService, times(1)).getByFilmId(eq(filmResponseDto.getId()));
        verifyNoMoreInteractions(filmService);
    }

    @Test
    @DisplayName("Get all films by director id endpoint")
    void getAllByDirectorIdTest() throws Exception {
        List<FilmResponseDto> filmResponseDtoList = Arrays.asList(
                generateFilmResponseDto(1),
                generateFilmResponseDto(2)
        );

        when(filmService.getAllByDirectorId(1)).thenReturn(filmResponseDtoList);

        mockMvc.perform(get("/films/director/1"))
                .andExpect(status().isOk());

        verify(filmService, times(1)).getAllByDirectorId(1);
        verifyNoMoreInteractions(filmService);
    }

    @Test
    @DisplayName("Create film normal behaviour")
    void createOkTest() throws Exception {
        FilmNewDto filmNewDto = generateFilmNewDto();
        FilmResponseDto filmResponseDto = generateFilmResponseDto(1);

        when(filmService.create(eq(filmNewDto))).thenReturn(filmResponseDto);

        mockMvc.perform(post("/films")
                        .content(objectMapper.writeValueAsString(filmNewDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        verify(filmService, times(1)).create(eq(filmNewDto));
        verifyNoMoreInteractions(filmService);
    }

    @Test
    @DisplayName("Create film without required field")
    void createFailTest() throws Exception {
        FilmNewDto filmNewDto = generateFilmNewDto();
        filmNewDto.setName(null);

        mockMvc.perform(post("/films")
                        .content(objectMapper.writeValueAsString(filmNewDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verifyNoMoreInteractions(filmService);
    }

    @Test
    @DisplayName("Delete film endpoint")
    public void deleteTest() throws Exception {
        mockMvc.perform(delete("/films/1"))
                .andExpect(status().isNoContent());

        verify(filmService, times(1)).delete(1);
        verifyNoMoreInteractions(filmService);
    }

    private FilmNewDto generateFilmNewDto() {
        return FilmNewDto.builder()
                .name("New film name")
                .description("New film description")
                .directorId(1L)
                .build();
    }

    private FilmResponseDto generateFilmResponseDto(long id) {
        return FilmResponseDto.builder()
                .id(id)
                .name("Name " + id)
                .description("Description " + id)
                .director(DirectorResponseDto.builder()
                        .name("Director " + id)
                        .birthYear((int) (1950 + id))
                        .build())
                .build();
    }
}