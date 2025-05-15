package ru.hogwarts.school.repository;

import org.springframework.data.domain.PageRequest;

public class AvatarPagination {
    public PageRequest avatarRequest = PageRequest.of(0, 10);
}
