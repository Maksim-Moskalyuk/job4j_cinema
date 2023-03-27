package ru.job4j.cinema.repository.repoImplements;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.repository.repoInterface.UserRepository;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Slf4j
@Primary
@Repository
public class Sql2oUserRepository implements UserRepository {

    private final Sql2o sql2o;

    public Sql2oUserRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public Optional<User> save(User user) {
        try (var connectionn = sql2o.open()) {
            var sql = "INSERT INTO users (full_name, email, password) VALUES (:fullName, :email, :password)";
            var query = connectionn.createQuery(sql, true)
                    .addParameter("email", user.getEmail())
                    .addParameter("fullName", user.getFullName())
                    .addParameter("password", user.getPassword());
            int generateId = query.executeUpdate().getKey(Integer.class);
            user.setId(generateId);
            return Optional.ofNullable(user);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findByEmailAndPassword(String email, String password) {
        try(var connection = sql2o.open()){
            var sql = "SELECT * FROM users WHERE email = :email AND password = :password";
            var query = connection.createQuery(sql)
                    .addParameter("email", email)
                    .addParameter("password", password);
            var user = query.executeAndFetchFirst(User.class);
            return Optional.ofNullable(user);
        }catch (Exception e){
            log.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        try(var connection = sql2o.open()){
            var sql = "SELECT * FROM users WHERE email = :email";
            var query = connection.createQuery(sql)
                    .addParameter("email", email);
            var user = query.setColumnMappings(User.COLUMN_MAPPING).executeAndFetchFirst(User.class);
            return Optional.ofNullable(user);
        }catch (Exception e){
            log.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public Collection<User> findAll() {
        try(var connection = sql2o.open()){
            var sql = "SELECT * FROM users";
            var list = connection.createQuery(sql).setColumnMappings(User.COLUMN_MAPPING).executeAndFetch(User.class);
            return list;
        }catch (Exception e){
            log.error(e.getMessage(), e);
        }
        return Collections.EMPTY_LIST;
    }
}
