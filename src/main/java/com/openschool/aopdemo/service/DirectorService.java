package com.openschool.aopdemo.service;

import com.openschool.aopdemo.annotation.TrackAsyncTime;
import com.openschool.aopdemo.annotation.TrackTime;
import com.openschool.aopdemo.dto.DirectorNewDto;
import com.openschool.aopdemo.dto.DirectorResponseDto;
import com.openschool.aopdemo.model.Director;
import com.openschool.aopdemo.repository.DirectorRepository;
import com.openschool.aopdemo.util.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DirectorService {
    private final DirectorRepository directorRepository;

    @TrackAsyncTime
    @Transactional(readOnly = true)
    public List<DirectorResponseDto> getAll() {
        return directorRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @TrackTime
    @Transactional(readOnly = true)
    public DirectorResponseDto getById(long producerId) {
        return toDto(findDirectorById(producerId));
    }

    @TrackAsyncTime
    @Transactional
    public DirectorResponseDto create(DirectorNewDto producerNewDto) {
        Director newDirector = new Director();

        newDirector.setName(producerNewDto.getName());
        newDirector.setBirthYear(producerNewDto.getBirthYear());

        return toDto(directorRepository.save(newDirector));
    }

    @TrackTime
    @Transactional
    public void delete(long producerId) {
        findDirectorById(producerId);
        directorRepository.deleteById(producerId);
    }

    private Director findDirectorById(long id) {
        return directorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Director with id=" + id + " was not found"));
    }

    private DirectorResponseDto toDto(Director entity) {
        return DirectorResponseDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .birthYear(entity.getBirthYear())
                .build();
    }
}