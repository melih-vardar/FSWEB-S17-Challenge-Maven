package com.workintech.spring17challenge.model;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GpaCalculator {

    private final CourseGpa lowCourseGpa;
    private final CourseGpa mediumCourseGpa;
    private final CourseGpa highCourseGpa;

    public int calculateTotalGpa(Course course) {
        int credit = course.getCredit();
        int coefficient = course.getGrade().getCoefficient();

        if (credit <= 2) {
            return coefficient * credit * lowCourseGpa.getGpa();
        } else if (credit == 3) {
            return coefficient * credit * mediumCourseGpa.getGpa();
        } else if (credit == 4) {
            return coefficient * credit * highCourseGpa.getGpa();
        } else {
            throw new IllegalArgumentException("Invalid credit value for GPA calculation: " + credit);
        }
    }
}
