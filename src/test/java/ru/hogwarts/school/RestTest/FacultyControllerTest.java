package ru.hogwarts.school.RestTest;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.dto.FacultyDto;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FacultyControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private FacultyController facultyController;

    @Autowired
    private TestRestTemplate testTemplate;

    @Test
    void contextLoaded() {
        Assertions.assertThat(facultyController).isNotNull();
    }

    @Test
    void shouldReturnFacultyList_WhneGetAllRequest() {
        String url = "http://localhost:" + port + "/faculty/getAll";

        List<FacultyDto> faculties = testTemplate.getForObject(url, List.class);

        Assertions.assertThat(faculties).isNotNull();
    }

    @Test
    void shouldReturnFaculty_WhenGetByIdRequest() {
        String url = "http://localhost:" + port + "/faculty/get" + 1;

        FacultyDto faculty = testTemplate.getForObject(url, FacultyDto.class);

        Assertions.assertThat(faculty).isNotNull();
        Assertions.assertThat(faculty.getId()).isEqualTo(1);
    }

    @Test
    void shouldReturnFaculty_WhenSaveRequest() {
        String url = "http://localhost:" + port + "/faculty/save";

        FacultyDto faculty = new FacultyDto(1L, "test", "test", null);

        FacultyDto savedFaculty = testTemplate.postForObject(url, faculty, FacultyDto.class);

        Assertions.assertThat(savedFaculty).isNotNull();
    }

    @Test
    void shouldReturnFacultyId_WhenDeleteRequest() {
        String url = "http://localhost:" + port + "/faculty/delete" + 1;

        testTemplate.delete(url, Long.class);
    }

    @Test
    void shouldReturnFaculty_WhenUpdateRequest() {
        String url = "http://localhost:" + port + "/faculty/update" + 1;

        FacultyDto faculty = new FacultyDto(1L, "test", "black", null);

        FacultyDto updatedFaculty = testTemplate.patchForObject(url, faculty, FacultyDto.class);

        Assertions.assertThat(updatedFaculty).isNotNull();
    }
}
