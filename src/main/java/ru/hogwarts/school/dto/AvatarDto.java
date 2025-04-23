package ru.hogwarts.school.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.hogwarts.school.dto.info.StudentInfoDto;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AvatarDto {
    private Long id;

    private String filePath;

    private long fileSize;

    private String mediaType;

    private byte[] data;

    private StudentInfoDto student;
}
