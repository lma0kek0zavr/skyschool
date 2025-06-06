package ru.hogwarts.school.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.hogwarts.school.dto.info.StudentInfoDto;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FacultyDto {
    private Long id;

    private String name;

    private String color;

    private List<StudentInfoDto> facultyStudents;
}
