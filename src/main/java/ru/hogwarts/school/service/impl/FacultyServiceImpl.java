package ru.hogwarts.school.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;
import ru.hogwarts.school.dto.FacultyDto;
import ru.hogwarts.school.mapper.FacultyMapper;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.service.FacultyService;

@Service
@RequiredArgsConstructor
public class FacultyServiceImpl implements FacultyService {

    private final FacultyRepository facultyRepository;

    private final FacultyMapper facultyMapper;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Faculty find(Long id) {
        logger.info("Was invoked method for find faculty by id: {}", id);

        try {
            facultyRepository.findById(id);
        } catch (RuntimeException e) {
            logger.error("ResponseStatusException was throw", e);

            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Faculty not found");
        }

        return facultyRepository.findById(id).get();
    }

    @Override
    public FacultyDto save(FacultyDto dto) {
        logger.info("Was invoked method for save faculty: {}", dto);

        facultyRepository.save(facultyMapper.toEntity(dto));

        return dto;
    }

    @Override
    public Long delete(Long id) {
        logger.info("Was invoked method for delete faculty by id: {}", id);

        facultyRepository.deleteById(id);

        return id;
    }

    @Override
    public FacultyDto findById(Long id) {
        logger.info("Was invoked method for find faculty by id: {}", id);

        return facultyMapper.toDto(find(id));
    }

    @Override
    public List<FacultyDto> findAll() {
        logger.info("Was invoked method for find all faculties");

        return facultyRepository.findAll()
                .stream()
                .map(facultyMapper::toDto)
                .toList();
    }
    @Override
    public FacultyDto update(Long id, FacultyDto facultyDto) {
        logger.info("Was invoked method for update faculty by id: {}, passed FacultyDto {}", id, facultyDto);

        Faculty facultyToUpdate = find(id);

        facultyToUpdate.setName(facultyDto.getName());
        facultyToUpdate.setColor(facultyDto.getColor());

        facultyRepository.save(facultyToUpdate);
        
        return facultyMapper.toDto(facultyToUpdate);
    }

    @Override
    public FacultyDto findLongestName() {
        List<Faculty> faculties = facultyRepository.findAll();

        return faculties.stream().parallel()
                .max((f1, f2) -> Integer.compare(f1.getName().length(), f2.getName().length()))
                .map(facultyMapper::toDto)
                .orElse(null);
    }
}
