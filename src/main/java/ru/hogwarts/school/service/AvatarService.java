package ru.hogwarts.school.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import ru.hogwarts.school.dto.AvatarDto;

public interface AvatarService {

    AvatarDto updateStudentAvatar(Long studentId, Long avatarId);

    void updateStudentAvatar(Long studentId, MultipartFile file) throws IOException;
}
