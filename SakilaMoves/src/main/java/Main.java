import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    static final Scanner scanner = new Scanner(System.in);
    private static final String url = "jdbc:mysql://localhost:3306/sakila?user=root&password=yearup";

    public static void main(String[] args) {
        System.out.println("Good day!");
        boolean keepGoing = true;

        try(BasicDataSource dataSource = new BasicDataSource()) {
            dataSource.setUrl(url);
            Connection connection = dataSource.getConnection();

            DataManager dataManager = new DataManager(dataSource);

            while (keepGoing) {
                System.out.println("What would you like to do today?");
                System.out.println("1) Search actor by last name");
                System.out.println("2) Find movies certain actor featured in");
                System.out.println("0) Exit");
                System.out.print("Choose an option: ");
                int userInput = Integer.parseInt(scanner.nextLine());
                System.out.println("====================================");
                switch(userInput) {
                    case 1 -> searchByLastName(connection);
                    case 2 -> searchByFullName(connection);
                    case 0 -> {
                        keepGoing = false;
                        System.out.println("Goodbye!....");
                    }
                    default -> System.out.println("Please choose a valid option!");
                }
            }
        } catch (SQLException e) {
            System.out.println("Connection error!!!");
            throw new RuntimeException(e);
        }
    }

    private static void searchByLastName(Connection connection) throws SQLException {
        System.out.println("Enter actor last name: ");
        String lastName = scanner.nextLine();
        
        String query = "SELECT actor_id, first_name, last_name FROM actor WHERE last_name = ?";
        List<Actor> actors = new ArrayList<>();
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, lastName);
            ResultSet results = preparedStatement.executeQuery();
            
            while (results.next()) {
                Actor actor = new Actor(
                    results.getInt("actor_id"),
                    results.getString("first_name"),
                    results.getString("last_name")
                );
                actors.add(actor);
            }
        }
        
        if (actors.isEmpty()) {
            System.out.println("No actors found.");
            System.out.println("====================================");
        } else {
            System.out.println("===== Results =====");
            actors.forEach(System.out::println);
        }
    }

    private static void searchByFullName(Connection connection) {
        System.out.println("Enter actor first name: ");
        String firstName = scanner.nextLine();
        System.out.println("Enter actor last name: ");
        String lastName = scanner.nextLine();
        String secureQuery = """
            SELECT f.title
            FROM film f
            JOIN film_actor fa ON f.film_id = fa.film_id
            JOIN actor a ON fa.actor_id = a.actor_id
            WHERE a.first_name = ? AND a.last_name = ?
        """;
        try (PreparedStatement preparedStatement = connection.prepareStatement(secureQuery)) {
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            ResultSet results = preparedStatement.executeQuery();

            if (results.next()) {
                System.out.println("Movies featuring: " + firstName + " " + lastName);
                do {
                    System.out.println("Movie title: " + results.getString("title"));
                } while (results.next());
            } else {
                System.out.println("No films where found where actor " + firstName + " " + lastName + " features in.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}