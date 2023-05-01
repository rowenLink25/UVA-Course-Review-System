package edu.virginia.cs.hw7;

import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static java.nio.file.Files.exists;

public class DataBaseManager {
    private boolean connected = false;
    private Connection connection;


    public void connect(ConfigSingleton configSingleton) {
        try {
            if (!connected) {
                connection = DriverManager.getConnection("jdbc:sqlite:Reviews.sqlite3");
                connected = true;
            } else {
                throw new IllegalStateException("Already connected");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void createTables(){
        try {
            String studentsTable = """
                       CREATE TABLE IF NOT EXISTS Students(
                                                id INT AUTO_INCREMENT PRIMARY KEY,
                                                login_name VARCHAR(255) UNIQUE NOT NULL,
                                                password VARCHAR(255) NOT NULL
                       )""";
            Statement statement = connection.createStatement();
            statement.execute(studentsTable);
            statement.close();
            String coursesTable = """
                   CREATE TABLE IF NOT EXISTS Courses(
                                           id INT AUTO_INCREMENT PRIMARY KEY,
                                           department VARCHAR(255) NOT NULL,
                                           catalog_number VARCHAR(255) NOT NULL
                   )
                   """;
            Statement statement2 = connection.createStatement();
            statement2.execute(coursesTable);
            statement2.close();
            String reviewsTable = """
                   CREATE TABLE IF NOT EXISTS Reviews (
                                           id INT AUTO_INCREMENT PRIMARY KEY,
                                           student_id INT NOT NULL,
                                           course_id INT NOT NULL,
                                           review_text TEXT NOT NULL,
                                           rating INT NOT NULL CHECK (rating BETWEEN 1 AND 5),
                                           FOREIGN KEY (student_id) REFERENCES Students(id),
                                           FOREIGN KEY (course_id) REFERENCES Courses(id)
                   )
                   """;
            Statement statement3 = connection.createStatement();
            statement3.execute(reviewsTable);
            statement3.close();
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

}
