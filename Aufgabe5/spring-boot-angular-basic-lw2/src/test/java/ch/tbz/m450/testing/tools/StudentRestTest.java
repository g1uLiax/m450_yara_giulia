package ch.tbz.m450.testing.tools;

import ch.tbz.m450.testing.tools.repository.entities.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentRestTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate rest;

    private String url(String path) {
        return "http://localhost:" + port + path;
    }

    @Test
    void getStudents_shouldReturn200() {
        ResponseEntity<String> response = rest.getForEntity(url("/students"), String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotEmpty();
    }

    @Test
    void postStudent_shouldCreate() {
        Student s = new Student("David", "david@tbz.ch");

        ResponseEntity<Void> response = rest.postForEntity(url("/students"), s, Void.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        // now check count
        ResponseEntity<Student[]> all = rest.getForEntity(url("/students"), Student[].class);
        assertThat(all.getBody().length).isPositive();
    }
}