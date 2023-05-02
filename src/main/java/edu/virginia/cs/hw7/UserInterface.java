package edu.virginia.cs.hw7;

import java.util.Scanner;

public class UserInterface {
    DataBaseManager manager;

    public UserInterface(DataBaseManager theManager){
        manager = theManager;
    }

    public void login_screen(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Type the number of your desired option and press enter");
        System.out.println("1 : Login-to existing user");
        System.out.println("2 : Create a new user");
        String choice = scanner.nextLine();
        if(choice.equals("1")){
            loginToExistingUser();
            return;
        }
        if(choice.equals("2")){
            createNewUser();
            return;
        }
        else {
            System.out.println("Please select a valid option");
            login_screen();
        }
    }
    public void loginToExistingUser(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter your username");
        String username = scanner.nextLine();
        System.out.println("Please enter your password");
        String password = scanner.nextLine();
        //Need to make database interaction
        System.out.println(username);
        System.out.println(password);
        Boolean result = manager.checkIfStudentExists(username, password);
        if(result == true){
            mainMenu();
        }
        else {
            System.out.println("No account found with that username or password");
            login_screen();
            return;
        }
    }
    public void createNewUser(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter your desired username");
        String username = scanner.nextLine();
        System.out.println("Please enter your desired password");
        String password = scanner.nextLine();
        System.out.println("Please re-enter your desired password");
        String confirmPassword = scanner.nextLine();
        if(password.equals(confirmPassword)){
            // Add code that adds them to Students table
            manager.addNewStudentAccount(username, password);
            mainMenu();
        }
        else{
            System.out.println("Passwords do not match");
            login_screen();
        }
    }
    public void mainMenu(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Type the number of your desired option and press enter");
        System.out.println("1 : Submit a review for a course");
        System.out.println("2 : See reviews for course");
        System.out.println("3 : Log-out");
        String choice = scanner.nextLine();
        if(choice.equals("1")){
            // need to add code to submit a review
        }
        if(choice.equals("2")){
            // need to add code to see reviews
        }
        if(choice.equals("3")){
            login_screen();
        }

    }
}
