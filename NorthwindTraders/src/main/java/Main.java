import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException {
        boolean keepGoing = true;
        Scanner scanner = new Scanner(System.in);
        while (keepGoing) {

            System.out.println("What do you want to do?");
            System.out.println("1) Display all products");
            System.out.println("2) Display all customers");
            System.out.println("0) Exit");
            System.out.print("Choose an option: ");
            int userInput = Integer.parseInt(scanner.nextLine());

            switch (userInput) {
                case 1:
                    displayAllProducts();
                    break;
                case 2:
                    displayAllCustomers();
                    break;
                case 0:
                    keepGoing = false;
                    System.out.println("Exiting...have a nice day");
                    break;
                default:
                    System.out.println("pick only from the following options, please try again.");
            }
        }


//            String secureQuery = "SELECT * FROM FILM WHERE title = ?";
//            String secureQuery = "SELECT * FROM FILM WHERE ? or film_id = ?";
//        String query = "SELECT productName, ProductID, UnitPrice, UnitsInStock FROM products;"; //WHERE = ?
//
//
//        try (Connection connection = DriverManager.getConnection(url, username, password)) {
//            Statement statement = connection.createStatement();
//            ResultSet results = statement.executeQuery(query);
////                PreparedStatement preparedStatement = connection.prepareStatement(SecureQuery);
////                PreparedStatement.setString(1, userInput);
////                PreparedStatement.setInt(2, filmId);
////                ResultSet rs = PreparedStatement.executeQuery();
//            while (results.next()) {
//                String productName = results.getString("ProductName");
//                String productId = results.getString("ProductID");
//                String unitInStock = results.getString("UnitsInStock");
//                String unitPrice = results.getString("UnitPrice");
//                System.out.println(productName);
//                System.out.println(productId);
//                System.out.println(unitPrice);
//                System.out.println(unitInStock);
////                    System.out.println("Title: " + rs.getString("title"));
//            }
//        }
    }

    private static void displayAllCustomers() throws ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String username = "root";
        String password = "yearup";
        String url = "jdbc:mysql://localhost:3306/northwind";
        String secureQuery = "SELECT ContactName, companyName, City, Country, Phone FROM customers;";
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            PreparedStatement allCustomers = connection.prepareStatement(secureQuery);
            ResultSet results = allCustomers.executeQuery();
            while(results.next()) {
                System.out.println("Contact Name: " + results.getString("ContactName"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void displayAllProducts() {

    }

}
