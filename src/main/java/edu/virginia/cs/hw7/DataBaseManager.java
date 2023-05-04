package edu.virginia.cs.hw7;

import java.nio.file.Path;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
        //insert courses
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
    public Boolean checkIfStudentExists(String username, String password){
        try{
            String checker = String.format("""
                SELECT COUNT(1) FROM Students WHERE login_name = "%s" AND password = "%s"
                """, username, password);
            Statement check = connection.createStatement();
            ResultSet rs = check.executeQuery(checker);
            if(rs.getBoolean(1)){
                check.close();
                return true;
            }
            else{
                check.close();
                return false;
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    public void addNewStudentAccount(String username, String password){
        try{
            String newAccount = String.format("""
                INSERT INTO STUDENTS (login_name, password)
                values("%s", "%s")""" , username, password);
            Statement insert = connection.createStatement();
            insert.execute(newAccount);
            insert.close();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public int getStudentID(String username) {
        try {
            String query = String.format("""
            SELECT id FROM Students
            WHERE login_name = '%s'
            """, username);

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                int studentID = resultSet.getInt("id");
                statement.close();
                return studentID;
            } else {
                statement.close();
                throw new IllegalArgumentException("Student not found.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean checkCourseExists(String department, String catalogNumber) {
        try {
            String query = String.format("""
            SELECT COUNT(*) FROM Courses
            WHERE department = '%s' AND catalog_number = '%s'
            """, department, catalogNumber);

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            int count = resultSet.getInt(1);
            statement.close();
            return count > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addCourse(String department, String catalogNumber) {
        try {
            String query = String.format("""
            INSERT INTO Courses (department, catalog_number)
            VALUES ('%s', '%s')
            """, department, catalogNumber);

            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getCourseID(String department, String catalogNumber) {
        try {
            String query = String.format("""
            SELECT id FROM Courses
            WHERE department = '%s' AND catalog_number = '%s'
            """, department, catalogNumber);

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                int courseID = resultSet.getInt("id");
                statement.close();
                return courseID;
            } else {
                statement.close();
                throw new IllegalArgumentException("Course not found.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addReview(String username, int courseID, String reviewText, int rating) {
        try {
            String studentCheck = String.format("""
            SELECT id FROM Students WHERE login_name = '%s'
            """, username);
            Statement studentStatement = connection.createStatement();
            ResultSet studentResult = studentStatement.executeQuery(studentCheck);

            if (!studentResult.next()) {
                throw new IllegalArgumentException("Student does not exist.");
            }

            int studentID = studentResult.getInt("id");

            String reviewInsert = String.format("""
            INSERT INTO Reviews (student_id, course_id, review_text, rating)
            VALUES (%d, %d, '%s', %d)
            """, studentID, courseID, reviewText, rating);

            Statement reviewStatement = connection.createStatement();
            reviewStatement.executeUpdate(reviewInsert);

            reviewStatement.close();
            studentStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean checkCourseHasReviews(int courseID) {
        try {
            String query = String.format("""
            SELECT COUNT(*) FROM Reviews
            WHERE course_id = %d
            """, courseID);

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            int count = resultSet.getInt(1);
            statement.close();
            return count > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String[] getReviewMessagesForCourse(int courseID) {
        try {
            String query = String.format("""
            SELECT review_text FROM Reviews
            WHERE course_id = %d
            """, courseID);

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            List<String> reviewMessages = new ArrayList<>();
            while (resultSet.next()) {
                String reviewText = resultSet.getString("review_text");
                reviewMessages.add(reviewText);
            }

            statement.close();
            return reviewMessages.toArray(new String[0]);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public double calculateAverageRatingForCourse(int courseID) {
        try {
            String query = String.format("""
            SELECT rating FROM Reviews
            WHERE course_id = %d
            """, courseID);

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            int ratingSum = 0;
            int count = 0;
            while (resultSet.next()) {
                int rating = resultSet.getInt("rating");
                ratingSum += rating;
                count++;
            }

            statement.close();

            if (count > 0) {
                return (double) ratingSum / count;
            } else {
                throw new IllegalArgumentException("Course not found or no ratings available.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean checkIfStudentReviewedCourse(int studentID, int courseID) {
        try {
            String query = String.format("""
            SELECT COUNT(*) FROM Reviews
            WHERE student_id = %d AND course_id = %d
            """, studentID, courseID);

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            int count = resultSet.getInt(1);
            statement.close();
            return count > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
