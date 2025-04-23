package ru.hogwarts.school.service.impl;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    private final StudentMapper studentMapper;

    private final FacultyMapper facultyMapper;

    private final FacultyService facultyService;

    @Override
    public Student find(Long id) {
        return studentRepository.findById(id).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found")
        );
    }

    @Override
    public StudentDto save(StudentDto dto) {
        studentRepository.save(studentMapper.toEntity(dto));

        return dto;
    }

    @Override
    public Long delete(Long id) {
        studentRepository.deleteById(id);
        return id;
    }

    @Override
    public StudentDto findById(Long id) {
        return studentMapper.toDto(find(id));
    }

    @Override
    public List<StudentDto> findAll() {
        return studentRepository.findAll()
                .stream()
                .map(studentMapper::toDto)
                .toList();
    }

    @Override
    public StudentDto update(Long id, StudentDto studentDto) {
        Student studentToUpdate = find(id);

        studentToUpdate.setName(studentDto.getName());
        studentToUpdate.setAge(studentDto.getAge());
        studentRepository.save(studentToUpdate);

        return studentMapper.toDto(studentToUpdate);
    }

    @Override
    public List<StudentDto> findByAgeBetween(int minRange, int maxRange) {
        return studentRepository.findByAgeBetween(minRange, maxRange).stream()
                .map(studentMapper::toDto)
                .toList();
    }

    @Override
    public List<String> getStudentsNames() {
        return studentRepository.getStudentAndDisplayOnlyNames();
    }

    @Override
    public List<StudentDto> findStudentsWithCharacterContains(char character) {
        return studentRepository.findByNameContains(character).stream()
                .map(studentMapper::toDto)
                .toList();
    }

    @Override
    public List<StudentDto> findStudentsWhoseAgeIsLessThanGiven(int age) {
        return studentRepository.findStudentsWhoseAgeIsLessThanGiven(age).stream()
                .map(studentMapper::toDto)
                .toList();
    }

    @Override
    public List<StudentDto> getStudentByAge() {
        return studentRepository.getStudentsSortedByAge().stream()
                .map(studentMapper::toDto)
                .toList();
    }

    @Override
    public FacultyDto getStudentFaculty(Long id) {
        return facultyMapper.toDto(studentRepository.getStudentFaculty(id));
    }

    @Override
    public FacultyDto updateStudentFaculty(Long studentId, Long facultyId) {
        Student studentToUpdate = find(studentId);
        Faculty facultyToSet = facultyService.find(facultyId);

        studentToUpdate.setStudentFaculties(facultyToSet);

        studentRepository.save(studentToUpdate);

        return facultyMapper.toDto(facultyToSet);
    }
}
