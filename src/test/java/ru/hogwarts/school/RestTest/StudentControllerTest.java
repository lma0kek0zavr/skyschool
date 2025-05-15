package ru.hogwarts.school.RestTest;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.dto.FacultyDto;
import ru.hogwarts.school.dto.StudentDto;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

    @Autowired
    private TestRestTemplate testTemplate;

    @Test
    void contextLoaded() {
        Assertions.assertThat(studentController).isNotNull();
    }

    @Test
    void shouldReturnAllStudents_WhenGetAllRequest() {
        String url = "http://localhost:" + port + "/student/getAll";

        List<StudentDto> students = this.testTemplate.getForObject(url, List.class);

        Assertions.assertThat(students)
        .isNotNull()
        .isInstanceOf(List.class);
    }

    @ParameterizedTest
    @ValueSource(longs = {3L, 4L})
    void shouldReturnStudent_WhenGetByIdRequest(Long id) {
        String url = "http://localhost:" + port + "/student/get" + id;

        StudentDto student = this.testTemplate.getForObject(url, StudentDto.class);

        Assertions.assertThat(student)
            .isNotNull()
            .isInstanceOf(StudentDto.class);

        Assertions.assertThat(student.getId()).isEqualTo(id);
    }

    @Test
    void shouldReturnStudent_WhenPostRequest() {
        StudentDto student = new StudentDto();

        student.setName("Test");
        student.setAge(10);
        student.setId(1L);
        student.setStudentFaculties(null);

        StudentDto savedStudent = this.testTemplate.postForObject("http://localhost:" + port + "/student/save", student, StudentDto.class);

        Assertions.assertThat(savedStudent).isNotNull();
    }

    @Test
    void shouldReturnStudent_WhenDeleteRequest() {
        Long id = 1L;

        String deleteUrl = "http://localhost:" + port + "/student/delete" + id;
        String getUrl = "http://localhost:" + port + "/student/get" + id;

        this.testTemplate.delete(deleteUrl);

        StudentDto student = new StudentDto();

        student = this.testTemplate.getForObject(getUrl, StudentDto.class);

        Assertions.assertThat(student).isNotNull();
    }

    @Test
    void shouldReturnStudent_WhenUpdateRequest() {
        Long id = 1L;

        StudentDto student = new StudentDto();
        
        student.setName("Test");
        student.setAge(10);

        String updateUrl = "http://localhost:" + port + "/student/update/" + id;
        String getUrl = "http://localhost:" + port + "/student/get" + id;

        StudentDto updatedStudent = new StudentDto();

        this.testTemplate.patchForObject(updateUrl, student, StudentDto.class);

        updatedStudent = this.testTemplate.getForObject(getUrl, StudentDto.class);

        Assertions.assertThat(updatedStudent).isNotNull();
    }

    @Test
    void shouldReturnStudentsNames_WhenGetStudentsNamesRequest() {
        String url = "http://localhost:" + port + "/student/getStudentsNames";

        List<String> studentsNames = this.testTemplate.getForObject(url, List.class);

        Assertions.assertThat(studentsNames)
                .isNotNull()
                .isInstanceOf(List.class);
    }

    @Test
    void shouldReturnStudentsAges_WhenGetStudentsAgesRequest() {
        int lowRange = 10;
        int highRange = 20;

        String url = "http://localhost:" + port + "/student/findByAgeBetween" + lowRange + "/" + highRange;

        List<StudentDto> students = this.testTemplate.getForObject(url, List.class);

        Assertions.assertThat(students)
                .isNotNull()
                .isInstanceOf(List.class);
    }

    @Test
    void shouldReturnStudentsWithCharacterContains_WhenGetStudentsWithCharacterContainsRequest() {
        char character = 'a';

        String url = "http://localhost:" + port + "/student/findByNameContains" + character;

        List<StudentDto> students = this.testTemplate.getForObject(url, List.class);

        Assertions.assertThat(students)
            .isNotNull()
            .isInstanceOf(List.class);
    }

    @Test
    void shouldReturnStudentsWhoseAgeIsLessThanGiven_WhenGetStudentsWhoseAgeIsLessThanGivenRequest() {
        int age = 22;

        String url = "http://localhost:" + port + "/student/findStudentsWhoseAgeIsLessThanGiven" + age;

        List<StudentDto> students = this.testTemplate.getForObject(url, List.class);

        Assertions.assertThat(students)
                .isNotNull()
                .isInstanceOf(List.class);
    }

    @Test
    void shouldReturnStudents_WhenGetStudentsByAgeRequest() {
        String url = "http://localhost:" + port + "/student/getStudentsByAge";

        List<StudentDto> students = this.testTemplate.getForObject(url, List.class);

        Assertions.assertThat(students)
                .isNotNull()
                .isInstanceOf(List.class);
    }

    @Test
    void shouldReturnFaculty_WhenGetStudentFacultyIdRequest() {
        Long facultyId = 2L;

        String url = "http://localhost:" + port + "/student/studentFuculty" + facultyId;

        FacultyDto faculty = this.testTemplate.getForObject(url, FacultyDto.class);

        Assertions.assertThat(faculty).isNotNull();
    }

    @Test
    void shouldReturnFaculty_WhenUpdateStudentFacultyRequest() {
        Long studentId = 1L;
        Long facultyId = 2L;

        String url = "http://localhost:" + port + "/student/updateFaculty" + studentId + "/" + facultyId;

        this.testTemplate.patchForObject(url, null, FacultyDto.class);

        FacultyDto faculty = this.testTemplate.getForObject(url, FacultyDto.class);

        Assertions.assertThat(faculty).isNotNull();
    }
}
