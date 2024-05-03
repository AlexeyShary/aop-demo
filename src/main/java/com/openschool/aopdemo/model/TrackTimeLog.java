package com.openschool.aopdemo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "track_time_logs")
public class TrackTimeLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "track_time_log_id")
    private Long id;

    @Column(name = "track_time_log_method_name")
    private String methodName;

    @Column(name = "track_time_log_method_args")
    private String methodArgs;

    @Column(name = "track_time_log_is_async")
    private Boolean isAsync;

    @Column(name = "track_time_log_timestamp")
    private LocalDateTime timestamp;

    @Column(name = "track_time_log_execution_duration")
    private Long duration;
}
