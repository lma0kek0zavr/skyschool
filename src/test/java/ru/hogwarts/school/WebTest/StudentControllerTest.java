package ru.hogwarts.school.WebTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyChar;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.dto.FacultyDto;
import ru.hogwarts.school.dto.StudentDto;
import ru.hogwarts.school.service.StudentService;

@WebMvcTest(StudentController.class)
public class StudentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private StudentService studentService;

    List<StudentDto> students = List.of(
            new StudentDto(1L, "Harry", 15, null, null),
            new StudentDto(2L, "Ron", 15, null, null),
            new StudentDto(3L, "Hermione", 15, null, null)
    );

    @Test
    void shouldReturnAllStudents_WhenGetAllRequest() throws Exception {
        when(studentService.findAll()).thenReturn(students);

        mockMvc.perform(MockMvcRequestBuilders.get("/student/getAll"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.size()").value(3));

        Mockito.verify(studentService, atLeastOnce()).findAll();
    }

    @ParameterizedTest
    @ValueSource(longs = {1L, 2L, 3L})
    void shouldReturnStudentById_WhenGetByIdRequest(Long id) throws Exception {
        when(studentService.findById(anyLong())).thenReturn(students.get(id.intValue() - 1));

        mockMvc.perform(MockMvcRequestBuilders.get("/student/get{id}", id))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(id));

        Mockito.verify(studentService, atLeastOnce()).findById(anyLong());
    }

    @Test
    void shouldReturnStudent_WhenSaveRequest() throws Exception {
        when(studentService.save(any(StudentDto.class))).thenReturn(students.get(0));

        String studentJson = "{\"id\":1,\"name\":\"Harry\",\"age\":15,\"faculty\":null,\"avatar\":null}";

        mockMvc.perform(MockMvcRequestBuilders.post("/student/save")
            .contentType("application/json")
            .content(studentJson))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.name").value("Harry"))
            .andExpect(jsonPath("$.age").value(15));

        Mockito.verify(studentService, atLeastOnce()).save(any(StudentDto.class));
    }

    @ParameterizedTest
    @ValueSource(longs = {1L, 2L, 3L})
    void shouldReturnDeletedStudentId_WhenDeleteRequest(Long id) throws Exception {
        when(studentService.delete(anyLong())).thenReturn(students.get(id.intValue() - 1).getId());

        mockMvc.perform(MockMvcRequestBuilders.delete("/student/delete{id}", id))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$").value(id));

        Mockito.verify(studentService, atLeastOnce()).delete(anyLong());
    }

    @Test
    void shouldReturnUpdatedStudent_WhenUpdateRequest() throws Exception {
        StudentDto expectedStudent = new StudentDto(1L, "Test", -1, null, null);

        String studentJson = "{\"name\":\"Test\",\"age\":\"-1\"}";

        when(studentService.update(anyLong(), any(StudentDto.class))).thenReturn(expectedStudent);

        mockMvc.perform(MockMvcRequestBuilders.patch("/student/update/{id}", 1L)
            .contentType("application/json")
            .content(studentJson))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.name").value("Test"))
            .andExpect(jsonPath("$.age").value(-1));

        Mockito.verify(studentService, atLeastOnce()).update(anyLong(), any(StudentDto.class));
    }

    @Test
    void shouldReturnStudentsNames_WhenGetStudentsNamesRequest() throws Exception {
        when(studentService.getStudentsNames()).thenReturn(List.of("Harry", "Ron", "Hermione"));

        mockMvc.perform(MockMvcRequestBuilders.get("/student/getStudentsNames"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.size()").value(3))
            .andExpect(jsonPath("$[0]").value("Harry"))
            .andExpect(jsonPath("$[1]").value("Ron"))
            .andExpect(jsonPath("$[2]").value("Hermione"));

        Mockito.verify(studentService, atLeastOnce()).getStudentsNames();
    }

    @Test
    void shouldReturnStudent_WhenFindByAgeBetweenRequest() throws Exception {
        when(studentService.findByAgeBetween(anyInt(), anyInt())).thenReturn(students);

        mockMvc.perform(MockMvcRequestBuilders.get("/student/findByAgeBetween{minRange}/{maxRange}", 10, 20))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.size()").value(3))
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[0].name").value("Harry"))
            .andExpect(jsonPath("$[0].age").value(15))
            .andExpect(jsonPath("$[1].id").value(2))
            .andExpect(jsonPath("$[1].name").value("Ron"))
            .andExpect(jsonPath("$[1].age").value(15))
            .andExpect(jsonPath("$[2].id").value(3))
            .andExpect(jsonPath("$[2].name").value("Hermione"))
            .andExpect(jsonPath("$[2].age").value(15));

        Mockito.verify(studentService, atLeastOnce()).findByAgeBetween(anyInt(), anyInt());
    }

    @Test
    void shouldReturnStudents_WhenFindStudentsWithCharacterContainsRequest() throws Exception {
        when(studentService.findStudentsWithCharacterContains(anyChar())).thenReturn(students);

        mockMvc.perform(MockMvcRequestBuilders.get("/student/findByNameContains{character}", 'a'))
            .andExpect(status().isOk());

        Mockito.verify(studentService, atLeastOnce()).findStudentsWithCharacterContains(anyChar());
    }

    @Test
    void shouldReturnStudents_WhenFindStudentsWithAgeRequest() throws Exception {
        when(studentService.findStudentsWhoseAgeIsLessThanGiven(anyInt())).thenReturn(students);

        mockMvc.perform(MockMvcRequestBuilders.get("/student/findStudentsWhoseAgeIsLessThanGiven{age}", 20))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.size()").value(3))
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[1].id").value(2))
            .andExpect(jsonPath("$[2].id").value(3));

        Mockito.verify(studentService, atLeastOnce()).findStudentsWhoseAgeIsLessThanGiven(anyInt());
    }

    @Test
    void shouldReturnStudents_WhenGetStudentByAgeDescRequest() throws Exception {
        when(studentService.getStudentByAge()).thenReturn(students.reversed());

        mockMvc.perform(MockMvcRequestBuilders.get("/student/getStudentsByAge"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.size()").value(3))
        .andExpect(jsonPath("$[0].id").value(3))
        .andExpect(jsonPath("$[1].id").value(2))
        .andExpect(jsonPath("$[2].id").value(1));

        Mockito.verify(studentService, atLeastOnce()).getStudentByAge();
    }

    @Test
    void shouldReturnFacultyId_WhenGetStudentFacultyRequest() throws Exception {
        when(studentService.getStudentFaculty(anyLong())).thenReturn(any(FacultyDto.class));

        mockMvc.perform(MockMvcRequestBuilders.get("/student/studentFaculty{id}", 1L))
        .andExpect(status().isOk());

        Mockito.verify(studentService, atLeastOnce()).getStudentFaculty(anyLong());
    }

    @Test
    void shouldReturnFaculty_WhenUpdateStudentFacultyRequest() throws Exception {
        FacultyDto testFaculty = new FacultyDto(1L, "Test", "Black", null);

        when(studentService.updateStudentFaculty(anyLong(), anyLong())).thenReturn(testFaculty);

        mockMvc.perform(MockMvcRequestBuilders.patch("/student/updateFaculty{studentId}/{facultyId}", 1L, 1L))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.name").value("Test"))
        .andExpect(jsonPath("$.color").value("Black"));

        Mockito.verify(studentService, atLeastOnce()).updateStudentFaculty(anyLong(), anyLong());
    }
}

