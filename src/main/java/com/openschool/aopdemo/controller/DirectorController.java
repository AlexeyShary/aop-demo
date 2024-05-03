package com.openschool.aopdemo.controller;

import com.openschool.aopdemo.dto.DirectorNewDto;
import com.openschool.aopdemo.dto.DirectorResponseDto;
import com.openschool.aopdemo.service.DirectorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/directors")
@RequiredArgsConstructor
@Tag(name = "Director Controller")
public class DirectorController {
    private final DirectorService directorService;

    @GetMapping()
    @Operation(summary = "Get all directors")
    public List<DirectorResponseDto> getAll() {
        return directorService.getAll();
    }

    @GetMapping("/{directorId}")
    @Operation(summary = "Get director by id")
    public DirectorResponseDto getById(@PathVariable long directorId) {
        return directorService.getById(directorId);
    }

    @PostMapping
    @Operation(summary = "Create director")
    @ResponseStatus(HttpStatus.CREATED)
    public DirectorResponseDto create(@Valid @RequestBody DirectorNewDto directorNewDto) {
        return directorService.create(directorNewDto);
    }

    @DeleteMapping("/{directorId}")
    @Operation(summary = "Delete director")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long directorId) {
        directorService.delete(directorId);
    }
}
