package io.broadcom.assignment.data;

import io.broadcom.assignment.data.model.Course;
import io.broadcom.assignment.data.model.Student;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestCoursesRepository {

    private static final String COURSE_NAME = "Logic";
    private static final Long[] COURSE_STUDENT_IDS = {1L, 2L, 3L};
    private static final String UPDATED_COURSE_NAME = "Intro to Logic";
    private static final Long[] UPDATED_COURSE_STUDENT_IDS = {3L, 4L};

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    static String id;

    @BeforeAll
    static void prepare() {
        id = UUID.randomUUID().toString();
    }

    @Test
    @Order(1)
    public void contextLoads() {
        assertThat(restTemplate).isNotNull();
    }

    @Test
    @Order(2)
    public void testPostCourse() {
        ResponseEntity<Course> courseRes = this.restTemplate.postForEntity("http://localhost:" + port + "/api/" + "courses",
                createCourse(), Course.class);
        assertThat(courseRes.getStatusCode().is2xxSuccessful());
        Course course = courseRes.getBody();
        assert course != null;
        verifyCourse(course);
    }

    @Test
    @Order(3)
    public void testGetCourse() {
        Course course = this.restTemplate.getForObject("http://localhost:" + port + "/api/" + "courses" + "/" + id,
                Course.class);
        assert course != null;
        verifyCourse(course);
    }

    @Test
    @Order(4)
    public void testUpdateCourse() {
        this.restTemplate.put("http://localhost:" + port + "/" + "courses",
                createUpdatedUser(), Course.class);

        Course course = this.restTemplate.getForObject("http://localhost:" + port + "/api/" + "courses" + "/" + id,
                Course.class);
        assert course != null;
        verifyUpdatedUser(course);
    }


    @Test
    @Order(5)
    public void testDeleteCourse() {
        this.restTemplate.delete("http://localhost:" + port + "/api/" + "courses" + "/" + id);
        Course course = this.restTemplate.getForObject("http://localhost:" + port + "/api/" + "courses" + "/" + id,
                Course.class);
        assertThat(course == null);
    }

    private Object createCourse() {
        List<Long> students = new ArrayList<>();
        Collections.addAll(students, COURSE_STUDENT_IDS);
        return new Course(id, COURSE_NAME, students);
    }


    private Object createUpdatedUser() {
        List<Long> students = new ArrayList<>();
        Collections.addAll(students, UPDATED_COURSE_STUDENT_IDS);
        return new Course(id, UPDATED_COURSE_NAME, students);

    }

    private void verifyCourse(Course course) {
        assertThat(course.getId().equals(id));
        assertThat(course.getName().equals(COURSE_NAME));

        for (long id : COURSE_STUDENT_IDS) {
            assertThat(course.getStudentIds().contains(id));
        }

    }

    private void verifyUpdatedUser(Course course) {
        assertThat(course.getId().equals(id));
        assertThat(course.getName().equals(COURSE_NAME));

        for (long id : COURSE_STUDENT_IDS) {
            assertThat(course.getStudentIds().contains(id));
        }
    }


}
