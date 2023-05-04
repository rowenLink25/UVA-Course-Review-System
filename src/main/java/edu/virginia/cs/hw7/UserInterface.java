package edu.virginia.cs.hw7;

import java.util.Scanner;

public class UserInterface {
    DataBaseManager manager;

    public UserInterface(DataBaseManager theManager){
        manager = theManager;
    }

    public String user = "";

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
        user = username;
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
        user = username;
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
            submitReview();
        }
        if(choice.equals("2")){
            // need to add code to see reviews
            seeReviews();
        }
        if(choice.equals("3")){
            login_screen();
        }
        else {
            System.out.println("Please select a valid option.");
            mainMenu();
        }

    }

    public void submitReview(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the course name (e.g., CS 3140): ");
        String courseName = scanner.nextLine();

        String[] breakDown = courseName.split(" ");
        String subject = breakDown[0];
        String catNum = breakDown[1];

        if(!validCourseName(courseName)){
            System.out.println("Invalid course name. Please enter a valid course name in the format 'Subject CatalogNumber'.");
            mainMenu();
        }

        if(!manager.checkCourseExists(subject, catNum)){
            manager.addCourse(subject,catNum);
        }

        if(manager.checkIfStudentReviewedCourse(manager.getStudentID(user), manager.getCourseID(subject, catNum))) {
            System.out.println("You have already reviewed this course, please choose a different course.");
            mainMenu();
        }

        System.out.print("Enter your review message: ");
        String message = scanner.nextLine();

        System.out.print("Rate your experience in the class between 1 and 5: ");
        int rating = validRating();

        manager.addReview(user, manager.getCourseID(subject, catNum), message, rating);

        System.out.println("Review submitted successfully!");
        mainMenu();
    }

    public boolean validCourseName(String courseName){
        String[] breakDown = courseName.split(" ");
        if(breakDown.length != 2){
            return false;
        }
        String subject = breakDown[0];
        String catNum = breakDown[1];
        if(subject.length() >4){
            return false;
        }
        for(char c : subject.toCharArray()){
            if(!Character.isLetter(c)){
                return false;
            }
            if (Character.isLowerCase(c)) {
                return false;
            }
        }
        if(catNum.length()!=4){
            return false;
        }
        for(char c : catNum.toCharArray()){
            if(!Character.isDigit(c)){
                return false;
            }
        }
        return true;
    }

    public int validRating(){
        Scanner scanner = new Scanner(System.in);

        int rate = Integer.parseInt(scanner.nextLine());
        if(rate>=1 && rate<=5){
            return rate;
        }
        else {
            System.out.println("Invalid rating. Please enter a number between 1 and 5.");
            return validRating();
        }
    }

    public void seeReviews() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the course name (e.g., CS 3140): ");
        String courseName = scanner.nextLine();

        String[] breakDown = courseName.split(" ");
        String subject = breakDown[0];
        String catNum = breakDown[1];

        if(!validCourseName(courseName)){
            System.out.println("Invalid course name. Please enter a valid course name in the format 'Subject CatalogNumber'.");
            mainMenu();
        }

        if(!manager.checkCourseHasReviews(manager.getCourseID(subject, catNum))) {
            System.out.println("This course has no reviews yet. Please pick a different course.");
            mainMenu();
        }

        String[] reviewMessages = manager.getReviewMessagesForCourse(manager.getCourseID(subject, catNum));
        System.out.println("Review Messages for "+ courseName+":");
        for (String message : reviewMessages) {
            System.out.println(message);
        }

        double averageRate = manager.calculateAverageRatingForCourse(manager.getCourseID(subject, catNum));
        System.out.println("Average rating for "+ courseName+": "+averageRate+"/5");

        mainMenu();
    }
}
