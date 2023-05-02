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
    private boolean alreadyFilled = false;


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
    public void SQLoperator(String query){
        try {
            Statement statement = connection.createStatement();
            statement.execute(query);
            statement.close();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    public void createTables(){
        try {
            String studentsTable = """
                       CREATE TABLE IF NOT EXISTS Students(
                                                id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
                                                login_name VARCHAR(255) UNIQUE NOT NULL,
                                                password VARCHAR(255) NOT NULL
                       )""";
            Statement statement = connection.createStatement();
            statement.execute(studentsTable);
            statement.close();
            String coursesTable = """
                   CREATE TABLE IF NOT EXISTS Courses(
                                           id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
                                           department VARCHAR(255) NOT NULL,
                                           catalog_number VARCHAR(255) NOT NULL
                   )
                   """;
            Statement statement2 = connection.createStatement();
            statement2.execute(coursesTable);
            statement2.close();
            String reviewsTable = """
                   CREATE TABLE IF NOT EXISTS Reviews (
                                           id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
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

    public void fillStartereData(){
        SQLoperator("INSERT INTO Courses(id, department, catalog_number) VALUES (1, 'CS', 2130)");
        SQLoperator("INSERT INTO Courses(id, department, catalog_number) VALUES (2, 'APMA', 1234)");
        SQLoperator("INSERT INTO Courses(id, department, catalog_number) VALUES (3, 'ENWR', 5693)");
        SQLoperator("INSERT INTO Courses(id, department, catalog_number) VALUES (4, 'CHEM', 3450)");
        SQLoperator("INSERT INTO Courses(id, department, catalog_number) VALUES (5, 'BIOS', 2232)");
        //insert students
        SQLoperator("INSERT INTO Students(login_name, password) VALUES ('Rowen', 'thePassword')");
        SQLoperator("INSERT INTO Students(login_name, password) VALUES ('emma', 'poopypants')");
        SQLoperator("INSERT INTO Students(login_name, password) VALUES ('nicotine', 'cancerous')");
        SQLoperator("INSERT INTO Students(login_name, password) VALUES ('kappakappakappa', 'mean')");
        SQLoperator("INSERT INTO Students(login_name, password) VALUES ('bambam', 'tuivasa')");
        //Insert reviews
        SQLoperator("Insert INTO Reviews(student_id, course_id, review_text, rating) VALUES (1, 1, 'bad', 4)");
        SQLoperator("Insert INTO Reviews(student_id, course_id, review_text, rating) VALUES (1, 3, 'decent', 2)");
        SQLoperator("Insert INTO Reviews(student_id, course_id, review_text, rating) VALUES (3, 2, 'meh', 3)");
        SQLoperator("Insert INTO Reviews(student_id, course_id, review_text, rating) VALUES (4, 5, 'good', 4)");
        SQLoperator("Insert INTO Reviews(student_id, course_id, review_text, rating) VALUES (2, 1, 'great', 5)");
    }

    public void clearTables(){
        SQLoperator("DELETE FROM Courses");
        SQLoperator("DELETE FROM Students");
        SQLoperator("DELETE FROM Reviews");
    }

}
