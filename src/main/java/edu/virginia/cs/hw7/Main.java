package edu.virginia.cs.hw7;

import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    public static DataBaseManager manager;
    public static void main(String[] args) {
        ConfigSingleton instance = ConfigSingleton.getInstance();
        manager = new DataBaseManager();
        if(!checkIfDatabaseExists()){
            manager.connect(instance);
            manager.createTables();
            manager.clearTables();
            manager.fillStartereData();
            UserInterface UI = new UserInterface(manager);
            UI.login_screen();
        }
        else {
            manager.connect(instance);
            UserInterface UI = new UserInterface(manager);
            UI.login_screen();
        }
    }

    public static boolean checkIfDatabaseExists(){
        return Files.exists(Path.of("Reviews.sqlite3"));
    }
}