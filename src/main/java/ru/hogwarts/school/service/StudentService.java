package ru.hogwarts.school.service;

import java.util.List;

import ru.hogwarts.school.dto.FacultyDto;
import ru.hogwarts.school.dto.StudentDto;
import ru.hogwarts.school.model.Student;

public interface StudentService {

    Student find(Long id);

    StudentDto save(StudentDto dto);

    Long delete(Long id);

    StudentDto findById(Long id);

    List<StudentDto> findAll();

    StudentDto update(Long id, StudentDto studentDto);

    List<StudentDto> findByAgeBetween(int minRange, int maxRange);

    List<String> getStudentsNames();

    List<StudentDto> findStudentsWithCharacterContains(char character);

    List<StudentDto> findStudentsWhoseAgeIsLessThanGiven(int age);

    List<StudentDto> getStudentByAge();

    FacultyDto getStudentFaculty(Long id);

    FacultyDto updateStudentFaculty(Long studentId, Long facultyId);

    List<StudentDto> getAllStarWithLetter();

    int getAverageAge();

    void getParallel();

    void getSynchronized();
}
