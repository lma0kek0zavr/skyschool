package ru.hogwarts.school.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import ru.hogwarts.school.dto.StudentDto;
import ru.hogwarts.school.mapper.StudentMapper;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.StudentService;

@Service
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    private final StudentMapper studentMapper;

    @Override
    public void save(StudentDto studentDto) {
        studentRepository.save(studentMapper.toEntity(studentDto));
    }

    @Override
    public void delete(Long id) {
        studentRepository.deleteById(id);
    }

    @Override
    public StudentDto findById(Long id) {
        return studentMapper.toDto(studentRepository.findById(id).get());
    }

    @Override
    public void update(Long id, StudentDto studentDto) {
        Student studentToUpdate = studentRepository.findById(id).get();
        studentToUpdate.setName(studentDto.getName());
        studentToUpdate.setAge(studentDto.getAge());
        studentRepository.save(studentToUpdate);
    }

    @Override
    public List<StudentDto> findAll() {
        return studentRepository.findAll().stream().map(studentMapper::toDto).toList();
    }
}
