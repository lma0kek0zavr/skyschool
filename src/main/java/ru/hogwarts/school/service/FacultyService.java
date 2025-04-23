package ru.hogwarts.school.service;

import java.util.List;

import ru.hogwarts.school.dto.FacultyDto;
import ru.hogwarts.school.model.Faculty;

public interface FacultyService {

    Faculty find(Long id);

    FacultyDto save(FacultyDto dto);

    Long delete(Long id);

    FacultyDto findById(Long id);

    List<FacultyDto> findAll();

    FacultyDto update(Long id, FacultyDto facultyDto);
}
