package com.workintech.spring17challenge.model;

public class CourseResponse {
    private Course course;
    private int totalGpa;

    public CourseResponse(Course course, int totalGpa) {
        this.course = course;
        this.totalGpa = totalGpa;
    }

    public Course getCourse() {
        return course;
    }

    public int getTotalGpa() {
        return totalGpa;
    }
}
