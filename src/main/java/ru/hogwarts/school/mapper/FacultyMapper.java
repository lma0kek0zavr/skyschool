package ru.hogwarts.school.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import ru.hogwarts.school.dto.FacultyDto;
import ru.hogwarts.school.dto.info.StudentInfoDto;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

@Mapper(componentModel = "spring", uses = StudentMapper.class)
public interface FacultyMapper {

    FacultyDto toDto(Faculty faculty);

    Faculty toEntity(FacultyDto facultyDto);

    StudentInfoDto studentToDto(Student student);

    default List<StudentInfoDto> studentsToInfoDto(List<Student> students) {
        return students.stream().map(this::studentToDto).toList();
    }
}
