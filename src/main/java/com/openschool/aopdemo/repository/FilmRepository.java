package com.openschool.aopdemo.repository;

import com.openschool.aopdemo.model.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilmRepository extends JpaRepository<Film, Long> {
    List<Film> findAllByDirectorId(Long directorId);
}
