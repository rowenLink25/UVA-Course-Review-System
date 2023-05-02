package edu.virginia.cs.hw7;

public class Main {
    public static DataBaseManager manager;
    public static void main(String[] args) {
        ConfigSingleton instance = ConfigSingleton.getInstance();
        manager = new DataBaseManager();
        manager.connect(instance);
        manager.createTables();
        manager.clearTables();
        manager.fillStartereData();
        UserInterface UI = new UserInterface(manager);
        UI.login_screen();
    }


}