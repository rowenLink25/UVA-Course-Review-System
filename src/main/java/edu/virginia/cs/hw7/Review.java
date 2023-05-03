package edu.virginia.cs.hw7;

public class Review {
    private int id;
    private int student_id;
    private int course_id;
    private String review_text;
    private int rating;

    public Review(int id, int student_id, int course_id, String review_text, int rating) {
        this.id = id;
        this.student_id = student_id;
        this.course_id = course_id;
        this.review_text = review_text;
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStudent_id() {
        return student_id;
    }

    public void setStudent_Id(int student_id) {
        this.student_id = student_id;
    }

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public String getReview_text() {
        return review_text;
    }

    public void setReview_text(String review_text) {
        this.review_text = review_text;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
