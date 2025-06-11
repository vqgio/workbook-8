import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final String url = "jdbc:mysql://localhost:3306/sakila?user=root&password=yearup";

    public static void main(String[] args) {
        System.out.println("Good day!");
        boolean keepGoing = true;

        try(BasicDataSource dataSource = new BasicDataSource()) {
            dataSource.setUrl(url);
            Connection connection = dataSource.getConnection();

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

    private static void searchByLastName(Connection connection) {
        System.out.print("Enter actors last name: ");
        String lastName = scanner.nextLine();
        System.out.println("====================================");

        String secureQuery = "SELECT actor_id, first_name, last_name FROM actor WHERE last_name = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(secureQuery)) {
            preparedStatement.setString(1, lastName);
            ResultSet results = preparedStatement.executeQuery();
            //can be better with if do else statement
            while (results.next()) {
                System.out.println("===== Results =====");
                System.out.println("Actor ID: " + results.getInt("actor_id"));
                System.out.println("First Name: " + results.getString("first_name"));
                System.out.println("Last Name: " + results.getString("last_name"));
                System.out.println("------------------------------------");
            }
            System.out.println("====================================");
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
