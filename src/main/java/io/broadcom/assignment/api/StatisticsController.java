package io.broadcom.assignment.api;

import io.broadcom.assignment.data.CourseRepository;
import io.broadcom.assignment.data.GradeRepository;
import io.broadcom.assignment.data.StudentsRepository;
import io.broadcom.assignment.data.model.Course;
import io.broadcom.assignment.data.model.Grade;
import io.broadcom.assignment.data.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/stats")
public class StatisticsController {

    private final GradeRepository gradeRepository;
    private final StudentsRepository studentsRepository;
    private final CourseRepository courseRepository;

    @Autowired
    StatisticsController(GradeRepository gradeRepository, StudentsRepository studentsRepository, CourseRepository courseRepository) {
        this.gradeRepository = gradeRepository;
        this.studentsRepository = studentsRepository;
        this.courseRepository = courseRepository;
    }

    @GetMapping("/highest-average-student")
    ResponseEntity<Student> highestAverageStudent() {
        List<Student> allStudents = studentsRepository.findAll();

        double maxAvg = Integer.MIN_VALUE;
        Student maxStudent = null;
        for (Student student : allStudents) {
            double studentAvg = 0;
            int counter = 0;
            List<Grade> grades = gradeRepository.findByStudentId(student.getId());
            for (Grade grade : grades) {
                studentAvg += grade.getGrade();
                ++counter;
            }

            studentAvg /= counter;

            if (studentAvg > maxAvg) {
                maxAvg = studentAvg;
                maxStudent = student;
            }
        }

        if (maxStudent == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(maxStudent);
    }

    @GetMapping("/highest-average-course")
    ResponseEntity<Course> getGrade() {
        List<Course> allCourses = courseRepository.findAll();

        double maxAvg = Integer.MIN_VALUE;
        Course maxCourse = null;
        for (Course course : allCourses) {
            double courseAvg = 0;
            int counter = 0;
            List<Grade> grades = gradeRepository.findByCourseId(course.getId());
            for (Grade grade : grades) {
                courseAvg += grade.getGrade();
                ++counter;
            }

            courseAvg /= counter;

            if (courseAvg > maxAvg) {
                maxAvg = courseAvg;
                maxCourse = course;
            }
        }

        if (maxCourse == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(maxCourse);
    }

}
