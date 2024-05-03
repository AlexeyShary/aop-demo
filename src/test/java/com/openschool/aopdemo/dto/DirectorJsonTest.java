package com.openschool.aopdemo.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
@DisplayName("Director DTO JSON convert tests")
class DirectorJsonTest {
    @Autowired
    private JacksonTester<DirectorNewDto> directorNewDtoJacksonTester;

    @Autowired
    private JacksonTester<DirectorResponseDto> directorResponseDtoJacksonTester;

    @Test
    @DisplayName("DirectorNewDto to JSON")
    void producerNewDtoTest() throws Exception {
        DirectorNewDto dto = DirectorNewDto.builder()
                .name("JsonTest Director Name")
                .birthYear(1950)
                .build();

        JsonContent<DirectorNewDto> jsonContent = directorNewDtoJacksonTester.write(dto);

        assertThat(jsonContent).extractingJsonPathStringValue("$.name").isEqualTo("JsonTest Director Name");
        assertThat(jsonContent).extractingJsonPathNumberValue("$.birthYear").isEqualTo(1950);
    }

    @Test
    @DisplayName("DirectorResponseDto to JSON")
    void producerResponseDtoTest() throws Exception {
        DirectorResponseDto dto = DirectorResponseDto.builder()
                .id(34L)
                .name("JsonTest Director Name")
                .birthYear(1950)
                .build();

        JsonContent<DirectorResponseDto> jsonContent = directorResponseDtoJacksonTester.write(dto);

        assertThat(jsonContent).extractingJsonPathNumberValue("$.id").isEqualTo(34);
        assertThat(jsonContent).extractingJsonPathStringValue("$.name").isEqualTo("JsonTest Director Name");
        assertThat(jsonContent).extractingJsonPathNumberValue("$.birthYear").isEqualTo(1950);
    }
}