package edu.virginia.cs.hw7;

public class Course {
    private int id;
    private String department;
    private int catalog_number;

    public Course(int id, String department, int catalog_number) {
        this.id = id;
        this.department = department;
        this.catalog_number = catalog_number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String name) {
        this.department = department;
    }

    public int getCatalog() {
        return catalog_number;
    }

    public void setCatalog(int catalog_number) {
        this.catalog_number = catalog_number;
    }
}
