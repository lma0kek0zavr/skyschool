package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import ru.hogwarts.school.dto.FacultyDto;
import ru.hogwarts.school.dto.StudentDto;
import ru.hogwarts.school.service.StudentService;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@AllArgsConstructor
@RequestMapping("/student")
public class StudentController {
    
    private final StudentService studentService;

    @GetMapping("/getAll")
    public List<StudentDto> getAllStudents() {
        return studentService.findAll();
    }
    
    @GetMapping("/get{id}")
    public StudentDto getStudentById(@PathVariable Long id) {
        return studentService.findById(id);
    }
    
    @PostMapping("/save")
    public StudentDto saveStudent(@RequestBody StudentDto studentDto) {
        return studentService.save(studentDto);
    }
    
    @DeleteMapping("/delete{id}")
    public Long deleteStudent(@PathVariable Long id) {
        return studentService.delete(id);
    }

    @PatchMapping("/update/{id}")
    public StudentDto updateStudent(@PathVariable Long id, @RequestBody StudentDto studentDto) {
        return studentService.update(id, studentDto);
    }

    @GetMapping("/getStudentsNames")
    public List<String> getStudentsNames() {
        return studentService.getStudentsNames();
    }
    
    @GetMapping("/findByAgeBetween{minRange}/{maxRange}")
    public List<StudentDto> findByAgeRange(@PathVariable int minRange, @PathVariable int maxRange) {
        return studentService.findByAgeBetween(minRange, maxRange);
    }
    
    @GetMapping("/findByNameContains{character}")
    public List<StudentDto> getMethodName(@PathVariable char character) {
        return studentService.findStudentsWithCharacterContains(character);
    }
    
    @GetMapping("/findStudentsWhoseAgeIsLessThanGiven{age}")
    public List<StudentDto> findStudents(@PathVariable int age) {
        return studentService.findStudentsWhoseAgeIsLessThanGiven(age);
    }
    
    @GetMapping("/getStudentsByAge")
    public List<StudentDto> getStudentsByAge() {
        return studentService.getStudentByAge();
    }
    
    @GetMapping("/studentFaculty{id}")
    public FacultyDto getStudentFaculty(@PathVariable Long id) {
        return studentService.getStudentFaculty(id);
    }
    
    @PatchMapping("/updateFaculty{studentId}/{facultyId}")
    public FacultyDto updateStudentFaculty(@PathVariable Long studentId, @PathVariable Long facultyId) {
        return studentService.updateStudentFaculty(studentId, facultyId);
    }

    @GetMapping("/getAllStartWithLetter")
    public List<StudentDto> getAllStartWith() {
        return studentService.getAllStarWithLetter();
    }

    @GetMapping("/averageAge")
    public double getAverageAge() {
        return studentService.getAverageAge();
    }
    
    @GetMapping("/printParallel")
    public void printParallel() {
        studentService.getParallel();
    }
    
    @GetMapping("printSynchronized")
    public void printSynchronized() {
        studentService.getSynchronized();
    }
    
}
