import java.sql.*;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
// 1. open a connection to the database
// use the database URL to point to the correct database
        Connection connection;
        String username = "root";
        String password = "yearup";
        connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/sakila",
                username,
                password);
// create statement
// the statement is tied to the open connection
    String query = "SELECT * FROM actor;";

    Statement statement = connection.createStatement();

    ResultSet results = statement.executeQuery(query);

    while (results.next()) {
        String firstName = results.getString("first_name");
        System.out.println(firstName);
    }

    connection.close();

    }
}
