package ru.hogwarts.school.RestTest;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import ru.hogwarts.school.controller.AvatarController;
import ru.hogwarts.school.dto.AvatarDto;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AvatarControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private AvatarController avatarController;

    @Autowired
    private TestRestTemplate testTemplate;

    private AvatarDto fakeAvatar = new AvatarDto(1L, "path/smth", 100, "image/jpeg", new byte[]{}, null);
    
    @Test
    void contextLoaded() {
        Assertions.assertThat(avatarController).isNotNull();
    }

    @Test
    void shouldReturnAvatar_WhenSaveRequest() {
        String url = "http://localhost:" + port + "/student/avatar/save";
        
        AvatarDto avatar = testTemplate.postForObject(url, fakeAvatar, AvatarDto.class);

        Assertions.assertThat(avatar).isNotNull();
    }

    @Test
    void shouldReturnAvatar_WhenUpdateRequest() {
        String url = "http://localhost:" + port + "/student/avatar/update/1/1";

        AvatarDto avatar = testTemplate.postForObject(url, fakeAvatar, AvatarDto.class);

        Assertions.assertThat(avatar).isNotNull();
    }

    @Test
    void shouldReturnAvatar_WhenGetFromServerRequest() {
        String url = "http://localhost:" + port + "/student/avatar/avatar" + 1 + "-from-server";

        ResponseEntity<byte[]> response = testTemplate.getForEntity(url, byte[].class);

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getBody()).isNotEmpty();
        Assertions.assertThat(response.getHeaders().getContentType()).isNotNull();
        Assertions.assertThat(response.getHeaders().getContentLength()).isGreaterThan(0);
    }
}
