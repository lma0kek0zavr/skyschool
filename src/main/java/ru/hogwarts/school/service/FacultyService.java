package ru.hogwarts.school.service;

import java.util.List;

import ru.hogwarts.school.dto.FacultyDto;

public interface FacultyService {
    void save(FacultyDto facultyDto);

    void delete(Long id);

    FacultyDto findById(Long id);

    void update(Long id, FacultyDto facultyDto);

    List<FacultyDto> findAll();
}
