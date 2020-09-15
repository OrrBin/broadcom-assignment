package io.broadcom.assignment.data;

import io.broadcom.assignment.data.model.Student;

import org.junit.jupiter.api.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestStudentsRepository {

    private static final String STUDENT_FIRST_NAME = "Orr";
    private static final String STUDENT_LAST_NAME = "Benyamini";
    private static final String STUDENT_EMAIL = "orrbenyamini@gmail.com";
    private static final String UPDATED_STUDENT_FIRST_NAME = "Bob";
    private static final String UPDATED_STUDENT_LAST_NAME = "Alice";
    private static final String UPDATED_STUDENT_EMAIL = "bob.alice@gmail.com";

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
    public void testPostStudent() throws Exception {
        ResponseEntity<Student> studentRes = this.restTemplate.postForEntity("http://localhost:" + port + "/" + "students",
                createUser(), Student.class);
        assertThat(studentRes.getStatusCode().is2xxSuccessful());
        Student student = studentRes.getBody();
        assert student != null;
        verifyUser(student);
    }

    @Test
    @Order(3)
    public void testGetStudent() throws Exception {
        Student student = this.restTemplate.getForObject("http://localhost:" + port + "/" + "students" + "/" + id,
                Student.class);
        assert student != null;
        verifyUser(student);
    }

    @Test
    @Order(4)
    public void testUpdateStudent() throws Exception {
        this.restTemplate.put("http://localhost:" + port + "/" + "students",
                createUpdatedUser(), Student.class);

        Student student = this.restTemplate.getForObject("http://localhost:" + port + "/" + "students" + "/" + id,
                Student.class);
        assert student != null;
        verifyUpdatedUser(student);
    }


    @Test
    @Order(5)
    public void testDeleteStudent() throws Exception {
        this.restTemplate.delete("http://localhost:" + port + "/" + "students" + "/" + id);
        Student studentRes = this.restTemplate.getForObject("http://localhost:" + port + "/" + "students" + "/" + id,
                Student.class);
        assertThat(studentRes == null);
    }

    private Object createUser() {
        return new Student(id, STUDENT_FIRST_NAME, STUDENT_LAST_NAME, STUDENT_EMAIL);
    }


    private Object createUpdatedUser() {
        return new Student(id, STUDENT_FIRST_NAME, STUDENT_LAST_NAME, STUDENT_EMAIL);

    }

    private void verifyUser(Student student) {
        assertThat(student.getId() == id);
        assertThat(student.getFirstName().equals(STUDENT_FIRST_NAME));
        assertThat(student.getLastName().equals(STUDENT_LAST_NAME));
        assertThat(student.getEmail().equals(STUDENT_EMAIL));
    }


    private void verifyUpdatedUser(Student student) {
        assertThat(student.getId() == id);
        assertThat(student.getFirstName().equals(UPDATED_STUDENT_FIRST_NAME));
        assertThat(student.getLastName().equals(UPDATED_STUDENT_LAST_NAME));
        assertThat(student.getEmail().equals(UPDATED_STUDENT_EMAIL));
    }


}
