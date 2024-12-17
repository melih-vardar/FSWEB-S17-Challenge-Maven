package com.workintech.spring17challenge.validation;

import com.workintech.spring17challenge.exceptions.ApiException;
import com.workintech.spring17challenge.model.Course;
import org.springframework.http.HttpStatus;

import java.util.List;

public class CourseValidation {
    public static void isValid(Integer id) {
        if(id == null || id < 0){
            throw new ApiException("Id is not valid: "+ id, HttpStatus.BAD_REQUEST);
        }
    }

    public static void checkCourseExistence(List<Course> courses, Integer id, boolean existence) {
        boolean courseExists = id >= 0 && id < courses.size();

        if (existence) {
            if (!courseExists) {
                throw new ApiException("Record not exist: " + id, HttpStatus.NOT_FOUND);
            }
        } else {
            if (courseExists) {
                throw new ApiException("Record already exists: " + id, HttpStatus.BAD_REQUEST);
            }
        }
    }

    public static void checkCourseCredit(int credit) {
        if(credit<0 || credit>4){
            throw new ApiException("Credit should not be less than zero or greater than 4: "+ credit, HttpStatus.BAD_REQUEST);
        }

    }
}
