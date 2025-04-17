package ru.hogwarts.school.mapper;

import org.mapstruct.Mapper;

import ru.hogwarts.school.dto.StudentDto;
import ru.hogwarts.school.model.Student;

@Mapper(componentModel = "spring")
public interface StudentMapper {
    StudentDto toDto(Student student);

    Student toEntity(StudentDto studentDto);
}
