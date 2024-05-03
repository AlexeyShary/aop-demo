package com.openschool.aopdemo.controller;

import com.openschool.aopdemo.dto.FilmNewDto;
import com.openschool.aopdemo.dto.FilmResponseDto;
import com.openschool.aopdemo.service.FilmService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/films")
@RequiredArgsConstructor
@Tag(name = "Film Controller")
public class FilmController {
    private final FilmService filmService;

    @GetMapping()
    @Operation(summary = "Get all films")
    public List<FilmResponseDto> getAll() {
        return filmService.getAll();
    }

    @GetMapping("/{filmId}")
    @Operation(summary = "Get film by id")
    public FilmResponseDto getByFilmId(@PathVariable Long filmId) {
        return filmService.getByFilmId(filmId);
    }

    @GetMapping("/director/{directorId}")
    @Operation(summary = "Get all films by director id")
    public List<FilmResponseDto> getAllByDirectorId(@PathVariable Long directorId) {
        return filmService.getAllByDirectorId(directorId);
    }

    @PostMapping
    @Operation(summary = "Create film")
    @ResponseStatus(HttpStatus.CREATED)
    public FilmResponseDto create(@Valid @RequestBody FilmNewDto filmNemDto) {
        return filmService.create(filmNemDto);
    }

    @DeleteMapping("/{filmId}")
    @Operation(summary = "Delete film")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long filmId) {
        filmService.delete(filmId);
    }
}
