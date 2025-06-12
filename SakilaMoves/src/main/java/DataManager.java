import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DataManager {
    private static DataSource dataSource;
    public DataManager(DataSource dataSource){
        DataManager.dataSource = dataSource;
    }
    public static List<Actor> searchActorsName() throws SQLException {
        List<Actor> actors = new ArrayList<>();
        System.out.println("Enter actor first name: ");
        String firstNameInput = Main.scanner.nextLine();
        System.out.println("Enter actor last name: ");
        String lastNameInput = Main.scanner.nextLine();
        String sql = "SELECT actor_id, first_name, last_name FROM actor WHERE first_name = ? AND last_name = ?;";
        Connection conn = dataSource.getConnection();
        PreparedStatement statement = conn.prepareStatement(sql);
        ResultSet results = statement.executeQuery(sql);
        while (results.next())
        {
            int id = results.getInt("actor_id");
            String firstName = results.getString("first_name");
            String lastName = results.getString("last_name");
            Actor actor = new Actor(id, firstName, lastName);
            actors.add(actor);
        }
        return actors;
    }
}
