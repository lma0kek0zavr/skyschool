package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import ru.hogwarts.school.dto.StudentDto;
import ru.hogwarts.school.service.StudentService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@AllArgsConstructor
@RequestMapping("/student")
public class StudentController {
    
    private final StudentService studentService;

    @GetMapping("/getAll")
    public ResponseEntity<List<StudentDto>> getAllStudents() {
        return new ResponseEntity<>(studentService.findAll(), HttpStatus.OK);
    }
    
    @GetMapping("/get{id}")
    public ResponseEntity<StudentDto> getStudentById(@PathVariable Long id) {
        return new ResponseEntity<>(studentService.findById(id), HttpStatus.OK);
    }
    
    @PostMapping("/save")
    public ResponseEntity<HttpStatus> saveStudent(@RequestBody StudentDto studentDto) {
        studentService.save(studentDto);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
    
    @DeleteMapping("/delete{id}")
    public ResponseEntity<HttpStatus> deleteStudent(@PathVariable Long id) {
        studentService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<HttpStatus> updateStudent(@PathVariable Long id, @RequestBody StudentDto studentDto) {
        studentService.update(id, studentDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
