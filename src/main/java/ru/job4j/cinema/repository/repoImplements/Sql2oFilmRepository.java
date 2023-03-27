package ru.job4j.cinema.repository.repoImplements;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;
import ru.job4j.cinema.model.Film;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.repository.repoInterface.FilmRepository;

import java.util.Optional;

@Slf4j
@Primary
@Repository
public class Sql2oFilmRepository implements FilmRepository {

    private final Sql2o sql2o;

    public Sql2oFilmRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public Optional<Film> findById(int id) {
        try(var connection = sql2o.open()){
            var sql = "SELECT * FROM films WHERE id = :id";
            var query = connection.createQuery(sql)
                    .addParameter("id", id);
            var film = query.setColumnMappings(Film.COLUMN_MAPPING).executeAndFetchFirst(Film.class);
            return Optional.ofNullable(film);
        }catch (Exception e){
            log.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Film> findByGenre(int genreId) {
        try(var connection = sql2o.open()){
            var sql = "SELECT * FROM films WHERE genre_id = :genreId";
            var query = connection.createQuery(sql)
                    .addParameter("genre_id", genreId);
            var film = query.setColumnMappings(Film.COLUMN_MAPPING).executeAndFetchFirst(Film.class);
            return Optional.ofNullable(film);
        }catch (Exception e){
            log.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Film> findByName(String name) {
        try(var connection = sql2o.open()){
            var sql = "SELECT * FROM films WHERE name = :name";
            var query = connection.createQuery(sql)
                    .addParameter("name", name);
            var film = query.setColumnMappings(Film.COLUMN_MAPPING).executeAndFetchFirst(Film.class);
            return Optional.ofNullable(film);
        }catch (Exception e){
            log.error(e.getMessage(), e);
        }
        return Optional.empty();
    }
}
