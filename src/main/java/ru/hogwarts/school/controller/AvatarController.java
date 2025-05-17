package ru.hogwarts.school.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import ru.hogwarts.school.dto.AvatarDto;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.service.AvatarService;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@AllArgsConstructor
@RequestMapping("student/avatar")
public class AvatarController {
    
    private final AvatarService avatarService;

    @PostMapping(value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public AvatarDto saveAvatar(@RequestParam MultipartFile file) throws IOException {
        return avatarService.save(file);
    }

    @PatchMapping("/update/{studentId}/{avatarId}")
    public AvatarDto updateStudentAvatar(@PathVariable Long studentId, @PathVariable Long avatarId) throws IOException {
        return avatarService.updateStudentAvatar(studentId, avatarId);
    }

    @GetMapping("/avatar{id}-from-server")
    public ResponseEntity<byte[]> getAvatar(@RequestParam Long id) {
        AvatarDto avatar = avatarService.getAvatar(id);

        HttpHeaders headers = new HttpHeaders();
        
        headers.setContentType(MediaType.parseMediaType(avatar.getMediaType()));
        headers.setContentLength(avatar.getData().length);

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(avatar.getData());
    }
    
    @GetMapping("/avatar{id}-from-internal")
    public void getAvatar(Long avatarId, HttpServletResponse response) throws IOException {
        Avatar avatar = avatarService.find(avatarId);
        
        Path path = Path.of(avatar.getFilePath());
        
        try (
            InputStream is = Files.newInputStream(path);
            OutputStream os = response.getOutputStream();
        ) {
            response.setStatus(200);
            response.setContentType(avatar.getMediaType());
            response.setContentLength(avatar.getData().length);

            is.transferTo(os);
        }
    }

    @GetMapping("/avatar/all")
    public ResponseEntity<List<byte[]>> getAllAvatars(@RequestParam int page, @RequestParam int size) {
        List<AvatarDto> avatars = avatarService.findAll(page, size);

        List<byte[]> avatarData = avatars.stream()
            .map(AvatarDto::getData)
            .toList();

        return ResponseEntity.status(HttpStatus.OK).body(avatarData);
    }
}
