package ru.hogwarts.school.WebTest;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.multipart.MultipartFile;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ru.hogwarts.school.controller.AvatarController;
import ru.hogwarts.school.dto.AvatarDto;

@WebMvcTest(AvatarController.class)
public class AvatarControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AvatarController avatarController;

    private MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "Hello, world!".getBytes());

    private MultipartFile multipartFile = file;

    @Test
    void shouldReturnAvatar_WhneSaveRequest() throws Exception {
        AvatarDto avatar = new AvatarDto(1L, "fake/path", file.getSize(), file.getContentType(), file.getBytes(), null);

        when(avatarController.saveAvatar(multipartFile)).thenReturn(avatar);

        mockMvc.perform(MockMvcRequestBuilders.multipart("/student/avatar/save").file(file))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.filePath").value("fake/path"))
            .andExpect(jsonPath("$.fileSize").value(file.getSize()))
            .andExpect(jsonPath("$.mediaType").value(file.getContentType()));

        Mockito.verify(avatarController, atLeastOnce()).saveAvatar(multipartFile);
    }
            
    @Test
    void shouldReturnAvatar_WhenUpdateRequest() throws Exception {
        AvatarDto avatar = new AvatarDto(1L, "fake/path", file.getSize(), file.getContentType(), file.getBytes(), null);

        when(avatarController.updateStudentAvatar(1L, 1L)).thenReturn(avatar);

        mockMvc.perform(MockMvcRequestBuilders.patch("/student/avatar/update/1/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.filePath").value("fake/path"))
            .andExpect(jsonPath("$.fileSize").value(file.getSize()))
            .andExpect(jsonPath("$.mediaType").value(file.getContentType()));

        Mockito.verify(avatarController, atLeastOnce()).updateStudentAvatar(1L, 1L);
    }

    @Test
    void shouldReturnAvatar_WhenGetAvatarRequest() throws Exception {
        when(avatarController.getAvatar(anyLong())).thenReturn(ResponseEntity.ok(file.getBytes()));

        mockMvc.perform(MockMvcRequestBuilders.get("/student/avatar/avatar1-from-server"))
            .andExpect(status().isOk());

        Mockito.verify(avatarController, atLeastOnce()).getAvatar(anyLong());
    }
}
