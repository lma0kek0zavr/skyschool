package ru.hogwarts.school.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;
import ru.hogwarts.school.dto.FacultyDto;
import ru.hogwarts.school.dto.StudentDto;
import ru.hogwarts.school.mapper.FacultyMapper;
import ru.hogwarts.school.mapper.StudentMapper;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.StudentService;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    private final StudentMapper studentMapper;

    private final FacultyMapper facultyMapper;

    private final FacultyService facultyService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Student find(Long id) {
        logger.info("Was invoked method for find student by id: {}", id);

        try {
            studentRepository.findById(id);
        } catch (RuntimeException e) {
            logger.error("ResponseStatusException was throw", e);

            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found");
        }

        return studentRepository.findById(id).get();
    }

    @Override
    public StudentDto save(StudentDto dto) {
        logger.info("Was invoked method for save student: {}", dto);

        studentRepository.save(studentMapper.toEntity(dto));

        return dto;
    }

    @Override
    public Long delete(Long id) {
        logger.info("Was invoked method for delete student by id: {}", id);

        studentRepository.deleteById(id);

        return id;
    }

    @Override
    public StudentDto findById(Long id) {
        logger.info("Was invoked method for find student by id: {}", id);

        return studentMapper.toDto(find(id));
    }

    @Override
    public List<StudentDto> findAll() {
        logger.info("Was invoked method for find all students");
        
        return studentRepository.findAll()
                .stream()
                .map(studentMapper::toDto)
                .toList();
    }

    @Override
    public StudentDto update(Long id, StudentDto studentDto) {
        logger.info("Was invoked method for update student by id: {}, passed StudentDto {}", id, studentDto);

        Student studentToUpdate = find(id);

        studentToUpdate.setName(studentDto.getName());
        studentToUpdate.setAge(studentDto.getAge());
        studentRepository.save(studentToUpdate);

        return studentMapper.toDto(studentToUpdate);
    }

    @Override
    public List<StudentDto> findByAgeBetween(int minRange, int maxRange) {
        logger.info("Was invoked method for find students by age between: {} and {}", minRange, maxRange);

        return studentRepository.findByAgeBetween(minRange, maxRange).stream()
                .map(studentMapper::toDto)
                .toList();
    }

    @Override
    public List<String> getStudentsNames() {
        logger.info("Was invoked method for get students names");

        return studentRepository.getStudentAndDisplayOnlyNames();
    }

    @Override
    public List<StudentDto> findStudentsWithCharacterContains(char character) {
        logger.info("Was invoked method for find students whose name contains character: {}", character);

        return studentRepository.findByNameContains(character).stream()
                .map(studentMapper::toDto)
                .toList();
    }

    @Override
    public List<StudentDto> findStudentsWhoseAgeIsLessThanGiven(int age) {
        logger.info("Was invoked method for find students whose age is less than given: {}", age);

        return studentRepository.findStudentsWhoseAgeIsLessThanGiven(age).stream()
                .map(studentMapper::toDto)
                .toList();
    }

    @Override
    public List<StudentDto> getStudentByAge() {
        logger.info("Was invoked method for get students by age");

        return studentRepository.getStudentsSortedByAge().stream()
                .map(studentMapper::toDto)
                .toList();
    }

    @Override
    public FacultyDto getStudentFaculty(Long id) {
        logger.info("Was invoked method for get student faculty by id: {}", id);

        return facultyMapper.toDto(studentRepository.getStudentFaculty(id));
    }

    @Override
    public FacultyDto updateStudentFaculty(Long studentId, Long facultyId) {
        logger.info("Was invoked method for update student faculty by student id: {} and faculty id: {}", studentId, facultyId);

        Student studentToUpdate = find(studentId);
        Faculty facultyToSet = facultyService.find(facultyId);

        studentToUpdate.setStudentFaculties(facultyToSet);

        studentRepository.save(studentToUpdate);

        return facultyMapper.toDto(facultyToSet);
    }
}
