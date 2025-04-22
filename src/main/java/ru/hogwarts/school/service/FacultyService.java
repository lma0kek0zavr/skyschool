package ru.hogwarts.school.service;

import java.util.List;

import ru.hogwarts.school.dto.FacultyDto;

public interface FacultyService {

    FacultyDto save(FacultyDto dto);

    Long delete(Long id);

    FacultyDto findById(Long id);

    List<FacultyDto> findAll();

    FacultyDto update(Long id, FacultyDto facultyDto);
}
