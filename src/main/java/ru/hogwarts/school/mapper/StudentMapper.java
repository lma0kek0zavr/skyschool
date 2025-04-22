package ru.hogwarts.school.mapper;

import org.mapstruct.Mapper;

import ru.hogwarts.school.dto.FacultyInfoDto;
import ru.hogwarts.school.dto.StudentDto;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

@Mapper(componentModel = "spring", uses = FacultyMapper.class)
public interface StudentMapper {

    StudentDto toDto(Student student);

    Student toEntity(StudentDto studentDto);

    FacultyInfoDto facultyToInfoDto(Faculty faculty);
}
