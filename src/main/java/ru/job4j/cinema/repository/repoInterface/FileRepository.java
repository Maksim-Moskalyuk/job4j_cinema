package ru.job4j.cinema.repository.repoInterface;

import ru.job4j.cinema.model.File;

import java.util.Optional;

public interface FileRepository {

    Optional<File> save(File file);

    Optional<File> findById(int id);

    boolean deleteById(int id);

}
