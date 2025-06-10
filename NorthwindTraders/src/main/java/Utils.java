import java.sql.*;

public class Utils {
    private static void secureConnection() throws ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String username = "root";
        String password = "yearup";
        String url = "jdbc:mysql://localhost:3306/northwind";

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}