package com.openschool.aopdemo.repository;

import com.openschool.aopdemo.model.TrackTimeLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TrackTimeLogRepository extends JpaRepository<TrackTimeLog, Long> {
    List<TrackTimeLog> findAllByMethodName(String methodName);

    @Query("SELECT AVG(t.duration) FROM TrackTimeLog t WHERE t.methodName = :methodName")
    Optional<Double> findAverageDurationByMethodName(@Param("methodName") String methodName);
}
