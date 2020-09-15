package io.broadcom.assignment.api;

import io.broadcom.assignment.data.CourseRepository;
import io.broadcom.assignment.data.GradeRepository;
import io.broadcom.assignment.data.StudentsRepository;
import io.broadcom.assignment.data.model.Course;
import io.broadcom.assignment.data.model.Grade;
import io.broadcom.assignment.data.model.Student;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * Represents the REST api of this service.
 * Some CRUD operations invalidate the cache.
 *
 * NOTE: For some reason when i tried to create seperate controllers for each resource it had some problems,
 * I didn't have time to figure our so all endpoints are in this controller, which i wouldn't do for production code.
 */
@RestController
@RequestMapping("/api")
@Slf4j
public class ApiController {

    private final GradeRepository gradeRepository;
    private final StudentsRepository studentsRepository;
    private final CourseRepository courseRepository;

    @Autowired
    ApiController(GradeRepository gradeRepository, StudentsRepository studentsRepository, CourseRepository courseRepository) {
        this.gradeRepository = gradeRepository;
        this.studentsRepository = studentsRepository;
        this.courseRepository = courseRepository;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Statistics
    /////////////////////////////////////////////////////////////////////////////////////////////////////////


    /**
     * Gets from cache, or Calculates, then returns the student with the highest average.
     * Nothing fancy, didn't get to make efficiency improvements.
     */
    @GetMapping("/stats/highest-average-student")
    @Cacheable(value = "best-student")
    public Student highestAverageStudent() {
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
            return null;
        }
        return ok(maxStudent);
    }

    public Student ok(Student student) {
        log.warn("running ok student function");
        return student;
    }

    /**
     * Gets from cache, or Calculates, then returns the course with the highest average.
     * Nothing fancy, didn't get to make efficiency improvements.
     */
    @GetMapping("/stats/highest-average-course")
    @Cacheable(value = "easiest-course")
    public Course getGrade() {
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
            return null;
        }
        return ok(maxCourse);
    }

    public Course ok(Course course) {
        log.warn("running ok Course function");
        return course;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Students
    /////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping("/students")
    public ResponseEntity<List<Student>> getStudents() {
        return ResponseEntity.ok(studentsRepository.findAll());
    }

    @GetMapping("/students/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable long id) {
        return studentsRepository.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/students")
    @CacheEvict(value = {"best-student", "easiest-course"}, allEntries = true)
    public ResponseEntity<Student> postStudent(@RequestBody Student student) {
        Student created = studentsRepository.save(student);
        return ResponseEntity.ok(created);
    }

    @DeleteMapping("/students/{id}")
    @CacheEvict(value = {"best-student", "easiest-course"}, allEntries = true)
    public void deleteStudent(@PathVariable long id) {
        studentsRepository.deleteById(id);
    }


    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Courses
    /////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping("/courses")
    public ResponseEntity<List<Course>> getCourses() {
        return ResponseEntity.ok(courseRepository.findAll());
    }

    @GetMapping("/courses/{id}")
    public ResponseEntity<Course> getCourse(@PathVariable String id) {
        return courseRepository.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/courses")
    @CacheEvict(value = {"best-student", "easiest-course"}, allEntries = true)
    public ResponseEntity<Course> postCourse(@RequestBody Course course) {
        Course created = courseRepository.save(course);
        return ResponseEntity.ok(created);
    }

    @DeleteMapping("/courses/{id}")
    @CacheEvict(value = {"best-student", "easiest-course"}, allEntries = true)
    public void deleteCourse(@PathVariable String id) {
        courseRepository.deleteById(id);
    }


    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Grades
    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    @GetMapping("/grades")
    public ResponseEntity<List<Grade>> getGrades() {
        return ResponseEntity.ok(gradeRepository.findAll());
    }

    @GetMapping("/grades/{studentId}/{courseId}")
    public ResponseEntity<Grade> getGrade(@PathVariable long studentId, @PathVariable String courseId) {
        Grade grade = gradeRepository.findFirstByStudentIdAndCourseId(studentId, courseId);
        if (grade == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(grade);
    }

    @PostMapping("/grades")
    @CacheEvict(value = {"best-student", "easiest-course"}, allEntries = true)
    public ResponseEntity<Grade> postGrade(@RequestBody Grade grade) {
        Grade created = gradeRepository.save(grade);
        return ResponseEntity.ok(created);
    }

    @DeleteMapping("/grades/{studentId}/{courseId}")
    @CacheEvict(value = {"best-student", "easiest-course"}, allEntries = true)
    public void deleteGrade(@PathVariable long studentId, @PathVariable String courseId) {
        gradeRepository.deleteByStudentIdAndCourseId(studentId, courseId);

    }
}
