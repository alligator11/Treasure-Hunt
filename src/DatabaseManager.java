import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
    String url = "jdbc:mysql://localhost:3306/";
    String databaseName = "db";
    String username = "root";
    String password = "Sanika@123";
    String name;
    Connection connection;
    Statement statement;

    DatabaseManager(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(url+databaseName, username, password);
            this.statement = connection.createStatement();
            System.out.println("Database Connected Sucessfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        try {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createDB(){
        try{
            String query = "CREATE DATABASE IF NOT EXISTS "+ databaseName;
            statement.execute(query);
            // System.out.println("Database created Sucessfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createTable(){
        try {
            String query = "CREATE TABLE IF NOT EXISTS scores (name VARCHAR(20), score INT(4))";
            statement.execute(query);
            // System.out.println("Table Created Successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createUser(String name){
        try {
            String query = "INSERT INTO scores (name, score) VALUES ('" + name + "', 0)";
            statement.execute(query);
            System.out.println("Data Inserted Successfully");
        } catch (Exception e) {
            System.out.println("Retry Inserting Data");
        }
    }

    public void updateScores(String name, int score){
        try{
              String query = "UPDATE scores SET score=? WHERE name=?";
              PreparedStatement ps = connection.prepareStatement(query);

              ps.setInt(1, score);
              ps.setString(2, name);

              int affectedRows = ps.executeUpdate();
              if(affectedRows<=0){
                System.out.println("User not found: " + name);
              } else {
                System.out.println("Score updated successfully for " + name);
              }
        } catch (Exception e) {
            System.out.println("Retry Updating");
        }
    }

    public ResultSet readData(){
        try {
            String query = "Select * FROM scores ORDER BY score DESC"; 
            ResultSet rs = statement.executeQuery(query);
            return rs;
            // while(rs.next()){
            //    System.out.println("name: "+rs.getString(1)); 
            //    System.out.println("score: "+rs.getInt(2));
            // }
        } catch (Exception e) {
            System.out.println("Error Reading Data");
            return null;
        }
    }

    public void deleteUser(String name){
        try {
        String query = "DELETE FROM scores WHERE name=?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, name);
        int affectedRows = ps.executeUpdate();
        if (affectedRows > 0) {
            System.out.println("Deleted entry for " + name);
        } else {
            System.out.println("User not found: " + name);
        }
        } catch (Exception e) {
            System.out.println("Error deleting entry for " + name);
        }
    }
}

//execute ==> used for all types of SQL statements return boolean true for rs
//executeUpdate ==> used for DML Statements (INSERT, UPDATE, DELETE), return int count of affected rows
//executeQuery ==> used to retrieve data, returns rs


