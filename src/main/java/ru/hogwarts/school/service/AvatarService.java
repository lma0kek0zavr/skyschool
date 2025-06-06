package ru.hogwarts.school.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import ru.hogwarts.school.dto.AvatarDto;
import ru.hogwarts.school.model.Avatar;

public interface AvatarService {

    AvatarDto updateStudentAvatar(Long studentId, Long avatarId);

    AvatarDto save(MultipartFile file) throws IOException;

    AvatarDto getAvatar(Long avatarId);

    Avatar find(Long id);

    List<AvatarDto> findAll(int page, int size);
}
