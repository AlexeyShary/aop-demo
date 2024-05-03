package com.openschool.aopdemo.service;

import com.openschool.aopdemo.annotation.TrackAsyncTime;
import com.openschool.aopdemo.annotation.TrackTime;
import com.openschool.aopdemo.dto.DirectorResponseDto;
import com.openschool.aopdemo.dto.FilmNewDto;
import com.openschool.aopdemo.dto.FilmResponseDto;
import com.openschool.aopdemo.model.Director;
import com.openschool.aopdemo.model.Film;
import com.openschool.aopdemo.repository.DirectorRepository;
import com.openschool.aopdemo.repository.FilmRepository;
import com.openschool.aopdemo.util.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FilmService {
    private final FilmRepository filmRepository;
    private final DirectorRepository directorRepository;

    @TrackAsyncTime
    @Transactional(readOnly = true)
    public List<FilmResponseDto> getAll() {
        return filmRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @TrackTime
    @Transactional(readOnly = true)
    public FilmResponseDto getByFilmId(long filmId) {
        return toDto(findFilmById(filmId));
    }

    @TrackAsyncTime
    @Transactional(readOnly = true)
    public List<FilmResponseDto> getAllByDirectorId(long directorId) {
        return filmRepository.findAllByDirectorId(directorId).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @TrackAsyncTime
    @Transactional
    public FilmResponseDto create(FilmNewDto filmNemDto) {
        Film newFilm = new Film();

        newFilm.setName(filmNemDto.getName());
        newFilm.setDescription(filmNemDto.getDescription());
        newFilm.setRating(filmNemDto.getRating());
        newFilm.setDirector(findDirectorById(filmNemDto.getDirectorId()));

        return toDto(filmRepository.save(newFilm));
    }

    @TrackTime
    @Transactional
    public void delete(long filmId) {
        findFilmById(filmId);
        filmRepository.deleteById(filmId);
    }

    private Film findFilmById(long id) {
        return filmRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Film with id=" + id + " was not found"));
    }

    private Director findDirectorById(long id) {
        return directorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Director with id=" + id + " was not found"));
    }

    private FilmResponseDto toDto(Film entity) {
        return FilmResponseDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .rating(entity.getRating())
                .director(DirectorResponseDto.builder()
                        .id(entity.getDirector().getId())
                        .name(entity.getDirector().getName())
                        .birthYear(entity.getDirector().getBirthYear())
                        .build())
                .build();
    }
}
