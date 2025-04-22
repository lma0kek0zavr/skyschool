package ru.hogwarts.school.service.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
        Avatar avatarToSet = avatarRepository.findById(avatarId).get();
        Student studentToUpdate = studentRepository.findById(studentId).get();
        studentToUpdate.setAvatar(avatarToSet);
        studentRepository.save(studentToUpdate);
        return avatarMapper.toDto(avatarToSet);
    }

    @Override
    public void updateStudentAvatar(Long studentId, MultipartFile avatarFile) throws IOException {
        Student student = studentRepository.findById(studentId).get();
        Path filePath = Path.of(avatarsDir, student.getId() + "." + getExtensions(avatarFile.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);
        try (
          InputStream is = avatarFile.getInputStream();
          OutputStream os = Files.newOutputStream(filePath);
          BufferedInputStream bis = new BufferedInputStream(is, 1024);
          BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            bis.transferTo(bos);
        }

        Avatar avatar = student.getAvatar();
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(avatarFile.getSize());
        avatar.setMediaType(avatarFile.getContentType());
        avatar.setData(avatarFile.getBytes());
        avatarRepository.save(avatar);
    }

    private String getExtensions(String fileName) { 
        return fileName.substring(fileName.lastIndexOf("."));
    }
}
