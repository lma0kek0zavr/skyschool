package ru.hogwarts.school.service.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
import ru.hogwarts.school.service.StudentService;

@Service
@RequiredArgsConstructor
public class AvatarServiceImpl implements AvatarService {

    private final AvatarRepository avatarRepository;

    private final AvatarMapper avatarMapper;

    private final StudentRepository studentRepository;

    private final StudentService studentService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${path.to.avatars.folder}")
    private String avatarsDir;

    @Override
    public AvatarDto updateStudentAvatar(Long studentId, Long avatarId) {
        logger.info("Was invoked method for update student avatar: with student id {} and avatar id {}", studentId, avatarId);
        Avatar avatarToSet = find(avatarId);

        Student studentToUpdate = studentService.find(avatarId);

        studentToUpdate.setAvatar(avatarToSet);
        
        studentRepository.save(studentToUpdate);
        
        return avatarMapper.toDto(avatarToSet);
    }

    @Override
    public AvatarDto save(MultipartFile avatarFile) throws IOException {
        logger.info("Was invoked method for save avatar: {}");

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
        logger.info("Was invoked method for get avatar by id: {}", avatarId);

        Avatar avatar = find(avatarId);
        
        return avatarMapper.toDto(avatar);
    }

    @Override
    public Avatar find(Long avatarId) {
        logger.info("Was invoked method for find avatar by id: {}", avatarId);

        try {
            avatarRepository.findById(avatarId);
        } catch (RuntimeException e) {
            logger.error("ResponseStatusException was throw", e);

            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Avatar not found");
        }

        return avatarRepository.findById(avatarId).get();
    }

    @Override
    public List<AvatarDto> findAll(int page, int size) {
        logger.info("Was invoked method for find all avatars with page: {} and size: {}", page, size);

        Page<Avatar> avatarPage = avatarRepository.findAll(PageRequest.of(page, size));

        List<Avatar> avatars = avatarPage.getContent();

        return avatars.stream()
                .map(avatarMapper::toDto)
                .toList();
    }
}
