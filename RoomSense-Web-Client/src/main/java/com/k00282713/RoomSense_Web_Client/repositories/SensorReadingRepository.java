package com.k00282713.RoomSense_Web_Client.repositories;

import com.k00282713.RoomSense_Web_Client.entities.SensorReading;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface SensorReadingRepository extends JpaRepository<SensorReading, Integer>
{
    @Query("""
        SELECT sr
        FROM SensorReading sr
        WHERE sr.timestamp >= :fromDate
        ORDER BY sr.timestamp ASC
    """)
    List<SensorReading> findReadingsFrom(@Param("fromDate") LocalDateTime fromDate);

    Optional<SensorReading> findTopByOrderByTimestampDesc();
}
