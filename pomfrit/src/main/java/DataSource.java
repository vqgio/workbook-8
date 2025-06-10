import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DataSource {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/sakila?user=root&password=yearup";

        try(BasicDataSource dataSource = new BasicDataSource()) {
            dataSource.setUrl(url);
            Connection connection = dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
