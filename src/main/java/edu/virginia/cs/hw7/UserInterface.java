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

        }
        else{
            System.out.println("Passwords do not match");
            login_screen();
        }
    }
}
