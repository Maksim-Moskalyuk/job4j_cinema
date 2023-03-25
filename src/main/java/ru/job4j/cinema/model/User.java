package ru.job4j.cinema.model;

import lombok.*;
import lombok.Getter;

import java.util.Map;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private int id;
    private String email;
    private String fullName;
    private String password;

    public static final Map<String, String> COLUMN_MAPPING = Map.of(
            "id", "id",
            "email", "email",
            "full_name", "fullName",
            "password", "password"
    );

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return id == user.id && Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email);
    }
}