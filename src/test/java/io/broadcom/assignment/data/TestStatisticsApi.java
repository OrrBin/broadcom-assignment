package io.broadcom.assignment.data;

import io.broadcom.assignment.data.model.Course;
import io.broadcom.assignment.data.model.Student;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Only sanity check, in real life should test the correctness of the results from the API
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestStatisticsApi {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    static Random random;
    static long id;

    @BeforeAll
    static void prepare() {
        random = new Random();
        id = random.nextLong();
    }

    @Test
    @Order(1)
    public void contextLoads() throws Exception {
        assertThat(restTemplate).isNotNull();
    }


    @Test
    @Order(2)
    public void testBestStudent() throws Exception {
        ResponseEntity<Student> studentRes = this.restTemplate.getForEntity("http://localhost:" + port + "/api/" + "stats/highest-average-student", Student.class);
        assertThat(studentRes.getStatusCode().is2xxSuccessful());
        Student student = studentRes.getBody();
        assert student != null;
        assert student.getFirstName() != null;
        assert student.getLastName() != null;
        assert student.getEmail() != null;
    }

    @Test
    @Order(2)
    public void testGetBestCourse() throws Exception {
        ResponseEntity<Course> studentRes = this.restTemplate.getForEntity("http://localhost:" + port + "/api/" + "stats/highest-average-course", Course.class);
        assertThat(studentRes.getStatusCode().is2xxSuccessful());
        Course student = studentRes.getBody();
        assert student != null;
        assert student.getId() != null;
        assert student.getName() != null;
        assert student.getStudentIds() != null;
    }

}
