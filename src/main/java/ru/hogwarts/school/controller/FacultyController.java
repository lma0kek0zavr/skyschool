package ru.hogwarts.school.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import ru.hogwarts.school.dto.FacultyDto;
import ru.hogwarts.school.service.FacultyService;

@RestController
@AllArgsConstructor
@RequestMapping("/faculty")
public class FacultyController {
    
    private final FacultyService facultyService;

    @GetMapping("/getAll")
    public ResponseEntity<List<FacultyDto>> getAllFaculties() {
        return new ResponseEntity<>(facultyService.findAll(), HttpStatus.OK);
    }
    
    @GetMapping("/get{id}")
    public ResponseEntity<FacultyDto> getFacultyById(@PathVariable Long id) {
        return new ResponseEntity<>(facultyService.findById(id), HttpStatus.OK);
    }
    
    @PostMapping("/save")
    public ResponseEntity<HttpStatus> saveFaculty(@RequestBody FacultyDto facultyDto) {
        facultyService.save(facultyDto);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
    
    @DeleteMapping("/delete{id}")
    public ResponseEntity<HttpStatus> deleteFaculty(@PathVariable Long id) {
        facultyService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<HttpStatus> updateFaculty(@PathVariable Long id, @RequestBody FacultyDto facultyDto) {
        facultyService.update(id, facultyDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
