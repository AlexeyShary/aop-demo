package com.openschool.aopdemo;

import com.openschool.aopdemo.controller.DirectorController;
import com.openschool.aopdemo.controller.FilmController;
import com.openschool.aopdemo.dto.DirectorNewDto;
import com.openschool.aopdemo.dto.FilmNewDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
@DisplayName("Integration test of all components with H2")
public class IntegrationTest {
    @Autowired
    private EntityManager entityManager;

    @Autowired
    private DirectorController directorController;

    @Autowired
    private FilmController filmController;

    @Test
    @Order(1)
    @Transactional
    @Rollback(false)
    @DisplayName("Create test data")
    void createTestData() {
        directorController.create(DirectorNewDto.builder()
                .name("Гор Вербински")
                .birthYear(1964)
                .build());

        directorController.create(DirectorNewDto.builder()
                .name("Кристофер Нолан")
                .birthYear(1970)
                .build());

        directorController.create(DirectorNewDto.builder()
                .name("Люк Бессон")
                .birthYear(1959)
                .build());

        filmController.create(FilmNewDto.builder()
                .name("Звонок")
                .description("Страшная девочка мешает смотреть телевизор разным людям")
                .rating(8.5)
                .directorId(1L)
                .build());

        filmController.create(FilmNewDto.builder()
                .name("Инерстеллар")
                .description("Мэтью Макконахи делает разные крутые штуки под музыку Ханса Зиммера")
                .rating(10.5)
                .directorId(2L)
                .build());

        filmController.create(FilmNewDto.builder()
                .name("Престиж")
                .description("Фокусники борются за зрителей")
                .rating(9.5)
                .directorId(2L)
                .build());

        filmController.create(FilmNewDto.builder()
                .name("Такси")
                .description("Мужик очень быстро катается на белой машине")
                .rating(9.5)
                .directorId(3L)
                .build());

        TypedQuery<Long> directorCountQuery = entityManager.createQuery("SELECT COUNT(d) FROM Director d", Long.class);
        Long directorCount = directorCountQuery.getSingleResult();
        assertThat(directorCount, equalTo(3L));

        TypedQuery<Long> filmCountQuery = entityManager.createQuery("SELECT COUNT(f) FROM Film f", Long.class);
        Long filmCount = filmCountQuery.getSingleResult();
        assertThat(filmCount, equalTo(4L));
    }

    @Test
    @Order(2)
    @Transactional
    @Rollback
    @DisplayName("Check time track logs")
    void checkLogs() {
        TypedQuery<Long> trackTimeLogCountQuery = entityManager.createQuery("SELECT COUNT(l) FROM TrackTimeLog l", Long.class);
        Long trackTimeLogCount = trackTimeLogCountQuery.getSingleResult();
        assertThat(trackTimeLogCount, equalTo(7L));

        TypedQuery<Long> trackTimeLogCountByNameQuery = entityManager.createQuery("SELECT COUNT(l) FROM TrackTimeLog l WHERE l.methodName='FilmService.create'", Long.class);
        Long trackTimeLogCountByName = trackTimeLogCountByNameQuery.getSingleResult();
        assertThat(trackTimeLogCountByName, equalTo(4L));
    }
}