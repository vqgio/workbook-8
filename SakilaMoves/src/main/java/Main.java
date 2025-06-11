import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        System.out.println("Good day!");
        System.out.println("what is the last name of your favorite actor?: ");
        String userInput = scanner.nextLine().trim();
        String url = "jdbc:mysql://localhost:3306/sakila?user=root&password=yearup";

        try(BasicDataSource dataSource = new BasicDataSource()) {
            dataSource.setUrl(url);
            Connection connection = dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
