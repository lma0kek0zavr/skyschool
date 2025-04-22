package ru.hogwarts.school.mapper;

import org.mapstruct.Mapper;

import ru.hogwarts.school.dto.AvatarDto;
import ru.hogwarts.school.model.Avatar;

@Mapper(componentModel = "spring")
public interface AvatarMapper {
    AvatarDto toDto(Avatar avatar);

    Avatar toEntity(AvatarDto avatarDto);
}
