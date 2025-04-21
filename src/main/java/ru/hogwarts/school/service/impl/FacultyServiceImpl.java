package ru.hogwarts.school.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import ru.hogwarts.school.dto.FacultyDto;
import ru.hogwarts.school.mapper.FacultyMapper;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.service.FacultyService;

@Service
@AllArgsConstructor
public class FacultyServiceImpl implements FacultyService {

    private final FacultyRepository facultyRepository;

    private final FacultyMapper facultyMapper;

     @Override
    public FacultyDto save(FacultyDto dto) {
        facultyRepository.save(facultyMapper.toEntity(dto));
        return dto;
    }

    @Override
    public Long delete(Long id) {
        facultyRepository.deleteById(id);
        return id;
    }

    @Override
    public FacultyDto findById(Long id) {
        return facultyMapper.toDto(facultyRepository.findById(id).get());
    }

    @Override
    public List<FacultyDto> findAll() {
        return facultyRepository.findAll()
                .stream()
                .map(facultyMapper::toDto)
                .toList();
    }
    @Override
    public FacultyDto update(Long id, FacultyDto facultyDto) {
        Faculty facultyToUpdate = facultyRepository.findById(id).get();
        facultyToUpdate.setName(facultyDto.getName());
        facultyToUpdate.setColor(facultyDto.getColor());
        facultyRepository.save(facultyToUpdate);
        return facultyMapper.toDto(facultyToUpdate);
    }
}
