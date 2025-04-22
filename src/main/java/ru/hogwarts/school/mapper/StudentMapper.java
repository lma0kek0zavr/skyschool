package ru.hogwarts.school.mapper;

import org.mapstruct.Mapper;

import ru.hogwarts.school.dto.StudentDto;
import ru.hogwarts.school.dto.info.AvatarInfoDto;
import ru.hogwarts.school.dto.info.FacultyInfoDto;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

@Mapper(componentModel = "spring", uses = {FacultyMapper.class, AvatarMapper.class})
public interface StudentMapper {

    StudentDto toDto(Student student);

    Student toEntity(StudentDto studentDto);

    FacultyInfoDto facultyToInfoDto(Faculty faculty);

    AvatarInfoDto avatarToInfoDto(Avatar avatar);
}
