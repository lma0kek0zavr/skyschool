package ru.hogwarts.school.dto.info;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AvatarInfoDto {
    private Long id;

    private String filePath;

    private long fileSize;
}
