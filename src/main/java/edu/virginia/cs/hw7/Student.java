package edu.virginia.cs.hw7;

public class Student {
    private int id;
    private String login_name;
    private String password;

    public Student(int id, String login_name, String password) {
        this.id = id;
        this.login_name = login_name;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin_name() {
        return login_name;
    }

    public void setLogin_name(String login_name) {
        this.login_name = login_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
