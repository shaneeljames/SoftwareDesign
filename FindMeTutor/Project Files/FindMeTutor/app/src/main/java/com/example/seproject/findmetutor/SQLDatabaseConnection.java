// Use the JDBC driver
import java.sql.*;
import com.microsoft.sqlserver.jdbc.*;

public class SQLDatabaseConnection {

    // Connect to your database.
    // Replace server name, username, and password with your credentials
    public static void main(String[] args) {
        String connectionString =
                "jdbc:sqlserver://yourserver.database.windows.net:1433;"
                        + "database=AdventureWorks;"
                        + "user=yourusername@yourserver;"
                        + "password=yourpassword;"
                        + "encrypt=true;"
                        + "trustServerCertificate=false;"
                        + "hostNameInCertificate=*.database.windows.net;"
                        + "loginTimeout=30;";

        // Declare the JDBC objects.
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(connectionString);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (connection != null) try { connection.close(); } catch(Exception e) {}
        }
    }
}