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
            if(exists(Path.of("Reviews.sqlite3"))){


            }
            else{
                String StopsTable = """
                       CREATE TABLE STOPS(ID INTEGER NOT NULL , NAME VARCHAR(255) NOT NULL,
                       LATITUDE DOUBLE NOT NULL ,LONGITUDE DOUBLE NOT NULL , PRIMARY KEY (ID))""";
                Statement statement = connection.createStatement();
                statement.execute(StopsTable);
                statement.close();
            }
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

}
