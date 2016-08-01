package com.example.seproject.findmetutor;// Use the JDBC driver
import java.sql.*;
import com.microsoft.sqlserver.jdbc.*;

public class Query{

    // Connect to your database.
    // Replace server name, username, and password with your credentials
    public static void main(String[] args) {
        String connectionString =
                "jdbc:sqlserver://findmetutorsql.database.windows.net:1433;"
                        + "database=findmetutor;"
                        + "user=Jared@findmetutorsql;"
                        + "password=Tremayne852;"
                        + "encrypt=true;"
                        + "trustServerCertificate=false;"
                        + "hostNameInCertificate=*.database.windows.net;"
                        + "loginTimeout=30;";

        // Declare the JDBC objects.
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection(connectionString);

            // Create and execute a SELECT SQL statement.
            String selectSql = "SELECT TOP 10 Title, FirstName, LastName from SalesLT.Customer";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(selectSql);

            // Print results from select statement
            while (resultSet.next())
            {
                System.out.println(resultSet.getString(2) + " "
                        + resultSet.getString(3));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            // Close the connections after the data has been handled.
            if (resultSet != null) try { resultSet.close(); } catch(Exception e) {}
            if (statement != null) try { statement.close(); } catch(Exception e) {}
            if (connection != null) try { connection.close(); } catch(Exception e) {}
        }
    }
}
