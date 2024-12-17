package com.workintech.spring17challenge.controller;


import com.workintech.spring17challenge.exceptions.ApiErrorResponse;
import com.workintech.spring17challenge.exceptions.ApiException;
import com.workintech.spring17challenge.model.*;
import com.workintech.spring17challenge.validation.CourseValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CourseController {

    private final List<Course> courses = new ArrayList<>();

    private final GpaCalculator gpaCalculator;

    // GET all courses
    @GetMapping
    public List<Course> getAllCourses() {
        return courses;
    }

    // GET course by name
    @GetMapping("/{name}")
    public ResponseEntity<Course> getCourseByName(@PathVariable String name) {
        return courses.stream()
                .filter(course -> course.getName().equalsIgnoreCase(name))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ApiException("Course not found with name: " + name, HttpStatus.NOT_FOUND));
    }

    // POST add a new course
    @PostMapping
    public ResponseEntity<CourseResponse> addCourse(@RequestBody Course newCourse) {
        CourseValidation.isValid(newCourse.getId());
        CourseValidation.checkCourseCredit(newCourse.getCredit());
//      CourseValidation.checkCourseExistence(courses, newCourse.getId(), false);

        courses.add(newCourse);

        int totalGpa = gpaCalculator.calculateTotalGpa(newCourse);
        CourseResponse response = new CourseResponse(newCourse, totalGpa);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // PUT update an existing course
    @PutMapping("/{id}")
    public ResponseEntity<CourseResponse>
    updateCourse(@PathVariable int id, @RequestBody Course updatedCourse) {
        CourseValidation.isValid(id);
        CourseValidation.checkCourseExistence(courses, id, true);
        CourseValidation.checkCourseCredit(updatedCourse.getCredit());

        courses.set(id, updatedCourse);

        int totalGpa = gpaCalculator.calculateTotalGpa(updatedCourse);
        CourseResponse response = new CourseResponse(updatedCourse, totalGpa);

        return ResponseEntity.ok(response);
    }

    // DELETE remove a course by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCourse(@PathVariable int id) {
        CourseValidation.isValid(id);
        CourseValidation.checkCourseExistence(courses, id, true);

        courses.remove(id);
        return ResponseEntity.ok("Course with ID " + id + " has been deleted successfully.");
    }
}