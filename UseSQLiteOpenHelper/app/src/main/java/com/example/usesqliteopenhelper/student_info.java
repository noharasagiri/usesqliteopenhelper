package com.example.usesqliteopenhelper;

public class student_info {
    private int id;
    private String name;
    private String course;
    private int score;

    public student_info() {
    }

    public student_info(int id, String name, String course, int score) {
        this.id = id;
        this.name = name;
        this.course = course;
        this.score = score;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}