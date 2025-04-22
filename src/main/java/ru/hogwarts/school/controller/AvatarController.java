package ru.hogwarts.school.controller;

import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import ru.hogwarts.school.service.AvatarService;

@RestController
@AllArgsConstructor
@RequestMapping("student/avatar")
public class AvatarController {
    
    private final AvatarService avatarService;

    @PostMapping(value = "/save{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void saveAvatar(@PathVariable Long id, @RequestParam MultipartFile file) throws IOException {
        avatarService.updateStudentAvatar(id, file);
    }
}
