package com.example.seproject.findmetutor;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.*;
import com.microsoft.sqlserver.jdbc.*;
/**
 * Created by jared on 2016/08/02.
 */
public class databse_connect {

        public static void main(String[] args) {

            query();
            //insertrow();

        }

        public static String query() {
            String Temp = "Failed";
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
                String selectSql = "SELECT Name, Surname from PERSON";
                statement = connection.createStatement();
                resultSet = statement.executeQuery(selectSql);

                // Print results from select statement
                while (resultSet.next())
                {
                    Temp = resultSet.getString(1) + " "+ resultSet.getString(2);
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
            return Temp;
        }

        public static void insertrow() {
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
            PreparedStatement prepsInsertProduct = null;

            try {
                connection = DriverManager.getConnection(connectionString);

                // Create and execute an INSERT SQL prepared statement.
                String insertSql = "INSERT INTO PERSON (Name, Surname, E_Mail_Address, Password, Address, Contact_Number, DOB,Student_Tutor, Security_Question, Answer) VALUES "
                        + "('Josh','Naidoo','josh@gmail.com','Josh_Naidoo','6 Lupin Road','0118229141','19/07/1995','Tutor','What is my older brothers name?','Jared');";
                System.out.println("Works until here!");
                prepsInsertProduct = connection.prepareStatement(
                        insertSql,
                        Statement.RETURN_GENERATED_KEYS);
                prepsInsertProduct.execute();

                // Retrieve the generated key from the insert.
                resultSet = prepsInsertProduct.getGeneratedKeys();

                // Print the ID of the inserted row.
                while (resultSet.next()) {
                    System.out.println("Generated: " + resultSet.getString(1));
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                // Close the connections after the data has been handled.
                if (prepsInsertProduct != null) try { prepsInsertProduct.close(); } catch(Exception e) {}
                if (resultSet != null) try { resultSet.close(); } catch(Exception e) {}
                if (statement != null) try { statement.close(); } catch(Exception e) {}
                if (connection != null) try { connection.close(); } catch(Exception e) {}
            }
        }


}
