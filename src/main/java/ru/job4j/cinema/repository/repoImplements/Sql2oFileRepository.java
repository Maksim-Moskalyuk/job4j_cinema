package ru.job4j.cinema.repository.repoImplements;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;
import ru.job4j.cinema.model.File;
import ru.job4j.cinema.repository.repoInterface.FileRepository;

import java.util.Optional;

@Slf4j
@Primary
@Repository
public class Sql2oFileRepository implements FileRepository {

    private final Sql2o sql2o;

    public Sql2oFileRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public Optional<File> save(File file) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("INSERT INTO files (name, path) VALUES (:name, :path)", true)
                    .addParameter("name", file.getName())
                    .addParameter("path", file.getPath());
            int generatedId = query.executeUpdate().getKey(Integer.class);
            file.setId(generatedId);
            return Optional.ofNullable(file);
        }catch (Exception e){
            log.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<File> findById(int id) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("SELECT * FROM files WHERE id = :id");
            var file = query.addParameter("id", id).executeAndFetchFirst(File.class);
            return Optional.ofNullable(file);
        }catch (Exception e){
            log.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public boolean deleteById(int id) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("DELETE FROM files WHERE id = :id");
            var affectedRows = query.addParameter("id", id).executeUpdate().getResult();
            return affectedRows > 0;
        }
    }

}