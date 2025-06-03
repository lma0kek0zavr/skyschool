package ru.hogwarts.school.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import ru.hogwarts.school.dto.FacultyDto;
import ru.hogwarts.school.service.FacultyService;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@AllArgsConstructor
@RequestMapping("/faculty")
public class FacultyController {
    
    private final FacultyService facultyService;

    @GetMapping("/getAll")
    public List<FacultyDto> getAllFaculties() {
        return facultyService.findAll();
    }
    
    @GetMapping("/get{id}")
    public FacultyDto getFacultyById(@PathVariable Long id) {
        return facultyService.findById(id);
    }
    
    @PostMapping("/save")
    public FacultyDto saveFaculty(@RequestBody FacultyDto facultyDto) {
        return facultyService.save(facultyDto);
    }
    
    @DeleteMapping("/delete{id}")
    public Long deleteFaculty(@PathVariable Long id) {
        return facultyService.delete(id);
    }

    @PatchMapping("/update/{id}")
    public FacultyDto updateFaculty(@PathVariable Long id, @RequestBody FacultyDto facultyDto) {
        return facultyService.update(id, facultyDto);
    }

    @GetMapping("/longestName")
    public FacultyDto getMethodName() {
        return facultyService.findLongestName();
    }
    
}
