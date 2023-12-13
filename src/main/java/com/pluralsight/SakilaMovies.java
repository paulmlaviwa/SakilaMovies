package com.pluralsight;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class SakilaMovies {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            // Ask user for the last name of an actor
            System.out.print("Enter the last name of an actor: ");
            String lastName = scanner.nextLine();

            // Display a list of actors with the given last name
            displayActorsByLastName(lastName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void displayActorsByLastName(String lastName) throws SQLException {
        String sql = "SELECT actor_id, first_name, last_name FROM actor WHERE last_name = ?";
        try (
                Connection connection = getDataSource().getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, lastName);

            // Execute the query and store the result set in a variable
            try (ResultSet resultSet = statement.executeQuery()) {
                // Use the first call to next to see if there are records
                if (resultSet.next()) {
                    System.out.println("Your matches are: \n");
                    System.out.printf("%-15s %-20s %-20s%n", "Actor ID", "First Name", "Last Name");
                    System.out.println("----------------------------------------------------------");

                    // if there are, you are already sitting on the first one, so switch your loop to using a do/while
                    do {
                        // process results
                        int actorId = resultSet.getInt("actor_id");
                        String firstName = resultSet.getString("first_name");
                        lastName = resultSet.getString("last_name");

                        System.out.printf("%-15d %-20s %-20s%n", actorId, firstName, lastName);
                    } while (resultSet.next());
                } else {
                    System.out.println("No matches!");
                }
            }
        }
    }

    private static BasicDataSource getDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/sakila");
        dataSource.setUsername("root");
        dataSource.setPassword("@Cyantist.93_");
        dataSource.setMinIdle(5);
        dataSource.setMaxIdle(10);
        dataSource.setMaxOpenPreparedStatements(100);
        return dataSource;
    }
}
