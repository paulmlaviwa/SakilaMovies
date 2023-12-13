package com.pluralsight;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class SakilaMovies {
    public static void main(String[] args) {
        BasicDataSource dataSource = getDataSource();
        DataManager dataManager = new DataManager(dataSource);

        try (Scanner scanner = new Scanner(System.in)) {
            // Ask user for the last name of an actor
            System.out.print("Enter the last name of an actor: ");
            String lastName = scanner.nextLine();

            // Search for actors by name and display the results
            List<Actor> actors = dataManager.searchActorsByName(lastName);
            System.out.println("Search results for actors:");
            for (Actor actor : actors) {
                System.out.println(actor);
            }

            // Ask user to enter an actor id
            System.out.print("Enter an actor ID to see their movies: ");
            int actorId = scanner.nextInt();

            // Search for and display a list of movies that the actor has appeared in
            List<Film> films = dataManager.getFilmsByActorId(actorId);
            System.out.println("\nMovies for actor with ID " + actorId + ":");
            for (Film film : films) {
                System.out.println(film);
            }

        }
    }

    private static BasicDataSource getDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/sakila");
        dataSource.setUsername("root");
        dataSource.setPassword("************");
        dataSource.setMinIdle(5);
        dataSource.setMaxIdle(10);
        dataSource.setMaxOpenPreparedStatements(100);
        return dataSource;
    }
}