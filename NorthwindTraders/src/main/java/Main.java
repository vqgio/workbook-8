import java.sql.*;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String username = "root";
            String password = "yearup";
            String url = "jdbc:mysql://localhost:3306/northwind";
//            String secureQuery = "SELECT * FROM FILM WHERE title = ?";
//            String secureQuery = "SELECT * FROM FILM WHERE ? or film_id = ?";
            String query = "SELECT productName, ProductID, UnitPrice, UnitsInStock FROM products;"; //WHERE = ?

            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                Statement statement = connection.createStatement();
                ResultSet results = statement.executeQuery(query);
//                PreparedStatement preparedStatement = connection.prepareStatement(SecureQuery);
//                PreparedStatement.setString(1, userInput);
//                PreparedStatement.setInt(2, filmId);
//                ResultSet rs = PreparedStatement.executeQuery();
                while (results.next()) {
                    String productName = results.getString("ProductName");
                    String productId = results.getString("ProductID");
                    String unitInStock = results.getString("UnitsInStock");
                    String unitPrice = results.getString("UnitPrice");
                    System.out.println(productName);
                    System.out.println(productId);
                    System.out.println(unitPrice);
                    System.out.println(unitInStock);
//                    System.out.println("Title: " + rs.getString("title"));
                }
            }
    }
}
