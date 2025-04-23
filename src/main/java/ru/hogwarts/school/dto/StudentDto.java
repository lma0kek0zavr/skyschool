package ru.hogwarts.school.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.hogwarts.school.dto.info.AvatarInfoDto;
import ru.hogwarts.school.dto.info.FacultyInfoDto;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentDto {
    private Long id;

    private String name;
    
    private int age;

    private FacultyInfoDto studentFaculties;

    private AvatarInfoDto avatar;
}
