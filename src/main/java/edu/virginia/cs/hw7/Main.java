package edu.virginia.cs.hw7;

public class Main {
    public static DataBaseManager manager;
    public static void main(String[] args) {
        manager = new DataBaseManager();
        UserInterface UI = new UserInterface(manager);
        UI.login_screen();
    }
}