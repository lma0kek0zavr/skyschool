package ru.hogwarts.school.mapper;

import org.mapstruct.Mapper;

import ru.hogwarts.school.dto.FacultyDto;
import ru.hogwarts.school.model.Faculty;

@Mapper(componentModel = "spring")
public interface FacultyMapper {
    FacultyDto toDto(Faculty faculty);

    Faculty toEntity(FacultyDto facultyDto);
}
