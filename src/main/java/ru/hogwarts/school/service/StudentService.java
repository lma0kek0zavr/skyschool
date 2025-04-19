package ru.hogwarts.school.service;

import java.util.List;

import ru.hogwarts.school.dto.StudentDto;

public interface StudentService {
    void save(StudentDto studentDto);

    void delete(Long id);
    
    StudentDto findById(Long id);

    void update(Long id, StudentDto studentDto);

    List<StudentDto> findAll();
}
