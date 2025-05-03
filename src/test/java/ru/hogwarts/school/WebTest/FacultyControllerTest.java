package ru.hogwarts.school.WebTest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.dto.FacultyDto;

@WebMvcTest(FacultyController.class)
public class FacultyControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FacultyController facultyController;

    List<FacultyDto> faculties = List.of(
        new FacultyDto(1L, "Gryffindor", "red", null),
        new FacultyDto(2L, "Slytherin", "green", null)
    );

    @Test
    void shouldReturnFacultyList_WhenGetAllFaculties() throws Exception {
        when(facultyController.getAllFaculties()).thenReturn(faculties);

        mockMvc.perform(MockMvcRequestBuilders.get("/faculty/getAll"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[0].name").value("Gryffindor"))
            .andExpect(jsonPath("$[0].color").value("red"))
            .andExpect(jsonPath("$[0].facultyStudents").isEmpty())
            .andExpect(jsonPath("$[1].id").value(2))
            .andExpect(jsonPath("$[1].name").value("Slytherin"))
            .andExpect(jsonPath("$[1].color").value("green"))
            .andExpect(jsonPath("$[1].facultyStudents").isEmpty());

        Mockito.verify(facultyController, atLeastOnce()).getAllFaculties();
    }

    @ParameterizedTest
    @ValueSource(longs = {1L, 2L})
    void shouldReturnFaculty_WhenGetFacultyById(Long id) throws Exception {
        when(facultyController.getFacultyById(anyLong())).thenReturn(faculties.get(id.intValue() - 1));

        mockMvc.perform(MockMvcRequestBuilders.get("/faculty/get{id}", id))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(id))
            .andExpect(jsonPath("$.name").value(faculties.get(id.intValue() - 1).getName()))
            .andExpect(jsonPath("$.color").value(faculties.get(id.intValue() - 1).getColor()))
            .andExpect(jsonPath("$.facultyStudents").isEmpty());

        Mockito.verify(facultyController, atLeastOnce()).getFacultyById(id);
    }

    @Test
    void shouldReturnFaculty_WhenSaveFaculty() throws Exception {
        FacultyDto facultyDto = new FacultyDto(1L, "Gryffindor", "red", null);

        when(facultyController.saveFaculty(any(FacultyDto.class))).thenReturn(facultyDto);

        String facultyJson = "{\"id\":1,\"name\":\"Gryffindor\",\"color\":\"red\",\"facultyStudents\":null}";

        mockMvc.perform(MockMvcRequestBuilders.post("/faculty/save")
            .contentType("application/json")
            .content(facultyJson))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.name").value("Gryffindor"))
            .andExpect(jsonPath("$.color").value("red"));

        Mockito.verify(facultyController, atLeastOnce()).saveFaculty(any(FacultyDto.class));
    }

    @ParameterizedTest
    @ValueSource(longs = {1L, 2L})
    void shouldReturnFacultyId_WhenDeleteFaculty(Long id) throws Exception {
        when(facultyController.deleteFaculty(anyLong())).thenReturn(id);

        mockMvc.perform(MockMvcRequestBuilders.delete("/faculty/delete{id}", id))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").value(id));

        Mockito.verify(facultyController, atLeastOnce()).deleteFaculty(id);
    }

    @Test
    void shouldReturnFaculty_WhenUpdateFaculty() throws Exception {
        FacultyDto expectedFaculty = new FacultyDto(1L, "Test", "black", null);

        String facultyJson = "{\"name\":\"Test\",\"color\":\"black\"}";

        when(facultyController.updateFaculty(anyLong(), any(FacultyDto.class))).thenReturn(expectedFaculty);

        mockMvc.perform(MockMvcRequestBuilders.patch("/faculty/update/{id}", 1L)
            .contentType("application/json")
            .content(facultyJson))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.name").value("Test"))
            .andExpect(jsonPath("$.color").value("black"));

        Mockito.verify(facultyController, atLeastOnce()).updateFaculty(anyLong(), any(FacultyDto.class));
    }
}
