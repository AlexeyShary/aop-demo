package com.openschool.aopdemo.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
@DisplayName("Film DTO JSON convert tests")
class FilmJsonTest {
    @Autowired
    private JacksonTester<FilmNewDto> filmNewDtoJacksonTester;

    @Autowired
    private JacksonTester<FilmResponseDto> filmResponseDtoJacksonTester;

    @Test
    @DisplayName("FilmNewDto to JSON")
    void filmNewDtoTest() throws Exception {
        FilmNewDto dto = FilmNewDto.builder()
                .name("JsonTest Film Name")
                .description("JsonTest Film Description")
                .rating(12.34)
                .directorId(5L)
                .build();

        JsonContent<FilmNewDto> jsonContent = filmNewDtoJacksonTester.write(dto);

        assertThat(jsonContent).extractingJsonPathStringValue("$.name").isEqualTo("JsonTest Film Name");
        assertThat(jsonContent).extractingJsonPathStringValue("$.description").isEqualTo("JsonTest Film Description");
        assertThat(jsonContent).extractingJsonPathNumberValue("$.rating").isEqualTo(12.34);
        assertThat(jsonContent).extractingJsonPathNumberValue("$.directorId").isEqualTo(5);
    }

    @Test
    @DisplayName("FilmResponseDto to JSON")
    void filmResponseDtoTest() throws Exception {
        FilmResponseDto dto = FilmResponseDto.builder()
                .id(6L)
                .name("JsonTest Film Name")
                .description("JsonTest Film Description")
                .rating(12.34)
                .director(DirectorResponseDto.builder()
                        .id(7L)
                        .name("JsonTest Director Name")
                        .birthYear(1999)
                        .build())
                .build();

        JsonContent<FilmResponseDto> jsonContent = filmResponseDtoJacksonTester.write(dto);

        assertThat(jsonContent).extractingJsonPathNumberValue("$.id").isEqualTo(6);
        assertThat(jsonContent).extractingJsonPathStringValue("$.name").isEqualTo("JsonTest Film Name");
        assertThat(jsonContent).extractingJsonPathStringValue("$.description").isEqualTo("JsonTest Film Description");
        assertThat(jsonContent).extractingJsonPathNumberValue("$.rating").isEqualTo(12.34);
        assertThat(jsonContent).extractingJsonPathNumberValue("$.director.id").isEqualTo(7);
        assertThat(jsonContent).extractingJsonPathStringValue("$.director.name").isEqualTo("JsonTest Director Name");
        assertThat(jsonContent).extractingJsonPathNumberValue("$.director.birthYear").isEqualTo(1999);
    }
}