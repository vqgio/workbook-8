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
                System.out.println("Company Name: " + results.getString("CompanyName"));
                System.out.println("City: " + results.getString("City"));
                System.out.println("Country: " + results.getString("Country"));
                System.out.println("Phone Number: " + results.getString("Phone"));
                System.out.println("======================================================");
            }
            System.out.println("All customers in the system.");
            System.out.println("======================================================");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void displayAllProducts() throws ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String username = "root";
        String password = "yearup";
        String url = "jdbc:mysql://localhost:3306/northwind";
        String secureQuery = "SELECT ProductID, ProductName, UnitPrice, UnitsInStock FROM products;";
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            PreparedStatement allProducts = connection.prepareStatement(secureQuery);
            ResultSet results = allProducts.executeQuery();
            while(results.next()) {
                System.out.println("Product ID: " + results.getString("ProductID"));
                System.out.println("Name: " + results.getString("ProductName"));
                System.out.println("Price: " + results.getString("UnitPrice"));
                System.out.println("Stock: " + results.getString("UnitsInStock"));
                System.out.println("======================================================");
            }
            System.out.println("All products in the system.");
            System.out.println("======================================================");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
