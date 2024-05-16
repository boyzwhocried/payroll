package com.lawencon.payroll.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lawencon.payroll.model.Document;

@Repository
public interface DocumentRepository extends JpaRepository<Document, String> {
    @Query(value = "SELECT d FROM Document WHERE d.schedule.id = :scheduleId ")
    List<Document> getByScheduleId(@Param("scheduleId") String scheduleId);
}
