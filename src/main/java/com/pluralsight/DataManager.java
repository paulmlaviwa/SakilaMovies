package com.pluralsight;

import com.pluralsight.Actor;
import com.pluralsight.Film;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataManager {
    private final DataSource dataSource;

    public DataManager(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Actor> searchActorsByName(String lastName) {
        List<Actor> actors = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM actor WHERE last_name = ?");
        ) {
            statement.setString(1, lastName);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Actor actor = new Actor();
                    actor.setActorId(resultSet.getInt("actor_id"));
                    actor.setFirstName(resultSet.getString("first_name"));
                    actor.setLastName(resultSet.getString("last_name"));

                    actors.add(actor);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return actors;
    }

    public List<Film> getFilmsByActorId(int actorId) {
        List<Film> films = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM film JOIN film_actor ON film.film_id = film_actor.film_id WHERE actor_id = ?");
        ) {
            statement.setInt(1, actorId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Film film = new Film();
                    film.setFilmId(resultSet.getInt("film_id"));
                    film.setTitle(resultSet.getString("title"));
                    // Set other film properties

                    films.add(film);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return films;
    }
}
