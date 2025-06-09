import java.sql.*;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String username = "root";
            String password = "yearup";
            String url = "jdbc:mysql://localhost:3306/northwind";
            String query = "SELECT productName FROM products;";

            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                Statement statement = connection.createStatement();
                ResultSet results = statement.executeQuery(query);
                while (results.next()) {
                    String productName = results.getString("ProductName");
                    System.out.println(productName);
                }
            }
    }
}
