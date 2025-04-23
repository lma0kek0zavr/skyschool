package ru.hogwarts.school.service.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;
import ru.hogwarts.school.dto.AvatarDto;
import ru.hogwarts.school.mapper.AvatarMapper;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.AvatarService;

@Service
@RequiredArgsConstructor
public class AvatarServiceImpl implements AvatarService {

    private final AvatarRepository avatarRepository;

    private final AvatarMapper avatarMapper;

    private final StudentRepository studentRepository;

    @Value("${path.to.avatars.folder}")
    private String avatarsDir;

    @Override
    public AvatarDto updateStudentAvatar(Long studentId, Long avatarId) {
        Avatar avatarToSet = find(avatarId);
        Student studentToUpdate = studentRepository.findById(studentId).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found")
        );

        studentToUpdate.setAvatar(avatarToSet);
        
        studentRepository.save(studentToUpdate);
        
        return avatarMapper.toDto(avatarToSet);
    }

    @Override
    public AvatarDto save(MultipartFile avatarFile) throws IOException {
        Path path = Path.of(avatarsDir, avatarFile.getOriginalFilename() + getExtensions(avatarFile.getOriginalFilename()));
        Files.createDirectories(path.getParent());
        Files.deleteIfExists(path);

        try (
            InputStream is = avatarFile.getInputStream();
            OutputStream os = Files.newOutputStream(path);
            BufferedInputStream bis = new BufferedInputStream(is, 1024);
            BufferedOutputStream bos = new BufferedOutputStream(os, 1024)
        ) {
            bis.transferTo(bos);
        } catch (Exception e) {
            throw new IOException(e);
        }

        Avatar avatar = new Avatar();
        byte[] data = avatarFile.getBytes();

        avatar.setFilePath(path.toString());
        avatar.setFileSize(avatarFile.getSize());
        avatar.setMediaType(avatarFile.getContentType());
        avatar.setData(data);

        avatarRepository.save(avatar);

        return avatarMapper.toDto(avatar);
    }

    private String getExtensions(String fileName) { 
        return fileName.substring(fileName.lastIndexOf("."));
    }

    @Override
    public AvatarDto getAvatar(Long avatarId) {
        Avatar avatar = find(avatarId);
        
        return avatarMapper.toDto(avatar);
    }

    @Override
    public Avatar find(Long avatarId) {
        return avatarRepository.findById(avatarId).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Avatar not found")
        );
    }
}
