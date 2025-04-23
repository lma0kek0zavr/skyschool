package ru.hogwarts.school.dto.info;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FacultyInfoDto {
    
    private Long id;

    private String name;
    
    private String color;
}
