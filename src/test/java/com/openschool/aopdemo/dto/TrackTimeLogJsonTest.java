package com.openschool.aopdemo.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
@DisplayName("TrackTimeLog DTO JSON convert tests")
class TrackTimeLogJsonTest {
    @Autowired
    private JacksonTester<TrackTimeLogAverageDurationDto> trackTimeLogAverageDurationDtoJacksonTester;

    @Autowired
    private JacksonTester<TrackTimeLogDto> trackTimeLogDtoJacksonTester;

    @Autowired
    private JacksonTester<TrackTimeLogStatDto> trackTimeLogStatDtoJacksonTester;

    @Test
    @DisplayName("TrackTimeLogAverageDurationDto to JSON")
    void trackTimeLogAverageDurationDtoTest() throws Exception {
        TrackTimeLogAverageDurationDto dto = TrackTimeLogAverageDurationDto.builder()
                .methodName("JsonTest Method Name")
                .averageDuration(12.34)
                .build();

        JsonContent<TrackTimeLogAverageDurationDto> jsonContent = trackTimeLogAverageDurationDtoJacksonTester.write(dto);

        assertThat(jsonContent).extractingJsonPathStringValue("$.methodName").isEqualTo("JsonTest Method Name");
        assertThat(jsonContent).extractingJsonPathNumberValue("$.averageDuration").isEqualTo(12.34);
    }

    @Test
    @DisplayName("TrackTimeLogDto to JSON")
    void trackTimeLogDtoTest() throws Exception {
        LocalDateTime timestamp = LocalDateTime.now();

        TrackTimeLogDto dto = TrackTimeLogDto.builder()
                .id(1L)
                .methodName("JsonTest Method Name")
                .methodArgs("JsonTest Method Args")
                .isAsync(true)
                .timestamp(timestamp)
                .duration(10L)
                .build();

        JsonContent<TrackTimeLogDto> jsonContent = trackTimeLogDtoJacksonTester.write(dto);

        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

        assertThat(jsonContent).extractingJsonPathNumberValue("$.id").isEqualTo(1);
        assertThat(jsonContent).extractingJsonPathStringValue("$.methodName").isEqualTo("JsonTest Method Name");
        assertThat(jsonContent).extractingJsonPathStringValue("$.methodArgs").isEqualTo("JsonTest Method Args");
        assertThat(jsonContent).extractingJsonPathBooleanValue("$.isAsync").isEqualTo(true);
        assertThat(jsonContent).extractingJsonPathStringValue("$.timestamp").isEqualTo(formatter.format(timestamp));
        assertThat(jsonContent).extractingJsonPathNumberValue("$.duration").isEqualTo(10);
    }

    @Test
    @DisplayName("TrackTimeLogStatDto to JSON")
    void trackTimeLogStatDtoTest() throws Exception {
        TrackTimeLogStatDto dto = TrackTimeLogStatDto.builder()
                .methodName("JsonTest Method Name")
                .callCount(1L)
                .minimumDuration(2L)
                .maximumDuration(3L)
                .averageDuration(2.5)
                .build();

        JsonContent<TrackTimeLogStatDto> jsonContent = trackTimeLogStatDtoJacksonTester.write(dto);

        assertThat(jsonContent).extractingJsonPathStringValue("$.methodName").isEqualTo("JsonTest Method Name");
        assertThat(jsonContent).extractingJsonPathNumberValue("$.callCount").isEqualTo(1);
        assertThat(jsonContent).extractingJsonPathNumberValue("$.minimumDuration").isEqualTo(2);
        assertThat(jsonContent).extractingJsonPathNumberValue("$.maximumDuration").isEqualTo(3);
        assertThat(jsonContent).extractingJsonPathNumberValue("$.averageDuration").isEqualTo(2.5);
    }
}