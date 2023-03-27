package ru.job4j.cinema.repository.repoInterface;

import ru.job4j.cinema.model.Film;

import java.util.Optional;

public interface FilmRepository {

    Optional<Film> findById(int id);

    Optional<Film> findByGenre(int genreId);

    Optional<Film> findByName(String name);

}
